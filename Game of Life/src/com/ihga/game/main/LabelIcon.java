package com.ihga.game.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LabelIcon extends JLabel {
	
	private int x, y, lifeOrDeath;
	private static boolean mousePressed;
	
	private static ImageIcon death, life;
	
	public LabelIcon(ImageIcon image, int xset, int yset, int type){
		this.x = xset;
		this.y = yset;
		this.lifeOrDeath = type;
		
		death = new ImageIcon(getClass().getClassLoader().getResource("com/ihga/graphics/img/death.png"));
		life = new ImageIcon(getClass().getClassLoader().getResource("com/ihga/graphics/img/life.png"));
		
		setIcon(image);
		
		addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(Main.editAllowed()){
					if(lifeOrDeath == 0){
						Main.getContentPanel().setSquare(x, y, 1);
						setIcon(life);
					}else{
						Main.getContentPanel().setSquare(x, y, 0);
						setIcon(death);
					}
					mousePressed = true;
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(Main.editAllowed()){
					if(mousePressed){
						if(lifeOrDeath == 0){
							Main.getContentPanel().setSquare(x, y, 1);
						}else{
							Main.getContentPanel().setSquare(x, y, 0);
						}	
					}
				}
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				mousePressed = false;
				
			}
			
		});
	}

}
