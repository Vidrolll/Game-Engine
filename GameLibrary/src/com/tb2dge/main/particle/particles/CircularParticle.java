package com.tb2dge.main.particle.particles;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import com.tb2dge.main.particle.ParticleHandler;

public class CircularParticle extends Particle {
	Shape circle;
	
	public CircularParticle(double x, double y, double lifespan, double speed, double size, Color color,
			ParticleHandler particleHandler) {
		super(x, y, lifespan, speed, size, color, particleHandler);
		circle = new Ellipse2D.Double((int)x,(int)y,(int)size,(int)size);
	}
	
	@Override
	public void update() {
		super.update();
		circle = new Ellipse2D.Double((int)x,(int)y,(int)size,(int)size);
	}
	@Override
	public void render(Graphics2D g) {
		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float)(1.0 - (1.0 / lifespan)));
		AlphaComposite reset = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1);
		g.setComposite(alpha);
		g.setColor(color);
		g.fill(circle);
		g.setComposite(reset);
	}
}
