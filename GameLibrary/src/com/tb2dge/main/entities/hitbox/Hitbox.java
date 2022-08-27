package com.tb2dge.main.entities.hitbox;

import com.tb2dge.main.math.Vector2;

public abstract class Hitbox {
	public Vector2[] points;
	public abstract boolean collision(Hitbox hitbox);
	public abstract void generatePoints();
	public void emptyPoints() {
		int emptyPoints = 0;
		for(Vector2 vector : points) if(vector == null) emptyPoints++;
		if(emptyPoints != 0) {
			int index = 0;
			Vector2[] newPoints = new Vector2[points.length-emptyPoints];
			for(Vector2 vector : points) {
				if(vector != null) {
					newPoints[index] = vector;
					index++;
				}
			}
			points = newPoints;
		}
	}
	public Vector2 getCollidingPoint(Hitbox hitbox) {
		if(!collision(hitbox)) return null;
		for(Vector2 vector : hitbox.points) {
			for(Vector2 checkVector : points) {
				if(vector.dist(checkVector)<1) 
					return vector;
			}
		}
		return null;
	}
}
