package com.tb2dge.main.entities.hitbox;

import com.tb2dge.main.math.Vector2;

public class RectangleHitbox extends Hitbox {
	int x, y, width, height;
	public RectangleHitbox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		generatePoints();
	}
	@Override
	public boolean collision(Hitbox hitbox) {
		boolean colliding = false;
		for(Vector2 point : hitbox.points) {
			if(point.getX() > x && point.getX() < x + width && 
			point.getY() > y && point.getY() < y + height) {
				colliding = true;
				break;
			}
		}
		return colliding;
	}
	@Override
	public void generatePoints() {
		points = new Vector2[width+width+height+height];
		for(int i = 0; i < points.length; i+=4) {
			points[i] = new Vector2(x+(i/4),y);
			points[i+1] = new Vector2(x+(i/4),y+height);
			points[i+2] = new Vector2(x,y+(i/4));
			points[i+3] = new Vector2(x+width,y+(i/4));
		}
		emptyPoints();
	}
}
