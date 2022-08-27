package com.tb2dge.main.gui.elements;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import com.tb2dge.main.gui.GUIHandler;
import com.tb2dge.main.gui.interfaces.Inputable;
import com.tb2dge.main.util.enums.KeyType;
import com.tb2dge.main.util.enums.MouseType;

public class Dropbox extends GUIElement implements Inputable {
	LinkedList<String> options = new LinkedList<String>();
	
	public Dropbox(int x, int y, int width, int height, GUIHandler gHandler, String... objects) {
		super(x, y, width, height, gHandler);
		for(String option : objects) options.add(option);
	}
	
	@Override
	public void input(KeyEvent e, KeyType kt) {
		
	}
	@Override
	public void input(MouseEvent e, MouseType mt, int mouseX, int mouseY) {
		
	}
	@Override
	public boolean isSelected() {
		return false;
	}
	@Override
	public void update() {
		
	}
	@Override
	public void render(Graphics2D g) {
		
	}
}
