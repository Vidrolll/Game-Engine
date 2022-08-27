package com.tb2dge.main.entities.hitbox;

import com.tb2dge.main.math.Vector2;

public class CircleHitbox extends Hitbox {
	Vector2 pos;
	int radius;
	public CircleHitbox(Vector2 pos, int radius) {
		this.pos = pos;
		this.radius = radius;
		generatePoints();
	}
	@Override
	public boolean collision(Hitbox hitbox) {
		boolean colliding = false;
		for(Vector2 point : hitbox.points) {
			if(point.dist(pos)<radius) {
				colliding = true;
				break;
			}
		}
		return colliding;
	}
	@Override
	public void generatePoints() {
		points = new Vector2[(int)Math.ceil(10000*Math.PI)];
		for(double a = 0; a < 2*Math.PI; a+=0.005) {
			points[(int)(a*5000)]=new Vector2(pos.getX()+(radius*Math.sin(a)),pos.getY()+(radius*Math.cos(a)));
		}
		emptyPoints();
	}
}
