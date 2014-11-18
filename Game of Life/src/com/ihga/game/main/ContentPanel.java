package com.ihga.game.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class ContentPanel extends JPanel {
	
	private GridBagConstraints c;
	
	private int width;
	private int height;
	
	private int[][] lifeAndDeath;
	
	public ContentPanel(){
		setLayout(new GridBagLayout());
		
		c = new GridBagConstraints();
		c.insets = new Insets(1, 1, 1, 1);
		
		width = 25;
		height = 25;
		
		setPreferredSize(new Dimension(width * 27, height * 27));
		
		lifeAndDeath = new int[height][width];
		
		//Filling matrix with death
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				lifeAndDeath[y][x] = 0;
			}
		}
		
		repaint();
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
