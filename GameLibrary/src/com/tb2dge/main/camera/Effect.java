package com.tb2dge.main.camera;

import java.awt.Graphics2D;

import com.tb2dge.main.Game;

public abstract class Effect {
	protected Camera camera;
	protected Game game;
	
	public Effect(Game game, Camera camera) {
		this.camera = camera;
		this.game = game;
	}
	public abstract void update();
	public abstract void render(Graphics2D g);
}
