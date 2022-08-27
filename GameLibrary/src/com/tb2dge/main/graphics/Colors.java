package com.tb2dge.main.graphics;

import java.awt.Color;

import com.tb2dge.main.math.MathUtils;

public class Colors {
	public static final Color GREY = new Color(128,128,128);
	public static final Color RED = new Color(255,0,0);
	public static final Color ORANGE = new Color(255,128,0);
	public static final Color YELLOW = new Color(255,255,0);
	public static final Color LIME_GREEN = new Color(128,255,0);
	public static final Color GREEN = new Color(0,255,0);
	public static final Color LIGHT_GREEN = new Color(0,255,128);
	public static final Color CYAN = new Color(0,255,255);
	public static final Color LIGHT_BLUE = new Color(0,128,255);
	public static final Color BLUE = new Color(0,0,255);
	public static final Color PURPLE = new Color(128,0,255);
	public static final Color PINK = new Color(255,0,255);
	
	public static final Color HOT_PINK = new Color(255,0,128);
	
	public static Color getColor(Color baseColor, int brightness) {
		if(brightness > 8) brightness = 8;
		if(brightness < -8) brightness = -8;
		int difference = (51 * brightness)/2;
		if(baseColor==Colors.GREY) difference = (72 * brightness)/2;
		if(baseColor==Colors.GREY && brightness > 0) difference = (64 * brightness)/2;
		int r = baseColor.getRed();
		int g = baseColor.getGreen();
		int b = baseColor.getBlue();
		if(brightness > 0) {
			if(r==0) r = difference;
			if(r==128) r = 128+(difference/2);
			if(g==0) g = difference;
			if(g==128) g = 128+(difference/2);
			if(b==0) b = difference;
			if(b==128) b = 128+(difference/2);
		}
		if(brightness < 0) {
			if(r==128) r = 128+(difference/2)+1;
			if(r==255) r = 255+(difference);
			if(g==128) g = 128+(difference/2)+1;
			if(g==255) g = 255+(difference);
			if(b==128) b = 128+(difference/2)+1;
			if(b==255) b = 255+(difference);
		}
		if(r>255) r = 255;
		if(r<0) r = 0;
		if(g>255) g = 255;
		if(g<0) g = 0;
		if(b>255) b = 255;
		if(b<0) b = 0;
		return new Color(r,g,b);
	}
	
	public static Color getAccurateColor(Color baseColor, int contrast) {
		if(contrast > 127) contrast = 127;
		if(contrast < -127) contrast = -127;
		int r = baseColor.getRed();
		int g = baseColor.getGreen();
		int b = baseColor.getBlue();
		double greatestNum = MathUtils.greatest(r,g,b);
		double redRatio = (contrast<0) ? (double)r/greatestNum : (double)(255-r)/greatestNum;
		double greenRatio = (contrast<0) ? (double)g/greatestNum : (double)(255-g)/greatestNum;
		double blueRatio = (contrast<0) ? (double)b/greatestNum : (double)(255-b)/greatestNum;
		r+=(redRatio*(contrast*2));
		g+=(greenRatio*(contrast*2));
		b+=(blueRatio*(contrast*2));
		if(r>255) r = 255;
		if(r<0) r = 0;
		if(g>255) g = 255;
		if(g<0) g = 0;
		if(b>255) b = 255;
		if(b<0) b = 0;
		return new Color(r,g,b);
	}
}
