package com.ihga.game.main;

import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
	//Detect if image is clicked:
	//	http://stackoverflow.com/questions/7454569/how-to-detect-a-click-event-in-an-image-class-in-java
	
	//GUI
	private static JFrame frame;
	private static ContentPanel contentPanel;
	private static GridBagConstraints c;
	
	private static boolean running;
	
	public static void main(String[] args){
		contentPanel = new ContentPanel();
		
		setupFrame();
		
		InitLoop initLoop = new InitLoop();
		running = true;
		initLoop.start();
	}
	
	public static void setupFrame(){
		frame = new JFrame("Game of Life");
		
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
		
		public GameLoop(){
			
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

}
