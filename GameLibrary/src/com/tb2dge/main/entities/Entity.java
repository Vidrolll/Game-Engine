package com.tb2dge.main.entities;

import com.tb2dge.main.util.handlers.EntityHandler;

public abstract class Entity extends Object {
	EntityHandler eh;
	public Entity(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	public abstract void ai();
	public void setEH(EntityHandler eh) {
		this.eh = eh;
	}
}
