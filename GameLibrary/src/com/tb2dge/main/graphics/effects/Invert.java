package com.tb2dge.main.graphics.effects;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Invert {
	public static BufferedImage invert(BufferedImage image) {
        BufferedImage input = image;
        BufferedImage output = new BufferedImage(
            input.getWidth(), input.getHeight(),
            BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < input.getHeight() - 0; x++) {
            for (int y = 0; y < input.getWidth() - 0; y++) {
            	Color c = new Color(image.getRGB(x, y));
            	int red = 255 - c.getRed();
            	int green = 255 - c.getGreen();
            	int blue = 255 - c.getBlue();
            	Color newColor = new Color(red,green,blue);
                output.setRGB(x, y, newColor.getRGB());
            }
        }
        return output;
	}
}
