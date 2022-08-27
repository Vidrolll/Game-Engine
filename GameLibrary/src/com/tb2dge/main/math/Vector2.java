package com.tb2dge.main.math;

public class Vector2 {
	double x;
	double y;
	
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void set(Vector2 vector) {
		this.x = vector.x;
		this.y = vector.y;
	}
	public void add(double x, double y) {
		this.x += x;
		this.y += y;
	}
	public void add(Vector2 vector) {
		this.x += vector.x;
		this.y += vector.y;
	}
	public void sub(double x, double y) {
		this.x -= x;
		this.y -= y;
	}
	public void sub(Vector2 vector) {
		this.x -= vector.x;
		this.y -= vector.y;
	}
	public void mul(double x, double y) {
		this.x *= x;
		this.y *= y;
	}
	public void mul(Vector2 vector) {
		this.x *= vector.x;
		this.y *= vector.y;
	}
	public void div(double x, double y) {
		this.x /= x;
		this.y /= y;
	}
	public void div(Vector2 vector) {
		this.x /= vector.x;
		this.y /= vector.y;
	}
	public double dist(int x, int y) {
		return Math.hypot((this.x-x), (this.y-y));
	}
	public double dist(Vector2 vector) {
		return Math.hypot(this.x-vector.x, this.y-vector.y);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
}
