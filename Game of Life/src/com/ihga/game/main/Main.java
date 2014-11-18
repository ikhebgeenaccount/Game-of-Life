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
	
	//JLabels with ImageIcons
	private static LabelIcon life;
	private static LabelIcon death;
	
	public static void main(String[] args){
		life = new LabelIcon(new ImageIcon(Main.class.getClassLoader().getResource("com/ihga/graphics/img/life.png")));
		death = new LabelIcon(new ImageIcon(Main.class.getClassLoader().getResource("com/ihga/graphics/img/death.png")));
		
		contentPanel = new ContentPanel();
		
		setupFrame();
	}
	
	public static void setupFrame(){
		frame = new JFrame("Game of Life");
		
		frame.setContentPane(contentPanel);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.pack();
		
		frame.setVisible(true);
	}
	
	//Getters for labelicons
	public static LabelIcon getLife(){
		return life;
	}
	
	public static LabelIcon getDeath(){
		return death;
	}

}
