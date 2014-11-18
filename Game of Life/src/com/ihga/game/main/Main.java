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
	
	public static void main(String[] args){
		contentPanel = new ContentPanel();
		
		setupFrame();
		
		GameLoop gameLoop = new GameLoop();
		gameLoop.start();
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
	
	private static class GameLoop extends Thread{
		
		public GameLoop(){
			
		}
		
		@Override
		public void run() {
			contentPanel.repaint();
			
		}
		
	}

}
