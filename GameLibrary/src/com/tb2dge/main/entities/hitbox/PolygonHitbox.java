package com.tb2dge.main.entities.hitbox;

import com.tb2dge.main.math.MathUtils;
import com.tb2dge.main.math.Vector2;

public class PolygonHitbox extends Hitbox {
	Vector2[] vertices;
	public PolygonHitbox(Vector2...vertices) {
		this.vertices = vertices;
		generatePoints();
	}
	@Override
	public boolean collision(Hitbox hitbox) {
		boolean colliding = false;
		for(Vector2 point : hitbox.points) {
			if(MathUtils.pointInPolygon(point, vertices)) {
				colliding = true;
				break;
			}
		}
		return colliding;
	}
	@Override
	public void generatePoints() {
		double length = 0;
		for(int i = 0; i < vertices.length; i++) {
			int i1 = i+1;
			if(i1==vertices.length)i1=0;
			length+=vertices[i].dist(vertices[i1]);
		}
		points = new Vector2[(int)(length)];
		int currentIndex = 0;
		for(int i = 0; i < vertices.length; i++) {
			int i1 = i+1;
			if(i1==vertices.length)i1=0;
			int startingX = (int)vertices[i].getX();
			int startingY = (int)vertices[i].getY();
			int endingX = (int)vertices[i1].getX();
			int endingY = (int)vertices[i1].getY();
			for(int j = 0+currentIndex; j < (int)vertices[i].dist(vertices[i1])+currentIndex; j++) {
				points[j] = new Vector2(startingX+(((endingX-startingX)/vertices[i].dist(vertices[i1]))*(j-currentIndex)),
						startingY+(((endingY-startingY)/vertices[i].dist(vertices[i1]))*(j-currentIndex)));
			}
			currentIndex+=(int)vertices[i].dist(vertices[i1]);
		}
		emptyPoints();
	}
}
