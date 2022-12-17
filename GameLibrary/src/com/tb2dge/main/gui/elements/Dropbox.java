package com.tb2dge.main.gui.elements;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.tb2dge.main.gui.GUIHandler;
import com.tb2dge.main.gui.interfaces.Inputable;
import com.tb2dge.main.util.enums.KeyType;
import com.tb2dge.main.util.enums.MouseType;

public class Dropbox extends GUIElement implements Inputable {
	ArrayList<String> options = new ArrayList<String>();
	ArrayList<Button> buttons = new ArrayList<Button>();
	
	public Dropbox(int x, int y, int width, int height, GUIHandler gHandler, String...options) {
		super(x, y, width, height, gHandler);
		for(String option : options) this.options.add(option);
	}
	
	@Override
	public void input(KeyEvent e, KeyType kt) {
		for(Button button : buttons) button.input(e,kt);
	}
	@Override
	public void input(MouseEvent e, MouseType mt, int mouseX, int mouseY) {
		for(Button button : buttons) button.input(e,mt,mouseX,mouseY);
	}
	@Override
	public boolean isSelected() {
		return false;
	}
	@Override
	public void update() {
		for(Button button : buttons) button.update();
	}
	@Override
	public void render(Graphics2D g) {
		for(Button button : buttons) button.render(g);
	}
}
