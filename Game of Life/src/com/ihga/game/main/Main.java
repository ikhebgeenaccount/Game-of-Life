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

public class Main {
	//Detect if image is clicked:
	//	http://stackoverflow.com/questions/7454569/how-to-detect-a-click-event-in-an-image-class-in-java
	
	//GUI
	private static GridBagConstraints c;
	private static JFrame frame;
	
	//Button
	private static JPanel buttonPanel;
	private static JButton startButton;
	
	//Statusbar
	private static JPanel statusBar;
	private static JLabel simulationsLabel;
	private static JLabel livingCells;
	
	//Gamepanel
	private static ContentPanel contentPanel;

	
	//Threads
	private static InitLoop initLoop;
	private static SimulationLoop simLoop;
	
	private static boolean running;
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
		
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		startButton = new JButton("Start simulation");
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				initLoop.interrupt();
				
				edit = false;
				
				simLoop = new SimulationLoop();
				simLoop.start();
			}	
		});
		
		buttonPanel.add(startButton);
		
		contentPanel = new ContentPanel();
		
		statusBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		livingCells = new JLabel(contentPanel.getLivingCells());
		simulationsLabel = new JLabel(simulations);
		
		frame.getContentPane().setLayout(new BorderLayout());
		
		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);		
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
				contentPanel.repaint();
				endTime = System.currentTimeMillis();
				try{
					//If the time it took to paint this frame is bigger than the time set for one frame, it needs to instantly
					//repaint(), since it is behind on schedule
					if(endTime - startTime > frameTime){
						
					 //If the time it took to paint this frame is equal to the time set to paint one frame, it needs to 
					 //instantly repaint(), since it is perfect on schedule
					}else if(endTime - startTime == frameTime){
						
					 //If it took less time, we need to sleep the remaining millis of the loop time	
					}else{
						sleep(frameTime - (endTime - startTime));
					}
				}catch(Exception e){
					
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
			long startTime;
			while(running){
				startTime = System.currentTimeMillis();
				contentPanel.simulate();
				System.out.println("Simulation time: " + (System.currentTimeMillis() - startTime));
				startTime = System.currentTimeMillis();
				contentPanel.repaint();
				System.out.println("Paint time: " + (System.currentTimeMillis() - startTime));
				simulations++;
				simulationsLabel.setText(simulations);
				livingCells.setText(contentPanel.getLivingCells());
			}
		}
	}
	
	public static boolean editAllowed(){
		return edit;
	}

}
