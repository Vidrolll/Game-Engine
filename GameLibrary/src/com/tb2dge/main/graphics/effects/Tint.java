package com.tb2dge.main.graphics.effects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Tint {
	public static BufferedImage tint(float r, float g, float b, double a, BufferedImage sprite) {
	    int width = sprite.getWidth(null);
	    int height = sprite.getHeight(null);
	    BufferedImage tinted = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    Graphics2D graphics = (Graphics2D) tinted.getGraphics();
	    graphics.drawImage(sprite, 0, 0, width, height, null);
	    Color c = new Color((int)r,(int)g,(int)b,(int)a);
	    Color n = new Color(0,0,0,0);
	    BufferedImage tint = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    for(int i = 0 ; i < width ; i++){
	        for(int j = 0 ; j < height ; j++){
	            if(tinted.getRGB(i, j) != n.getRGB()){
	                tint.setRGB(i, j, c.getRGB());
	            }
	        }
	    }
	    graphics.drawImage(tint, 0, 0, null);
	    graphics.dispose();
	    return tinted;
	}
	public BufferedImage tint(float r, float g, float b, BufferedImage sprite) {
		return tint(r,g,b,255/2,sprite);
	}
	public BufferedImage tint(BufferedImage sprite, Color color) {
		return tint(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha(),sprite);
	}
}
