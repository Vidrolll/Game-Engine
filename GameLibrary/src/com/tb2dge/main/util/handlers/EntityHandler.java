package com.tb2dge.main.util.handlers;

import java.awt.Graphics2D;
import java.util.LinkedList;

import com.tb2dge.main.entities.Entity;

public class EntityHandler {
	LinkedList<Entity> entities = new LinkedList<Entity>();
	
	public void addEntity(Entity entity) {
		entity.setEH(this);
		entities.add(entity);
	}
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	public Entity getEntity(int index) {
		return entities.get(index);
	}
	public void update() {
		for(Entity entity : entities) entity.update();
		for(Entity entity : entities) entity.ai();
	}
	public void render(Graphics2D g) {
		for(Entity entity : entities) entity.render(g);
	}
}
