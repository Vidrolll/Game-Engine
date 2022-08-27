package com.tb2dge.main.util.handlers;

import java.awt.Graphics2D;
import java.util.LinkedList;

import com.tb2dge.main.entities.Object;

public class ObjectHandler {
	LinkedList<Object> objects = new LinkedList<Object>();
	
	public void addObject(Object object) {
		object.setOBH(this);
		objects.add(object);
	}
	public void removeObject(Object object) {
		objects.remove(object);
	}
	public Object getObject(int index) {
		return objects.get(index);
	}
	public void update() {
		for(Object object : objects) object.update();
	}
	public void render(Graphics2D g) {
		for(Object object : objects) object.render(g);
	}
}
