package com.tb2dge.main.graphics.effects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Mirrored {
	public static BufferedImage flipHorizontal(BufferedImage sprite) {
		BufferedImage flippedSprite = new BufferedImage(sprite.getWidth(), sprite.getHeight(),
				BufferedImage.TRANSLUCENT);
		Graphics2D graphics = flippedSprite.createGraphics();
		graphics.drawImage(sprite, 0, 0, null);
		graphics.dispose();
		for (int i = 0; i < sprite.getWidth(); i++) {
			for (int j = 0; j < sprite.getHeight(); j++) {
				int a = sprite.getColorModel().getAlpha(sprite.getRaster().getDataElements(i, j, null));
				int r = sprite.getColorModel().getRed(sprite.getRaster().getDataElements(i, j, null));
				int g = sprite.getColorModel().getGreen(sprite.getRaster().getDataElements(i, j, null));
				int b = sprite.getColorModel().getBlue(sprite.getRaster().getDataElements(i, j, null));
				flippedSprite.setRGB((flippedSprite.getWidth() - 1) - i, j, new Color(r,g,b,a).getRGB());
			}
		}
		return flippedSprite;
	}
	public static BufferedImage flipVertical(BufferedImage sprite) {
		BufferedImage flippedSprite = new BufferedImage(sprite.getWidth(), sprite.getHeight(),
				BufferedImage.TRANSLUCENT);
		Graphics2D graphics = flippedSprite.createGraphics();
		graphics.drawImage(sprite, 0, 0, null);
		graphics.dispose();
		for (int i = 0; i < sprite.getWidth(); i++) {
			for (int j = 0; j < sprite.getHeight(); j++) {
				int a = sprite.getColorModel().getAlpha(sprite.getRaster().getDataElements(i, j, null));
				int r = sprite.getColorModel().getRed(sprite.getRaster().getDataElements(i, j, null));
				int g = sprite.getColorModel().getGreen(sprite.getRaster().getDataElements(i, j, null));
				int b = sprite.getColorModel().getBlue(sprite.getRaster().getDataElements(i, j, null));
				flippedSprite.setRGB(i,(flippedSprite.getHeight() - 1) - j, new Color(r,g,b,a).getRGB());
			}
		}
		return flippedSprite;
	}
}
