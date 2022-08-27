package com.tb2dge.main.gui.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.tb2dge.main.gui.GUIHandler;

public abstract class GUIElement {
	protected double x,y,velX,velY;
	protected int width,height,xOffset,yOffset;
	protected boolean visible = true;
	protected GUIHandler gHandler;
	boolean noBorder = false;
	boolean noBackground = false;
	int borderThickness = 1;
	
	Color backgroundColor = Color.WHITE;
	Color borderColor = Color.BLACK;
	BufferedImage imageBackground;
	
	public GUIElement(int x, int y, int width, int height, GUIHandler gHandler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gHandler = gHandler;
	}
	
	public abstract void update();
	public abstract void render(Graphics2D g);

	public int getX() {
		return (int)x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return (int)y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getOffsetX() {
		return xOffset;
	}
	public void setOffsetX(int xOffset) {
		this.xOffset = xOffset;
	}
	public int getOffsetY() {
		return yOffset;
	}
	public void setOffsetY(int yOffset) {
		this.yOffset = yOffset;
	}
	public int getVelX() {
		return (int)velX;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	public int getVelY() {
		return (int)velY;
	}
	public void setVelY(int velY) {
		this.velY = velY;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	public void setBorder(boolean noBorder) {
		this.noBorder = !noBorder;
	}
	public void setBackground(boolean noBackground) {
		this.noBackground = !noBackground;
	}
	public void setImageBackground(BufferedImage image) {
		this.imageBackground = image;
	}
	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
	}
}
