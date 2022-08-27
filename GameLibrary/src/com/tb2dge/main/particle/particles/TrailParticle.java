package com.tb2dge.main.particle.particles;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.tb2dge.main.particle.ParticleHandler;

public class TrailParticle extends Particle {
	Particle[] trailParticles = new Particle[0];
	Random random = new Random();
	double trailAmount;
	
	public TrailParticle(double x, double y, double lifespan, double speed, double size, double trailAmount, Color color,
			ParticleHandler particleHandler) {
		super(x, y, lifespan, speed, size, color, particleHandler);
		this.trailAmount = trailAmount;
	}
	public TrailParticle(double x, double y, double lifespan, double speed, double size, double trailAmount, Color color, BufferedImage image,
			ParticleHandler particleHandler) {
		super(x, y, lifespan, speed, size, image, particleHandler);
		this.trailAmount = trailAmount;
		this.color = color;
	}
	
	@Override
	public void update() {
		super.update();
		int particleAmount = random.nextInt((int)trailAmount);
		trailParticles = new Particle[particleAmount];
		for(int i = 0; i < particleAmount; i++) {
			trailParticles[i] = new Particle(random.nextInt((int)size) + x,random.nextInt((int)(size * 2)) + y,5,speed,size / 3, color, particleHandler);
		}
	}
	@Override
	public void render(Graphics2D g) {
		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float)(1.0 - (1.0 / lifespan)));
		AlphaComposite reset = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1);
		g.setComposite(alpha);
		g.rotate(Math.toRadians(rotation),x + (15/2),y + (24/2));
		if(image!=null) {
			g.drawImage(image, (int)x, (int)y, (int)(image.getWidth() * size), (int)(image.getHeight() * size), null);
		} else {
			g.setColor(color);
			g.fillRect((int)x, (int)y, (int)size, (int)size);
		}
		g.setColor(Color.BLACK);
		for(int i = 0; i < trailParticles.length; i++) trailParticles[i].render(g);
		g.setComposite(reset);
		g.rotate(Math.toRadians(-rotation),x + (15/2),y + (24/2));
	}
}
