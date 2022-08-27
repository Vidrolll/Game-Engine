package com.tb2dge.main.gui.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.tb2dge.main.EngineSettings;
import com.tb2dge.main.gui.GUIHandler;
import com.tb2dge.main.gui.interfaces.Inputable;
import com.tb2dge.main.util.enums.KeyType;
import com.tb2dge.main.util.enums.MouseType;

public class Button extends GUIElement implements Inputable {
	Runnable function;
	
	public Button(int x, int y, int width, int height, GUIHandler gHandler) {
		super(x, y, width, height, gHandler);
	}
	
	public void setFunction(Runnable function) {
		this.function = function;
	}
	@Override
	public void input(KeyEvent e, KeyType kt) {}
	@Override
	public void input(MouseEvent e, MouseType mt, int mouseX, int mouseY) {
		if(e.getID() != MouseEvent.MOUSE_CLICKED) return;
		if(new Rectangle((int)x+xOffset,(int)y+yOffset,width,height).contains(new Point(mouseX,mouseY)) && e.getButton() == 1) {
			if(function!=null)function.run();
		}
	}
	@Override
	public void update() {
		x+=velX*EngineSettings.getUpdateScale();
		y+=velY*EngineSettings.getUpdateScale();
	}
	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		if(!noBackground)g.fillRect(xOffset+(int)x, yOffset+(int)y, width, height);
		g.setColor(Color.BLACK);
		if(!noBorder)g.drawRect(xOffset+(int)x, yOffset+(int)y, width, height);
	}
	@Override
	public boolean isSelected() {
		return false;
	}
}
