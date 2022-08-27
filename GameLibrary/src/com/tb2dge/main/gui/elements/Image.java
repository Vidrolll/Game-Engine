package com.tb2dge.main.gui.elements;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.tb2dge.main.EngineSettings;
import com.tb2dge.main.gui.GUIHandler;

public class Image extends GUIElement {
	BufferedImage image;
	
	public Image(int x, int y, int width, int height, BufferedImage image, GUIHandler gHandler) {
		super(x, y, width, height, gHandler);
		this.image = image;
	}
	public Image(int x, int y, int width, int height, String image, GUIHandler gHandler) {
		super(x, y, width, height, gHandler);
		try {
			this.image = ImageIO.read(new File(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	@Override
	public void update() {
		x+=velX*EngineSettings.getUpdateScale();
		y+=velY*EngineSettings.getUpdateScale();
	}
	@Override
	public void render(Graphics2D g) {
		if(image!=null) g.drawImage(image,xOffset+(int)x,yOffset+(int)y,width,height,null);
	}
}
