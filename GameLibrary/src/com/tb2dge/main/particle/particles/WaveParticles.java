package com.tb2dge.main.particle.particles;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.tb2dge.main.EngineSettings;
import com.tb2dge.main.particle.ParticleHandler;

/**
 *  Creates a new waving particle object. Takes in a position, a lifespan, a speed,
 *  a size, a waving amount, a {@link BufferedImage}, and a {@link ParticleHandler}.
 *  <p>
 *  Not supplying a particle handler, a color, or an image throws a {@link NullPointerException}.
 * @version 20 January 2022
 * @author Caden Vize
 * @see ParticleHandler
 * @see WaveEmitter
 */

public class WaveParticles extends Particle {
	/** The magnitude the particle waves at. */
	double waveAmount;
	
	/**
	 * Creates a waving particle at the given x point and the given y point.
	 * <p>
	 * Renders a rectangle, at the given size, at the given color, for the given amount of time.
	 * @param x the X position of the particle.
	 * @param y the Y position of the particle.
	 * @param lifespan the lifespan of the particle. measured in game updates.
	 * @param speed the speed the particle moves.
	 * @param size the size of the particle.
	 * @param waveAmount the magnitude the particle waves at.
	 * @param color the {@link Color} of the particle.
	 * @param particleHandler the {@link ParticleHandler} tied to the particle.
	 */
	public WaveParticles(double x, double y, double lifespan, double speed, double size, double waveAmount, Color color,
			ParticleHandler particleHandler) {                                          
		super(x, y, lifespan, speed, size, color, particleHandler);
		this.waveAmount = waveAmount;
	}
	/**
	 * Creates a waving particle at the given x point and the given y point. 
	 * <p>
	 * Renders the given image, at the given size, for the given amount of time.
	 * @param x the X position of the particle.
	 * @param y the Y position of the particle.
	 * @param lifespan the lifespan of the particle. measured in game updates.
	 * @param speed the speed the particle moves.
	 * @param size the size of the particle.
	 * @param waveAmount the magnitude the particle waves at.
	 * @param image the {@link BufferedImage} the particle displays.
	 * @param particleHandler the {@link ParticleHandler} tied to the particle.
	 * @see ParticleHandler
	 */
	public WaveParticles(double x, double y, double lifespan, double speed, double size, double waveAmount, BufferedImage image,
			ParticleHandler particleHandler) {
		super(x, y, lifespan, speed, size, image, particleHandler);
		this.waveAmount = waveAmount;
	}
	/** Updates the particle. Changes position, velocity, rotation, and lifespan. Changes position based on the magnitude of the wave amount */
	@Override
	public void update() {
		super.update();
		x+=(Math.sin(System.currentTimeMillis() / 120) * waveAmount)*EngineSettings.getUpdateScale();
	}
}
