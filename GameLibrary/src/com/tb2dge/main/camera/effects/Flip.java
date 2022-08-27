package com.tb2dge.main.camera.effects;

import java.awt.Graphics2D;

import com.tb2dge.main.Game;
import com.tb2dge.main.camera.Camera;
import com.tb2dge.main.camera.Effect;

public class Flip extends Effect {
	double flipX = 1, flipY = 1;
	double offsetX = 0, offsetY = 0;
	
	public Flip(Game game, Camera camera, boolean flipX, boolean flipY) {
		super(game,camera);
		if(flipX) {
			this.flipX = -1;
			this.offsetX = -game.resX;
		}
		if(flipY) {
			this.flipY = -1;
			this.offsetY = -game.resY;
		}
	}
	
	@Override
	public void update() {}
	@Override
	public void render(Graphics2D g) {
		g.scale(camera.getXScale() * flipX, camera.getYScale() * flipY);
		g.translate(-camera.getXOffset() + offsetX, -camera.getYOffset() + offsetY);
	}
}
