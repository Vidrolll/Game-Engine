package com.tb2dge.main.particle.emitters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.tb2dge.main.EngineSettings;
import com.tb2dge.main.particle.ParticleHandler;
import com.tb2dge.main.particle.particles.Particle;

public class ParticleEmitter {
	double rate,amount,x,y,width,height,spread,lifespanMin,lifespanMax,speed,
	timer,xForceMin,xForceMax,yForceMin,yForceMax,rotSpeedMin,rotSpeedMax, sizeMin, sizeMax;
	ParticleHandler particleHandler;
	Random random = new Random();
	BufferedImage image;
	Color color;
	
	public ParticleEmitter(double x, double y, double width, double height, double spread, double rate,
			double amount, double xForceMin, double xForceMax, double yForceMin, double yForceMax, double lifespanMin, 
			double lifespanMax, double speed, double rotSpeedMin, double rotSpeedMax, double sizeMin,
			double sizeMax, Color color, ParticleHandler particleHandler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.spread = spread;
		this.rate = rate;
		this.amount = amount;
		this.xForceMin = xForceMin;
		this.xForceMax = xForceMax;
		this.yForceMin = yForceMin;
		this.yForceMax = yForceMax;
		this.lifespanMin = lifespanMin;
		this.lifespanMax = lifespanMax;
		this.rotSpeedMin = rotSpeedMin;
		this.rotSpeedMax = rotSpeedMax;
		this.speed = speed;
		this.sizeMin = sizeMin;
		this.sizeMax = sizeMax;
		this.color = color;
		this.particleHandler = particleHandler;
	}
	public ParticleEmitter(double x, double y, double width, double height, double spread, double rate,
			double amount, double xForceMin, double xForceMax, double yForceMin, double yForceMax, double lifespanMin, 
			double lifespanMax, double speed, double rotSpeedMin, double rotSpeedMax, double sizeMin,
			double sizeMax, BufferedImage image, ParticleHandler particleHandler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.spread = spread;
		this.rate = rate;
		this.amount = amount;
		this.xForceMin = xForceMin;
		this.xForceMax = xForceMax;
		this.yForceMin = yForceMin;
		this.yForceMax = yForceMax;
		this.lifespanMin = lifespanMin;
		this.lifespanMax = lifespanMax;
		this.rotSpeedMin = rotSpeedMin;
		this.rotSpeedMax = rotSpeedMax;
		this.speed = speed;
		this.sizeMin = sizeMin;
		this.sizeMax = sizeMax;
		this.image = image;
		this.particleHandler = particleHandler;
	}
	
	public void update() {
		timer+=1.0*EngineSettings.getUpdateScale();
		if(timer >= rate) {
			timer = 0;
			for(int i = 0; i < random.nextInt((int)amount); i++) {
				double x = this.x + ((width / 2) + random.nextInt((int)spread) - spread / 2);
				double y = this.y + ((height / 2) + random.nextInt((int)spread) - spread / 2);
				Particle particle = getParticle(x,y);
				particle.addForce((random.nextDouble() * (xForceMin - xForceMax)) + xForceMax,
						(random.nextDouble() * (yForceMin - yForceMax)) - yForceMax);
				if(rotSpeedMax - rotSpeedMin != 0)
					particle.addForce(random.nextInt((int)((rotSpeedMax + 1) - rotSpeedMin)) + rotSpeedMin);
				particleHandler.addParticle(particle);
			}
		}
	}
	public Particle getParticle(double x, double y) {
		if(image==null)
			return new Particle(x,y,random.nextInt((int)(lifespanMax - lifespanMin)) + lifespanMin,speed,
					random.nextInt((int)(sizeMax - sizeMin)) + sizeMin,color,particleHandler);
		else
			return new Particle(x,y,random.nextInt((int)(lifespanMax - lifespanMin)) + lifespanMin,speed,
					random.nextInt((int)(sizeMax - sizeMin)) + sizeMin,image,particleHandler);
	}
}
