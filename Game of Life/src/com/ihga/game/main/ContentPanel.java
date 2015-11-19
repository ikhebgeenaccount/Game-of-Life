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
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ContentPanel extends JPanel implements MouseListener, MouseMotionListener{
	
	private GridBagConstraints c;
	
	private int width;
	private int height;
	
	private int previousX;
	private int previousY;
	
	private int[][] lifeAndDeath;
	private int[][] newLifeAndDeath;
	
	private Image death;
	private Image life;
	
	private int space;//Space between cells
	
	private boolean mousePressed;
	
	public ContentPanel(){
		setLayout(new GridBagLayout());
		
		//Add listeners
		addMouseListener(this);
		addMouseMotionListener(this);
		
		space = 0;
		
		c = new GridBagConstraints();
		c.insets = new Insets(0, 0, space, space);
		
		//Number of tiles
		width = 190;
		height = 90;
		
		lifeAndDeath = new int[height][width];
		
		mousePressed = false;
		
		resetField();
		
		//Load images
		try{
			death= ImageIO.read(getClass().getClassLoader().getResource("com/ihga/graphics/img/death.png"));
			life = ImageIO.read(getClass().getClassLoader().getResource("com/ihga/graphics/img/life.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		setPreferredSize(new Dimension(width * (10 + space), height * (10 + space)));
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(c.gridx = 0; c.gridx < width; c.gridx++){
			for(c.gridy = 0; c.gridy < height; c.gridy++){
				//Draw LabelIcons
				if(lifeAndDeath[c.gridy][c.gridx] == 0){
					g.drawImage(death, c.gridx * (10 + space), c.gridy * (10 + space), null);
				}else{
					g.drawImage(life, c.gridx * (10 + space), c.gridy * (10 + space), null);
				}
			}
		}
	}
	
	public void simulate(){
		newLifeAndDeath = copyArray(lifeAndDeath);
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				checkCell(x, y);
			}
		}
		
		lifeAndDeath = copyArray(newLifeAndDeath);
	}
	
	/**Returns true if the cell should live in the next generation, false if the cell should be dead
	 * @return
	 */
	private boolean checkCell(int x, int y){
		boolean live = false;
		
		/*
		 * Rules:
		 * 	1. Any live cell with fewer than two live neighbours dies, as if caused by under-population.
		 * 	2. Any live cell with two or three live neighbours lives on to the next generation.
		 *	3. Any live cell with more than three live neighbours dies, as if by over-population.
		 * 	4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
		 */
		
		int sum = 0;
		
		for(int i = x - 1; i < x + 2; i++){
			if(i < lifeAndDeath[0].length && i >= 0){
				for(int j = y - 1; j < y + 2; j++){
					if(j < lifeAndDeath.length && j >= 0){
						sum += lifeAndDeath[j][i];						
					}
				}				
			}
		}
		
		if(sum - 1 < 2 && lifeAndDeath[y][x] == 1){
			//Rule 1: die
			newLifeAndDeath[y][x] = 0;
		}else if((sum - 1 == 2 || sum - 1 == 3) && lifeAndDeath[y][x] == 1){
			//Rule 2: live
			newLifeAndDeath[y][x] = 1;
		}else if(sum - 1 > 3 && lifeAndDeath[y][x] == 1){
			//Rule 3: die
			newLifeAndDeath[y][x] = 0;
		}else if(sum == 3 && lifeAndDeath[y][x] == 0){
			//Rule 4: live
			newLifeAndDeath[y][x] = 1;
		}
		
		return live;
	}
	
	private int[][] copyArray(int[][] src){
		int[][] dest = new int[height][width];
		for(int i = 0; i < height; i++){
			System.arraycopy(src[i], 0, dest[i], 0, src[i].length);
		}
		return dest;
	}
	
	public int getLivingCells(){
		int sum = 0;
		for(int i = 0; i < height; i++){
			sum+= IntStream.of(lifeAndDeath[i]).sum();
		}
		//int sum = IntStream.of(IntStream.of(lifeAndDeath).sum()).sum();
		return sum;
	}
	
	public void resetField(){
		//Filling matrix with death
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				lifeAndDeath[y][x] = 0;
			}
		}
		
	}
	
	//MouseListener implemented methods
	public void mouseClicked(MouseEvent e) {
		Point mousePoint = e.getPoint();
		
		int x = mousePoint.x/(10 + space);
		int y = mousePoint.y/(10 + space);
		
		previousX = x;
		previousY = y;
		
		if(lifeAndDeath[y][x] == 0 && Main.editAllowed()){
			lifeAndDeath[y][x] = 1;
		}else if(Main.editAllowed()){
			lifeAndDeath[y][x] = 0;
		}
		repaint();
	}
	
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		mousePressed = true;
		
	}

	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
		
	}
	
	//MouseMotionListener implemented methods
	public void mouseMoved(MouseEvent e){
		
	}
	
	public void mouseDragged(MouseEvent e){
		Point mousePoint = e.getPoint();
		
		int x = mousePoint.x/(10 + space);
		int y = mousePoint.y/(10 + space);
		
		//Check if the cell has to brought alive or killed, check if editing is allowed and check if this cell wasn't changed the previous time we changed a cell.
		if(lifeAndDeath[y][x] == 0 && Main.editAllowed() && (previousX != x ^ previousY != y)){
			lifeAndDeath[y][x] = 1;
		}else if(Main.editAllowed() && previousX != x && previousY != y){
			lifeAndDeath[y][x] = 0;
		}
		
		previousX = x;
		previousY = y;
	}
}
