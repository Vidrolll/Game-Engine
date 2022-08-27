package com.tb2dge.main.particle.emitters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.tb2dge.main.particle.ParticleHandler;
import com.tb2dge.main.particle.particles.Particle;
import com.tb2dge.main.particle.particles.SolidParticle;

public class SolidEmitter extends ParticleEmitter {
	double bounciness;
	
	public SolidEmitter(double x, double y, double width, double height, double spread, double rate, double amount,
			double xForceMin, double xForceMax, double yForceMin, double yForceMax, double lifespanMin,
			double lifespanMax, double speed, double rotSpeedMin, double rotSpeedMax, double sizeMin, double sizeMax,
			double bounciness, Color color, ParticleHandler particleHandler) {
		super(x, y, width, height, spread, rate, amount, xForceMin, xForceMax, yForceMin, yForceMax, lifespanMin, lifespanMax,
				speed, rotSpeedMin, rotSpeedMax, sizeMin, sizeMax, color, particleHandler);
		this.bounciness = bounciness;
	}
	public SolidEmitter(double x, double y, double width, double height, double spread, double rate, double amount,
			double xForceMin, double xForceMax, double yForceMin, double yForceMax, double lifespanMin,
			double lifespanMax, double speed, double rotSpeedMin, double rotSpeedMax, double sizeMin, double sizeMax,
			double bounciness, BufferedImage image, ParticleHandler particleHandler) {
		super(x, y, width, height, spread, rate, amount, xForceMin, xForceMax, yForceMin, yForceMax, lifespanMin, lifespanMax,
				speed, rotSpeedMin, rotSpeedMax, sizeMin, sizeMax, image, particleHandler);
		this.bounciness = bounciness;
	}
	
	@Override
	public Particle getParticle(double x, double y) {
		if(image==null)
			return new SolidParticle(x,y,random.nextInt((int)(lifespanMax - lifespanMin)) + lifespanMin,speed,
					random.nextInt((int)(sizeMax - sizeMin)) + sizeMin,bounciness,color,particleHandler);
		else
			return new SolidParticle(x,y,random.nextInt((int)(lifespanMax - lifespanMin)) + lifespanMin,speed,
					random.nextInt((int)(sizeMax - sizeMin)) + sizeMin,bounciness,image,particleHandler);
	}
}
