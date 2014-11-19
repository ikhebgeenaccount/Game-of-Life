package com.ihga.game.main;

import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
	//Detect if image is clicked:
	//	http://stackoverflow.com/questions/7454569/how-to-detect-a-click-event-in-an-image-class-in-java
	
	//GUI
	private static JFrame frame;
	private static JPanel buttonPanel;
	private static JButton startButton;
	private static ContentPanel contentPanel;
	private static GridBagConstraints c;
	
	private static InitLoop initLoop;
	private static SimulationLoop simLoop;
	
	private static boolean running;
	
	public static void main(String[] args){
		contentPanel = new ContentPanel();
		
		setupFrame();
		
		initLoop = new InitLoop();
		running = true;
		initLoop.start();
	}
	
	public static void setupFrame(){
		frame = new JFrame("Game of Life");
		
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setText("Start simulation");
		buttonPanel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				initLoop.quit();
				
				simLoop = new SimulationLoop();
				simLoop.start();
			}	
		});
		
		frame.getContentPane().add(contentPanel);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);		
		
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
			long startTime;
			while(running){
				startTime = System.currentTimeMillis();
				contentPanel.repaint();	
				System.out.println(System.currentTimeMillis() - startTime);
			}
		}
		
	}
	
	private static class SimulationLoop extends Thread{
		
		public SimulationLoop(){
			
		}
		
		@Override
		public void run(){
			long startTime;
			while(running){
				startTime = System.currentTimeMillis();
				contentPanel.simulate();
				contentPanel.repaint();
				System.out.println(System.currentTimeMillis() - startTime);
			}
		}
	}

}
