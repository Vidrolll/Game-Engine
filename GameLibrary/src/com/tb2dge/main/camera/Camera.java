package com.tb2dge.main.camera;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.tb2dge.main.EngineSettings;

public class Camera {
	List<Effect> cameraEffects = new ArrayList<Effect>();
	double xOffset = 0, yOffset = 0, xScale = 1, yScale = 1;
	double xZoom = 0, yZoom = 0, zoomAmount = 0, zoomSpeed = 0;
	double zoomScale = 1;
	
	
	public void render(Graphics2D g) {
		g.translate(xOffset, yOffset);
		if(!(zoomSpeed != 0 || zoomScale != 1))g.scale(xScale, yScale);
		if(zoomSpeed != 0 || zoomScale != 1) {
			g.translate(xZoom, yZoom);
			g.scale(xScale, yScale);
			g.translate(-xZoom, -yZoom);
		}
		for(Effect effect : cameraEffects) effect.render(g);
	}
	public void update() {
		if(zoomSpeed != 0) {
			changeScale(zoomAmount, zoomAmount);
			zoomAmount += zoomSpeed*EngineSettings.getUpdateScale();
		}
		if(zoomScale != 1) {
			setScale(zoomScale, zoomScale);
		}
		for(Effect effect : cameraEffects) effect.update();
	}
	public void setScale(double xScale, double yScale) {
		this.xScale = xScale;
		this.yScale = yScale;
	}

	public void setOffset(double xOffset, double yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void changeScale(double xScale, double yScale) {
		this.xScale += xScale;
		this.yScale += yScale;
	}

	public void changeOffset(double xOffset, double yOffset) {
		this.xOffset += xOffset;
		this.yOffset += yOffset;
	}

	public void zoom(int x, int y, double zoomSpeed, double startingAmount) {
		this.xZoom = x;
		this.yZoom = y;
		this.zoomSpeed = zoomSpeed;
		this.zoomAmount = startingAmount;
	}
	public void zoom(int x, int y, double zoomScale) {
		this.xZoom = x;
		this.yZoom = y;
		this.zoomScale = zoomScale;
	}

	public void reset() {
		xScale = 1;
		yScale = 1;
		zoomSpeed = 0;
		zoomAmount = 0;
	}

	public void addCameraEffect(Effect effect) {
		cameraEffects.add(effect);
	}
	public void removeCameraEffect(Effect effect) {
		cameraEffects.remove(effect);
	}
	public void clearCameraEffects() {
		cameraEffects.clear();
	}
	public double getXOffset() {
		return xOffset;
	}
	public double getYOffset() {
		return yOffset;
	}

	public double getXScale() {
		return xScale;
	}

	public double getYScale() {
		return yScale;
	}
}
