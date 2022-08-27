package com.tb2dge.main.graphics.effects;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;

public class Scramble {
	public static BufferedImage scramble(BufferedImage scrambleImage, int percent) {
		if(percent > 100 || percent < 0) percent = 0;
		BufferedImage scrambledImage = new BufferedImage(scrambleImage.getWidth() , scrambleImage.getHeight(), BufferedImage.TRANSLUCENT);
		double pixels = scrambledImage.getWidth() * scrambledImage.getHeight() * (percent / 100.0);
		String[] pixelPos = new String[scrambledImage.getWidth() * scrambledImage.getHeight()];
		String[] scramblePos = new String[scrambledImage.getWidth() * scrambledImage.getHeight()];
		int index = 0;
		for(int x = 0; x < scrambledImage.getWidth(); x++) {
			for(int y = 0; y < scrambledImage.getHeight(); y++) {
				scrambledImage.setRGB(x, y, scrambleImage.getRGB(x, y));
			}
		}
		for(int x = 0; x < scrambledImage.getWidth(); x++) {
			for(int y = 0; y < scrambledImage.getHeight(); y++) {
				pixelPos[index] = x + ":" + y;
				index++;
			}
		}
		for(int i = 0; i < pixelPos.length; i++) scramblePos[i] = pixelPos[i];
		Collections.shuffle(Arrays.asList(scramblePos));
		for(int i = 0; i < (int)pixels; i++) {
			Color color = new Color(scrambleImage.getRGB(Integer.parseInt(pixelPos[i].split(":")[0]),
					Integer.parseInt(pixelPos[i].split(":")[1])));
			scrambledImage.setRGB(Integer.parseInt(scramblePos[i].split(":")[0]),
					Integer.parseInt(scramblePos[i].split(":")[1]), color.getRGB());	
		}
		return scrambledImage;
	}
	public BufferedImage scramble(BufferedImage scrambleImage) {
		return scramble(scrambleImage,50);
	}
}
