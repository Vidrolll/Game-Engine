package com.tb2dge.main.particle;

import java.awt.Graphics2D;
import java.util.LinkedList;

import com.tb2dge.main.particle.emitters.ParticleEmitter;
import com.tb2dge.main.particle.particles.Particle;

public class ParticleHandler {
	public LinkedList<Particle> particles = new LinkedList<Particle>();
	public LinkedList<ParticleEmitter> particleEmitters = new LinkedList<ParticleEmitter>();
	
	public void addParticle(Particle particle) {
		particles.add(particle);
	}
	public void removeParticle(Particle particle) {
		particles.remove(particle);
	}
	public void addParticleEmitters(ParticleEmitter particleEmitter) {
		particleEmitters.add(particleEmitter);
	}
	public void removeParticleEmitters(ParticleEmitter particleEmitter) {
		particleEmitters.remove(particleEmitter);
	}
	public void update() {
		for(int i = 0; i < particles.size(); i++) particles.get(i).update();
		for(int i = 0; i < particleEmitters.size(); i++) particleEmitters.get(i).update();
		collision();
	}
	public void render(Graphics2D g) {
		for(int i = 0; i < particles.size(); i++) particles.get(i).render(g);
	}
	public void collision() {}
}
