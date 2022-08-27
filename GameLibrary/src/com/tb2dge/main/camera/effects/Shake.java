package com.tb2dge.main.camera.effects;

import java.awt.Graphics2D;
import java.util.Random;

import com.tb2dge.main.Game;
import com.tb2dge.main.camera.Camera;
import com.tb2dge.main.camera.Effect;

public class Shake extends Effect {
	Random random = new Random();
	int offsetX, offsetY;
	int shakeX, shakeY;
	
	public Shake(Game game, Camera camera, int shakeX, int shakeY) {
		super(game, camera);
		this.shakeX = shakeX;
		this.shakeY = shakeY;
	}

	@Override
	public void update() {
		offsetX = random.nextInt(shakeX) - (shakeX / 2);
		offsetY = random.nextInt(shakeY) - (shakeY / 2);
	}
	@Override
	public void render(Graphics2D g) {
		g.translate(camera.getXOffset() + offsetX, camera.getYOffset() + offsetY);
	}
}
