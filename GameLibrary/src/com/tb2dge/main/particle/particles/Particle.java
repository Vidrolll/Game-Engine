package com.tb2dge.main.particle.particles;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.tb2dge.main.EngineSettings;
import com.tb2dge.main.particle.ParticleHandler;
import com.tb2dge.main.particle.emitters.ParticleEmitter;

/**
 *  Creates a new particle object. Takes in a position, a lifespan, a speed,
 *  a size, a {@link BufferedImage}, and a {@link ParticleHandler}.
 *  <p>
 *  Not supplying a particle handler throws a {@link NullPointerException}.
 * @version 20 January 2022
 * @author Caden Vize
 * @see ParticleHandler
 * @see ParticleEmitter
 */

public class Particle {
	/** The current X value of the particle. */
	double x;
	/** The current Y value of the particle. */ 
	double y;
	/** The change in X of a particle. */
	double velX;
	/** The change in Y of a particle. */ 
	double velY; 
	/** How long a particle lasts for. Decreases by 1 every game update. Deletes particle when timer hits 0. */
	double lifespan;
	/** The speed the particle updates position based on the velocity. */ 
	double speed;
	/** The current rotation of the particle. */
	double rotation; 
	/** The change in rotation of a particle. */
	double rotSpeed; 
	/** The size of the particle. */
	double size;
	/** The {@link ParticleHandler} a particle is tied to. */
	ParticleHandler particleHandler;
	/** The {@link BufferedImage} a particle will render. */
	BufferedImage image;
	/** The {@link Color} a particle will render. */
	Color color;
	
	/** The rate of change of a particle in the X direction. */
	public double friction = .98;
	
	/**
	 * Creates a particle at the given x point and the given y point.
	 * <p>
	 * Renders a rectangle, at the given size, at the given color, for the given amount of time.
	 * @param x the X position of the particle.
	 * @param y the Y position of the particle.
	 * @param lifespan the lifespan of the particle. measured in game updates.
	 * @param speed the speed the particle moves.
	 * @param size the size of the particle.
	 * @param color the {@link Color} of the particle.
	 * @param particleHandler the {@link ParticleHandler} tied to the particle.
	 * @see Color
	 * @see ParticleHandler
	 */
	public Particle(double x, double y, double lifespan, double speed, double size, Color color, ParticleHandler particleHandler) {
		this.x = x;
		this.y = y;
		this.lifespan = lifespan;
		this.speed = speed;
		this.size = size;
		this.color = color;
		this.particleHandler = particleHandler;
	}
	/**
	 * Creates a particle at the given x point and the given y point. 
	 * <p>
	 * Renders the given image, at the given size, for the given amount of time.
	 * @param x the X position of the particle.
	 * @param y the Y position of the particle.
	 * @param lifespan the lifespan of the particle. measured in game updates.
	 * @param speed the speed the particle moves.
	 * @param size the size of the particle.
	 * @param image the {@link BufferedImage} the particle displays.
	 * @param particleHandler the {@link ParticleHandler} tied to the particle.
	 * @see ParticleHandler
	 */
	public Particle(double x, double y, double lifespan, double speed, double size, BufferedImage image, ParticleHandler particleHandler) {
		this.x = x;
		this.y = y;
		this.lifespan = lifespan;
		this.speed = speed;
		this.size = size;
		this.image = image;
		this.particleHandler = particleHandler;
	}
	
	/**
	 * Applies a velocity force to a particle.
	 * @param xForce the force applied to the particle. Increases velX by xForce.
	 * @param yForce the force applied to the particle. Increases velY by yForce.
	 */
	public void addForce(double xForce, double yForce) {
		velX += xForce;
		velY += yForce;
	}
	/**
	 * Applies a spinning force to a particle.
	 * @param spin the force applied to the particle. Increases rotation speed by spin.
	 */
	public void addForce(double spin) {
		rotSpeed += spin;
	}
	/**
	 * Updates the particle. Changes position, velocity, rotation, and lifespan.
	 */
	public void update() {
		velY+=(speed / 10.0)*EngineSettings.getUpdateScale();
		velX*=friction*EngineSettings.getUpdateScale();
		rotSpeed-=(Math.signum(rotSpeed) * (speed / 10.0))*EngineSettings.getUpdateScale();
		rotation+=rotSpeed*EngineSettings.getUpdateScale();
		x+=velX*EngineSettings.getUpdateScale();
		y+=velY*EngineSettings.getUpdateScale();
		lifespan-=1.0*EngineSettings.getUpdateScale();
		if(lifespan>=0) particleHandler.removeParticle(this);
	}
	/**
	 * Renders the particle. If theres an image, renders the image. Otherwise it draws a rectangle.
	 * @param g the {@link Graphics2D} object used to render the particle.
	 */
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
		g.setComposite(reset);
		g.rotate(Math.toRadians(-rotation),x + (15/2),y + (24/2));
	}
	/**
	 * The function that runs to see if the particle is colliding with anything.
	 * @param rect the rectangle you wish to check if its colliding with.
	 */
	public void collision(Rectangle rect) {}
}
