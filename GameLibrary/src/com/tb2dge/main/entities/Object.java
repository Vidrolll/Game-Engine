package com.tb2dge.main.entities;

import java.awt.Graphics2D;

import com.tb2dge.main.entities.hitbox.Hitbox;
import com.tb2dge.main.util.handlers.ObjectHandler;

public abstract class Object {
	double x, y, width, height;
	ObjectHandler obh;
	
	public Object(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setOBH(ObjectHandler obh) {
		this.obh = obh;
	}

	public abstract void update();
	public abstract Hitbox[] hitbox();
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
	public int getWidth() {
		return (int)width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return (int)height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
