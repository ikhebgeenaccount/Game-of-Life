package com.ihga.game.main;

public class PatternScripter {
	
	private static String delimiter = ContentPanel.getDelimiter();
	
	public static void main(String[] args){
		ContentPanel.loadProperties();
		System.out.print(generateRandom(200000));
	}
	
	public static String getStraightLine(int y){
		String res = "";
		for(int i = 0; i < ContentPanel.getFieldWidth(); i++){
			res += i + "," + y + delimiter;
		}
		return res;
	}
	
	public static String generateRandom(int number){
		String res = "";
		for(int i = 0; i < number; i++){
			int x = Integer.MAX_VALUE, y = Integer.MAX_VALUE;
			
			while(x >= ContentPanel.getFieldWidth()){
				x = (int)Math.round(Math.random() * ContentPanel.getFieldWidth());
			}
			
			while(y >= ContentPanel.getFieldHeight()){
				y = (int)Math.round(Math.random() * ContentPanel.getFieldHeight());
			}			
			
			res += x + "," + y + delimiter;
		}
		return res;
	}

}
