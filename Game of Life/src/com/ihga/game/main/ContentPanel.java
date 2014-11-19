package com.ihga.game.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ContentPanel extends JPanel {
	
	private GridBagConstraints c;
	
	private int width;
	private int height;
	
	private int[][] lifeAndDeath;
	
	private Image death;
	private Image life;
	
	private boolean mousePressed;
	
	public ContentPanel(){
		setLayout(new GridBagLayout());
		
		c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 1, 1);
		
		//Number of tiles
		width = 90;
		height = 50;
		
		lifeAndDeath = new int[height][width];
		
		mousePressed = false;
		
		//Filling matrix with death
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				lifeAndDeath[y][x] = 0;
			}
		}
		
		//Load images
		try{
			death = ImageIO.read(getClass().getClassLoader().getResource("com/ihga/graphics/img/death.png"));
			life = ImageIO.read(getClass().getClassLoader().getResource("com/ihga/graphics/img/life.png"));
		}catch(IOException e){
			e.printStackTrace();
		}	
		
		addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				Point mousePoint = arg0.getPoint();
				
				int x = mousePoint.x/11;
				int y = mousePoint.y/11;
				
				System.out.println("(x, y) | (" + x + "," + y + ")");
				
				if(lifeAndDeath[y][x] == 0){
					lifeAndDeath[y][x] = 1;
					System.out.println("Life");
				}else{
					lifeAndDeath[y][x] = 0;
					System.out.println("Death");
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				mousePressed = true;
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				mousePressed = false;
				
			}
			
		});
		
		setPreferredSize(new Dimension(width * 11, height * 11));
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		for(c.gridx = 0; c.gridx < width; c.gridx++){
			for(c.gridy = 0; c.gridy < height; c.gridy++){
				//Draw LabelIcons
				if(lifeAndDeath[c.gridy][c.gridx] == 0){
					g.drawImage(death, c.gridx * 11, c.gridy * 11, null);
				}else{
					g.drawImage(life, c.gridx * 11, c.gridy * 11, null);
				}
			}
		}
	}
	
	public void simulate(){
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				int sum = 0;
				for(int x2 = x; x2 < x + 2; x2++){
					for(int y2 = y; y2 < y + 2; y2++){
						try{
							sum += lifeAndDeath[y2 + y][x2 + x];
						}catch(ArrayIndexOutOfBoundsException e){
							
						}
					}
				}
				if(sum == 2 || sum == 3){
					lifeAndDeath[y][x] = 1;
				}else{
					lifeAndDeath[y][x] = 0;
				}
			}
		}
	}

}
