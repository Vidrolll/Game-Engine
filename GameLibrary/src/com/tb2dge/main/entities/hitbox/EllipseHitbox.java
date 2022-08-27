package com.tb2dge.main.entities.hitbox;

import com.tb2dge.main.math.Vector2;

public class EllipseHitbox extends Hitbox {
	public Vector2 focalPoint1, focalPoint2;
	int x, y, width, height;
	
	public EllipseHitbox(int x, int y, int width, int height) {
		double a = Math.max(width,height)/2;
		double b = Math.min(width,height)/2;
		double c = Math.sqrt(Math.pow(a,2)-Math.pow(b,2));
		focalPoint1 = (width>height) ? new Vector2(x-c,y) : new Vector2(x,y-c);
		focalPoint2 = (width>height) ? new Vector2(x+c,y) : new Vector2(x,y+c);
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
			if(point.dist(focalPoint1)+point.dist(focalPoint2)<Math.max(width,height)) {
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
			points[(int)(a*5000)]=new Vector2(x+((width/2)*Math.sin(a)),y+((height/2)*Math.cos(a)));
		}
	}
}
