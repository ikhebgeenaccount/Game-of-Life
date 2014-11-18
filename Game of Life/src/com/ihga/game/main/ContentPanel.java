package com.ihga.game.main;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class ContentPanel extends JPanel {
	
	private GridBagConstraints c;
	
	private int width;
	private int height;
	
	private int[][] lifeAndDeath;
	
	public ContentPanel(){
		setLayout(new GridBagLayout());
		
		c = new GridBagConstraints();
		
		width = 50;
		height = 50;
		
		lifeAndDeath = new int[height][width];
		
		//Filling matrix with death
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				lifeAndDeath[y][x] = 0;
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		for(c.gridx = 0; c.gridx < width; c.gridx++){
			for(c.gridy = 0; c.gridy < height; c.gridy++){
				//Draw LabelIcons
				if(lifeAndDeath[c.gridy][c.gridy] == 0){
					add(Main.getDeath(), c);
				}else{
					add(Main.getLife(), c);
				}
			}
		}
		g.dispose();
	}

}
