package com.tb2dge.main.gui;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import com.tb2dge.main.gui.elements.GUIElement;
import com.tb2dge.main.gui.interfaces.Inputable;
import com.tb2dge.main.gui.windows.GUIPanel;
import com.tb2dge.main.util.enums.KeyType;
import com.tb2dge.main.util.enums.MouseType;

public class GUIHandler {
	public LinkedList<GUIElement> elements = new LinkedList<GUIElement>();
	public LinkedList<GUIPanel> panels = new LinkedList<GUIPanel>();
	
	public void addElement(GUIElement element) {
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
	public void update() {
		for(int i = 0; i < elements.size(); i++) if(elements.get(i).isVisible()) elements.get(i).update();
		for(int i = 0; i < panels.size(); i++) panels.get(i).update();
	}
	public void render(Graphics2D g) {
		for(int i = 0; i < elements.size(); i++) if(elements.get(i).isVisible()) elements.get(i).render(g);
		for(int i = 0; i < panels.size(); i++) panels.get(i).render(g);
	}
	public boolean selected() {
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
				if(elements.get(i).isVisible())element.input(e,kt);
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
				if(elements.get(i).isVisible())element.input(e,mt,mouseX,mouseY);
			}
		}
		for(GUIPanel panel : panels) if(panel.isVisible()) panel.input(e,mt,mouseX,mouseY);
	}
}
