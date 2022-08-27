package com.tb2dge.main.gui;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.tb2dge.main.Game;
import com.tb2dge.main.gui.elements.GUIElement;
import com.tb2dge.main.util.enums.KeyType;
import com.tb2dge.main.util.enums.MouseType;

public abstract class Menu {
	public GUIHandler guiHandler;
	int offsetX, offsetY;
	boolean isShowing;
	Game game;
	
	public Menu(Game game) {
		this.game = game;
		this.guiHandler = new GUIHandler();
	}
	
	protected void addElement(GUIElement element) {
		guiHandler.addElement(element);
	}
	protected void removeElement(GUIElement element) {
		guiHandler.removeElement(element);
	}
	
	public void setOffset(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	public void setShowing(boolean isShowing) {
		this.isShowing = isShowing;
	}
	public boolean isShowing() {
		return isShowing;
	}
	public int getOffsetX() {
		return offsetX;
	}
	public int getOffsetY() {
		return offsetY;
	}
	public void engineUpdate() {
		guiHandler.update();
		update();
	}
	public void engineRender(Graphics2D g) {
		g.translate(getOffsetX(),getOffsetY());
		render(g);
		guiHandler.render(g);
		g.translate(-getOffsetX(),-getOffsetY());
	}
	public void engineInput(KeyEvent e, KeyType kt) {
		guiHandler.input(e,kt);
		input(e,kt);
	}
	public void engineInput(MouseEvent e, MouseType mt, int mouseX, int mouseY) {
		guiHandler.input(e,mt,mouseX,mouseY);
		input(e,mt,mouseX,mouseY);
	}
	
	public abstract void update();
	public abstract void render(Graphics2D g);
	public abstract void input(KeyEvent e, KeyType kt);
	public abstract void input(MouseEvent e, MouseType mt, int mouseX, int mouseY);
}
