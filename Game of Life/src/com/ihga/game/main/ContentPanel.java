package com.ihga.game.main;

import java.awt.Color;
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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ContentPanel extends JPanel implements MouseListener, MouseMotionListener{
	
	private static String delimiter = "c";
	private static int width, height; //Field properties
	private static int size, space; //Cell properties
	
	private GridBagConstraints c;
	
	private int previousX;
	private int previousY;
	
	private int[][] lifeAndDeath;
	private int[][] newLifeAndDeath;
	
	private Image death;
	private Image life;
	
	private boolean mousePressed;
	
	public ContentPanel(){
		loadProperties();
		setLayout(new GridBagLayout());
		
		//Add listeners
		addMouseListener(this);
		addMouseMotionListener(this);
		
		c = new GridBagConstraints();
		c.insets = new Insets(0, 0, space, space);
		
		lifeAndDeath = new int[height][width];
		
		mousePressed = false;
		
		resetField();
		
		//Load images
		loadPics();
		
		setPreferredSize(new Dimension(width * (size + space), height * (size + space)));
	}
	
	private void loadPics(){
		death = new BufferedImage(size,size, BufferedImage.TYPE_INT_RGB);
		Graphics gd = death.getGraphics();
		gd.setColor(Color.BLACK);
		gd.fillRect(0, 0, size, size);
		
		life = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		Graphics gl = life.getGraphics();
		gl.setColor(Color.WHITE);
		gl.fillRect(0, 0, size, size);
	}
	
	public static void loadProperties(){
		Properties properties = new Properties();
		InputStream propertiesFile = Main.class.getClassLoader().getResourceAsStream("settings.cfg");
		try {
			properties.load(propertiesFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		size = Integer.parseInt(properties.getProperty("size"));
		space = Integer.parseInt(properties.getProperty("space"));
		width = Integer.parseInt(properties.getProperty("width"));
		height = Integer.parseInt(properties.getProperty("height"));
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(c.gridx = 0; c.gridx < width; c.gridx++){
			for(c.gridy = 0; c.gridy < height; c.gridy++){
				//Draw LabelIcons
				if(lifeAndDeath[c.gridy][c.gridx] == 0){
					g.drawImage(death, c.gridx * (size + space), c.gridy * (size + space), null);
				}else{
					g.drawImage(life, c.gridx * (size + space), c.gridy * (size + space), null);
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
		
		/*
		 * Rules:
		 * 	1. Any live cell with fewer than two live neighbours dies, as if caused by under-population.
		 * 	2. Any live cell with two or three live neighbours lives on to the next generation.
		 *	3. Any live cell with more than three live neighbours dies, as if by over-population.
		 * 	4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
		 */
		
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
		
		int x = mousePoint.x/(size + space);
		int y = mousePoint.y/(size + space);
		
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
		
		int x = mousePoint.x/(size + space);
		int y = mousePoint.y/(size + space);
		
		//Check if the cell has to brought alive or killed, check if editing is allowed and check if this cell wasn't changed the previous time we changed a cell.
		if(lifeAndDeath[y][x] == 0 && Main.editAllowed() && (previousX != x ^ previousY != y)){
			lifeAndDeath[y][x] = 1;
		}else if(Main.editAllowed() && previousX != x && previousY != y){
			lifeAndDeath[y][x] = 0;
		}
		
		previousX = x;
		previousY = y;
		
		//repaint();
	}
	
	public String getSaveString(){
		String save = "";
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if(lifeAndDeath[y][x] == 1){
					save += x + "," + y + delimiter;
				}
			}
		}
		
		return save;
	}
	
	public void loadString(String load){
		Scanner sc = new Scanner(load);
		sc.useDelimiter(delimiter);
		
		while(sc.hasNext()){
			String coord = sc.next();
			String[] c = coord.split(",");
			
			lifeAndDeath[Integer.parseInt(c[1])][Integer.parseInt(c[0])] = 1;
		}
	}
	
	public static String getDelimiter(){
		return delimiter;
	}
	
	public static int getFieldWidth(){
		return width;
	}
	
	public static int getFieldHeight(){
		return height;
	}
}
