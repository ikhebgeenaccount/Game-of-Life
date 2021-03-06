package com.ihga.game.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {
	//Detect if image is clicked:
	//	http://stackoverflow.com/questions/7454569/how-to-detect-a-click-event-in-an-image-class-in-java
	
	//GUI
	private static GridBagConstraints c;
	private static JFrame frame;
	
	//Top bar
	private static JPanel topPanel;
	
	//Menu
	private static JPanel menuPanel;
	private static JButton saveButton, loadButton;
	
	//Button
	private static JPanel buttonPanel;
	private static JButton startButton, pauseButton, resetButton;
	
	//Statusbar
	private static JPanel statusBar;
	private static JLabel simulationsLabel;
	private static JLabel livingCells;
	
	//Gamepanel
	private static ContentPanel contentPanel;
	
	//Threads
	private static InitLoop initLoop;
	private static SimulationLoop simLoop;
	
	private static boolean running, runningSimulation;
	private static boolean edit;
	
	private static int simulations;
	
	public static void main(String[] args){
		setupFrame();
		
		simulations = 0;
		
		initLoop = new InitLoop();
		running = true;
		initLoop.start();
		
		edit = true;
	}
	
	public static void setupFrame(){
		frame = new JFrame("Game of Life");
		
		topPanel = new JPanel(new BorderLayout());
		
		menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				saveSimulation();
			}
		});
		
		loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				loadSimulation();
				
			}
			
		});
		
		menuPanel.add(saveButton);
		menuPanel.add(loadButton);
		
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		startButton = new JButton("Start simulation");
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				startSimulation();
			}	
		});
		
		pauseButton = new JButton("Pause simulation");
		pauseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pauseSimulation();
			}
		});
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				contentPanel.resetField();
				//contentPanel.repaint();
			}
		});
		
		buttonPanel.add(resetButton);
		buttonPanel.add(startButton);
		
		topPanel.add(buttonPanel, BorderLayout.EAST);
		topPanel.add(menuPanel, BorderLayout.WEST);
		
		contentPanel = new ContentPanel();
		
		statusBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		livingCells = new JLabel(String.valueOf(contentPanel.getLivingCells()));
		simulationsLabel = new JLabel(String.valueOf(simulations));
		statusBar.add(new JLabel("Generations:"));
		statusBar.add(simulationsLabel);
		statusBar.add(new JLabel("Living cells:"));
		statusBar.add(livingCells);
		
		frame.getContentPane().setLayout(new BorderLayout());
		
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);		
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
		
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}
	
	public static ContentPanel getContentPanel(){
		return contentPanel;
	}
	
	private static class InitLoop extends Thread{
		
		public InitLoop(){
			
		}
		
		@Override
		public void run() {
			long maxFPS = 30;
			long startTime;
			long frameTime = 1000/maxFPS;
			long endTime;
			while(running){
				//The startTime of this loop
				startTime = System.currentTimeMillis();
				//contentPanel.repaint();
				endTime = System.currentTimeMillis();
				livingCells.setText(String.valueOf(contentPanel.getLivingCells()));
				contentPanel.repaint();	
				try {
					sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private static class SimulationLoop extends Thread{
		
		public SimulationLoop(){
			
		}
		
		@Override
		public void run(){
			edit = false;
			while(runningSimulation){
				long startTime = System.currentTimeMillis();
				contentPanel.simulate();
				contentPanel.repaint();
				simulations++;
				simulationsLabel.setText(String.valueOf(simulations));
				livingCells.setText(String.valueOf(contentPanel.getLivingCells()));
				if(contentPanel.getLivingCells() == 0){
					pauseSimulation();
				}
				try {
					sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Simulation time: " + (System.currentTimeMillis() - startTime));
			}
		}
	}
	
	public static boolean editAllowed(){
		return edit;
	}

	/**
	 * 
	 */
	public static void pauseSimulation() {
		runningSimulation = false;
		
		edit = true;
		
		initLoop = new InitLoop();
		initLoop.start();
		
		buttonPanel.removeAll();
		buttonPanel.add(resetButton);
		buttonPanel.add(startButton);
		buttonPanel.revalidate();
		buttonPanel.repaint();
	}

	/**
	 * 
	 */
	public static void startSimulation() {
		runningSimulation = true;
		
		edit = false;
		
		simLoop = new SimulationLoop();
		simLoop.start();
		
		buttonPanel.removeAll();
		buttonPanel.add(resetButton);
		buttonPanel.add(pauseButton);
		buttonPanel.revalidate();
		buttonPanel.repaint();
	}
	
	public static void saveSimulation(){
		JFrame saveFrame = new JFrame("Save string");
		JTextField saveField = new JTextField();
		saveField.setColumns(25);
		saveField.setText(contentPanel.getSaveString());
		
		saveFrame.getContentPane().add(saveField);
		saveFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		saveFrame.pack();
		saveFrame.setResizable(false);
		saveFrame.setVisible(true);
		saveFrame.setLocationRelativeTo(null);
	}
	
	public static void loadSimulation(){
		JFrame loadFrame = new JFrame("Load string");
		
		JTextField loadField = new JTextField();
		loadField.setColumns(25);
		
		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				contentPanel.loadString(loadField.getText());
				loadFrame.dispose();
			}
		});
		
		loadFrame.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));
		loadFrame.getContentPane().add(loadField);
		loadFrame.getContentPane().add(load);
		loadFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		loadFrame.pack();
		loadFrame.setResizable(false);
		loadFrame.setVisible(true);
		loadFrame.setLocationRelativeTo(null);		
	}

}
