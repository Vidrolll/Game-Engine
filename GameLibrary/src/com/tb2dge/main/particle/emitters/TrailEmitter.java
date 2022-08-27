package com.tb2dge.main.particle.emitters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.tb2dge.main.particle.ParticleHandler;
import com.tb2dge.main.particle.particles.Particle;
import com.tb2dge.main.particle.particles.TrailParticle;

public class TrailEmitter extends ParticleEmitter {
	double trailAmount;
	
	public TrailEmitter(double x, double y, double width, double height, double spread, double rate, double amount,
			double xForceMin, double xForceMax, double yForceMin, double yForceMax, double lifespanMin,
			double lifespanMax, double speed, double rotSpeedMin, double rotSpeedMax, double sizeMin, double sizeMax,
			double trailAmount, Color color, ParticleHandler particleHandler) {
		super(x, y, width, height, spread, rate, amount, xForceMin, xForceMax, yForceMin, yForceMax, lifespanMin, lifespanMax,
				speed, rotSpeedMin, rotSpeedMax, sizeMin, sizeMax, color, particleHandler);
		this.trailAmount = trailAmount;
	}
	public TrailEmitter(double x, double y, double width, double height, double spread, double rate,
			double amount, double xForceMin, double xForceMax, double yForceMin, double yForceMax, double lifespanMin, 
			double lifespanMax, double speed, double rotSpeedMin, double rotSpeedMax, double sizeMin,
			double sizeMax, double trailAmount, BufferedImage image, ParticleHandler particleHandler) {
		super(x, y, width, height, spread, rate, amount, xForceMin, xForceMax, yForceMin, yForceMax, lifespanMin, lifespanMax,
				speed, rotSpeedMin, rotSpeedMax, sizeMin, sizeMax, image, particleHandler);
		this.trailAmount = trailAmount;
	}
	
	@Override
	public Particle getParticle(double x, double y) {
		return new TrailParticle(x,y,random.nextInt((int)(lifespanMax - lifespanMin)) + lifespanMin,speed,
					random.nextInt((int)(sizeMax - sizeMin)) + sizeMin,trailAmount,color,image,particleHandler);
	}
}
