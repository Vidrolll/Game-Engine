package com.tb2dge.main.player;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.tb2dge.main.Game;
import com.tb2dge.main.util.enums.KeyType;
import com.tb2dge.main.util.enums.MouseType;

public abstract class PlayerBase {
	protected double x, y, width, height, velX, velY;
	
	Game game;
	
	public PlayerBase(int x, int y, int width, int height, Game game) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.game = game;
	}
	public abstract void update();
	public abstract void render(Graphics2D g);
	public abstract void input(KeyEvent e, KeyType kt);
	public abstract void input(MouseEvent e, MouseType mt);
	public abstract void collision();
	
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
	public int getWidth() {
		return (int)width;
	}
	public int getHeight() {
		return (int)height;
	}
}
