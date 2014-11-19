package com.ihga.game.main;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
	private static boolean edit;
	
	public static void main(String[] args){
		contentPanel = new ContentPanel();
		
		setupFrame();
		
		//initLoop = new InitLoop();
		running = true;
		//initLoop.start();
		
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
		
		frame.getContentPane().add(buttonPanel);		
		frame.getContentPane().add(contentPanel);
		
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
			long startTime;
			while(running){
				startTime = System.currentTimeMillis();
				contentPanel.simulate();
				System.out.println("Simulation time: " + (System.currentTimeMillis() - startTime));
				startTime = System.currentTimeMillis();
				contentPanel.repaint();
				System.out.println("Paint time: " + (System.currentTimeMillis() - startTime));
			}
		}
	}
	
	public static boolean editAllowed(){
		return edit;
	}

}
