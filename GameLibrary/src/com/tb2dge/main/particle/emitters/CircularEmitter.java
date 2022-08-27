package com.tb2dge.main.particle.emitters;

import java.awt.Color;

import com.tb2dge.main.particle.ParticleHandler;
import com.tb2dge.main.particle.particles.CircularParticle;
import com.tb2dge.main.particle.particles.Particle;

public class CircularEmitter extends ParticleEmitter {
	public CircularEmitter(double x, double y, double width, double height, double spread, double rate, double amount,
			double xForceMin, double xForceMax, double yForceMin, double yForceMax, double lifespanMin,
			double lifespanMax, double speed, double rotSpeedMin, double rotSpeedMax, double sizeMin, double sizeMax,
			Color color, ParticleHandler particleHandler) {
		super(x, y, width, height, spread, rate, amount, xForceMin, xForceMax, yForceMin, yForceMax, lifespanMin, lifespanMax,
				speed, rotSpeedMin, rotSpeedMax, sizeMin, sizeMax, color, particleHandler);
	}
	@Override
	public Particle getParticle(double x, double y) {
		return new CircularParticle(x,y,random.nextInt((int)(lifespanMax - lifespanMin)) + lifespanMin,speed,
				random.nextInt((int)(sizeMax - sizeMin)) + sizeMin,color,particleHandler);
	}
}
