package com.tb2dge.main.graphics.guianims;

import com.tb2dge.main.EngineSettings;
import com.tb2dge.main.Game;
import com.tb2dge.main.math.Vector2;

public class SlidingAnimation {
	double animTimerX=0;
	double animTimerY=0;
	boolean animating = true;
	
	Vector2 startingPos;
	Vector2 endingPos;
	Vector2 midPos;
	Vector2 speed;
	Game game;
	
	Vector2 currentPos;
	
	public SlidingAnimation(Vector2 startingPos, Vector2 endingPos, Vector2 speed, Game game) {
		this.startingPos = startingPos;
		this.endingPos = endingPos;
		this.speed = speed;
		this.currentPos = new Vector2(startingPos.getX(),startingPos.getY());
		this.game = game;
	}
	
	public void update() {
		if(!animating) return;
		double bx = endingPos.getX() - startingPos.getX();
		double ax = (bx)/(-(Math.pow(speed.getX(),2)))*1.0;
		double by = endingPos.getY() - startingPos.getY();
		double ay = (by)/(-(Math.pow(speed.getY(),2)))*1.0;
		animTimerX+=Math.signum(speed.getX())*EngineSettings.getUpdateScale();
		animTimerY+=Math.signum(speed.getY())*EngineSettings.getUpdateScale();
		if(animTimerX >= speed.getX()) animTimerX = speed.getX();
		if(animTimerY >= speed.getY()) animTimerY = speed.getY();
		if(animTimerX >= speed.getX() && animTimerY >= speed.getY()) animating = false;
		currentPos.setX(startingPos.getX() + (((ax*Math.pow(((animTimerX-speed.getX())),2))+bx)));
		currentPos.setY(startingPos.getY() + (((ay*Math.pow(((animTimerY-speed.getY())),2))+by)));
	}
	
	public Vector2 getPos() {
		return currentPos;
	}
}
