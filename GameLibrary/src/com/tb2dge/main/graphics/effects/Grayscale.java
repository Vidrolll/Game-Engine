package com.tb2dge.main.graphics.effects;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Grayscale {
	public BufferedImage grayScale(BufferedImage image) {
		int width = image.getWidth(null);
	    int height = image.getHeight(null);
	    BufferedImage gScaled = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    for(int i = 0 ; i < width ; i++){
	        for(int j = 0 ; j < height ; j++){
		    	Color c = new Color(image.getRGB(i, j));
                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);
                int color = red+green+blue;
		    	Color g = new Color(color,color,color);
			    Color n = new Color(0,0,0,0);
	            if(image.getRGB(i, j) != n.getRGB()){
	            	gScaled.setRGB(i, j, g.getRGB());
	            }
	        }
	    }
	    return gScaled;
	}
}
