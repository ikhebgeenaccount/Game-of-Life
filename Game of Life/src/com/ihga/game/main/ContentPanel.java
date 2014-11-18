package com.ihga.game.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ContentPanel extends JPanel {
	
	private GridBagConstraints c;
	
	private int width;
	private int height;
	
	private int[][] lifeAndDeath;
	
	private ImageIcon death;
	private ImageIcon life;
	
	public ContentPanel(){
		setLayout(new GridBagLayout());
		
		c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 1, 1);
		
		width = 75;
		height = 60;
		
		lifeAndDeath = new int[height][width];
		
		//Filling matrix with death
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				lifeAndDeath[y][x] = 0;
			}
		}
		
		death = new ImageIcon(getClass().getClassLoader().getResource("com/ihga/graphics/img/death.png"));
		life = new ImageIcon(getClass().getClassLoader().getResource("com/ihga/graphics/img/life.png"));
		
		for(c.gridx = 0; c.gridx < width; c.gridx++){
			for(c.gridy = 0; c.gridy < height; c.gridy++){
				//Draw LabelIcons
				if(lifeAndDeath[c.gridy][c.gridy] == 0){
					add(new LabelIcon(death, c.gridx, c.gridy, 0), c);
				}else{
					add(new LabelIcon(life, c.gridx, c.gridy, 1), c);
				}
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
					add(new LabelIcon(death, c.gridx, c.gridy, 0), c);
				}else{
					add(new LabelIcon(life, c.gridx, c.gridy, 1), c);
				}
			}
		}
		
		System.out.println("repaint");
	}
	
	public void setSquare(int x, int y, int set){
		lifeAndDeath[y][x] = set;
	}

}
