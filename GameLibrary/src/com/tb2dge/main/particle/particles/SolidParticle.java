package com.tb2dge.main.particle.particles;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.tb2dge.main.particle.ParticleHandler;

public class SolidParticle extends Particle {
	double bounciness;
	
	public SolidParticle(double x, double y, double lifespan, double speed, double size, double bounciness, Color color,
			ParticleHandler particleHandler) {
		super(x, y, lifespan, speed, size, color, particleHandler);
		this.bounciness = bounciness;
	}
	public SolidParticle(double x, double y, double lifespan, double speed, double size, double bounciness, BufferedImage image,
			ParticleHandler particleHandler) {
		super(x, y, lifespan, speed, size, image, particleHandler);
		this.bounciness = bounciness;
	}
	
	@Override
	public void collision(Rectangle rect) {
		if(intersects(new Point((int)x + (int) size / 2, (int)y),rect) || 
				intersects(new Point((int)x + (int) size / 2, (int)y + (int)size),rect)) {
			velY*=-1*bounciness;
			if(Math.abs(rect.getY() - y) < Math.abs((rect.getY() + rect.getHeight()) - y)) {
				y = rect.getY() - size;
			} else {
				y = rect.getY() + rect.getHeight();
			}
		}
		if(intersects(new Point((int)x, (int)y + (int)size / 2),rect) ||
				intersects(new Point((int)x + (int)size, (int)y + (int)size / 2),rect)) {
			velX*=-1*bounciness;
		}
	}
	public boolean intersects(Point point, Rectangle rect) {
		return rect.contains(point);
	}
}
