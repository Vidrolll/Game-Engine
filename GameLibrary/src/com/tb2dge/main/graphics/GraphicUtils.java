package com.tb2dge.main.graphics;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import com.tb2dge.main.math.Vector2;

public class GraphicUtils {
	Graphics2D g;
	
	public GraphicUtils(Graphics2D g) {
		this.g = g;
	}
	
	public void setAlpha(double alphaValue) {
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float)alphaValue);
		g.setComposite(ac);
	}
	public void rotate(Vector2 pivotPoint, int degrees) {
		g.translate(pivotPoint.getX(),pivotPoint.getY());
		g.rotate(Math.toRadians(degrees));
		g.translate(-pivotPoint.getX(),-pivotPoint.getY());
	}
	public void zoom(Vector2 pos, double zoom) {
		g.translate(pos.getX(),pos.getY());
		g.scale(zoom,zoom);
		g.translate(-pos.getX(),-pos.getY());
	}
	public void drawPolygon(Vector2... vertices) {
		int[] xPoints = new int[vertices.length];
		int[] yPoints = new int[vertices.length];
		for(int i = 0; i < vertices.length; i++) {
			xPoints[i] = (int)vertices[i].getX();
			yPoints[i] = (int)vertices[i].getY();
		}
		g.drawPolygon(xPoints,yPoints,vertices.length);
	}
	public void fillPolygon(Vector2... vertices) {
		int[] xPoints = new int[vertices.length];
		int[] yPoints = new int[vertices.length];
		for(int i = 0; i < vertices.length; i++) {
			xPoints[i] = (int)vertices[i].getX();
			yPoints[i] = (int)vertices[i].getY();
		}
		g.fillPolygon(xPoints,yPoints,vertices.length);
	}
}
