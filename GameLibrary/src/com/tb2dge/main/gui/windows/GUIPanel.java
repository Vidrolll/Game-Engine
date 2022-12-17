package com.tb2dge.main.gui.windows;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.tb2dge.main.EngineSettings;
import com.tb2dge.main.gui.GUIHandler;
import com.tb2dge.main.gui.elements.GUIElement;
import com.tb2dge.main.gui.interfaces.Inputable;
import com.tb2dge.main.util.enums.KeyType;
import com.tb2dge.main.util.enums.MouseType;

public class GUIPanel {
	public ArrayList<GUIElement> elements = new ArrayList<GUIElement>();
	public ArrayList<GUIPanel> panels = new ArrayList<GUIPanel>();
	double x,y,width,height,velX,velY;
	int borderThickness = 1;
	
	boolean hideBackground = false;
	boolean hideBorder = false;
	boolean visible = true;
	
	GUIHandler gHandler;
	BufferedImage backgroundImage;
	Color backgroundColor;
	Color borderColor;
	
	public GUIPanel(int x, int y, int width, int height, GUIHandler gHandler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gHandler = gHandler;
	}
	
	public void showBackground(boolean showBackground) {
		hideBackground = !showBackground;
	}
	public void showBorder(boolean showBorder) {
		hideBorder = !showBorder;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setBackgroundImage(BufferedImage image) {
		this.backgroundImage = image;
	}
	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
	}
	public void setBorderColor(Color color) {
		this.borderColor = color;
	}
	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
	}
	public void addElement(GUIElement element) {
		element.setOffsetX((int)x);
		element.setOffsetY((int)y);
		elements.add(element);
	}
	public void removeElement(GUIElement element) {
		elements.remove(element);
	}
	public void addPanel(GUIPanel panel) {
		panels.add(panel);
	}
	public void removePanel(GUIPanel panel) {
		panels.remove(panel);
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
	public void setOffset(int x, int y) {
		for(int i = 0; i < elements.size(); i++) {
			elements.get(i).setOffsetX(x);
			elements.get(i).setOffsetY(y);
		}
	}
	public void changeOffset(int x, int y) {
		for(int i = 0; i < elements.size(); i++) {
			elements.get(i).setOffsetX(elements.get(i).getOffsetX()+x);
			elements.get(i).setOffsetY(elements.get(i).getOffsetY()+y);
		}
	}
	public void movePanel(int x, int y) {
		this.x = x;
		this.y = y;
		for(int i = 0; i < elements.size(); i++) {
			elements.get(i).setOffsetX(x);
			elements.get(i).setOffsetY(y);
		}
	}
	
	public void update() {
		if(!visible) return;
		x+=velX*EngineSettings.getUpdateScale();
		y+=velY*EngineSettings.getUpdateScale();
		for(int i = 0; i < elements.size(); i++) {
			elements.get(i).update();
			elements.get(i).setVelX((int)velX);
			elements.get(i).setVelY((int)velY);
		}
	}
	
	public boolean elementSelected() {
		Class<Inputable> inputable = Inputable.class;
		for(int i = 0; i < elements.size(); i++) {
			Inputable element;
			if(inputable.isAssignableFrom(elements.get(i).getClass())) {
				element = (Inputable)elements.get(i);
				if(element.isSelected()) return true;
			}
		}
		for(GUIPanel panel : panels) if(panel.elementSelected()) return true;
		return false;
	}
	
	public void input(KeyEvent e, KeyType kt) {
		Class<Inputable> inputable = Inputable.class;
		for(int i = 0; i < elements.size(); i++) {
			Inputable element;
			if(inputable.isAssignableFrom(elements.get(i).getClass())) {
				element = (Inputable)elements.get(i);
				if(elements.get(i).isVisible()) element.input(e,kt); 
			}
		}
		for(GUIPanel panel : panels) if(panel.isVisible()) panel.input(e,kt);
	}
	public void input(MouseEvent e, MouseType mt, int mouseX, int mouseY) {
		Class<Inputable> inputable = Inputable.class;
		for(int i = 0; i < elements.size(); i++) {
			Inputable element;
			if(inputable.isAssignableFrom(elements.get(i).getClass())) {
				element = (Inputable)elements.get(i);
				if(elements.get(i).isVisible()) element.input(e,mt,mouseX,mouseY); 
			}
		}
		for(GUIPanel panel : panels) if(panel.isVisible()) panel.input(e,mt,mouseX,mouseY);
	}
	
	public void render(Graphics2D g) {
		if(!visible) return;
		Stroke oldStroke = g.getStroke();
		g.setColor(Color.BLACK);
		g.clipRect((int)x, (int)y, (int)width+1, (int)height+1);
		if(backgroundColor!=null) g.setColor(backgroundColor);
		if(backgroundImage!=null && !hideBackground) g.drawImage(backgroundImage, (int)x, (int)y, (int)width, (int)height, null);
		else if(!hideBackground) g.fillRect((int)x, (int)y, (int)width, (int)height);
		for(int i = 0; i < elements.size(); i++) if(elements.get(i).isVisible()) elements.get(i).render(g);
		for(GUIPanel panel : panels) if(panel.isVisible()) panel.render(g);
		g.setStroke(new BasicStroke(borderThickness));
		if(borderColor!=null) g.setColor(borderColor);
		if(!hideBorder) g.drawRect((int)x, (int)y, (int)width, (int)height);
		g.setStroke(oldStroke);
		g.setClip(null);
	}
}
