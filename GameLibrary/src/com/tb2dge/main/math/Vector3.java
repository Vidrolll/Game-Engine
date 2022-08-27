package com.tb2dge.main.math;

public class Vector3 {
	double x;
	double y;
	double z;
	
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void set(Vector3 vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}
	public void add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	public void add(Vector3 vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
	}
	public void sub(double x, double y, double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}
	public void sub(Vector3 vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
	}
	public void mul(double x, double y, double z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
	}
	public void mul(Vector3 vector) {
		this.x *= vector.x;
		this.y *= vector.y;
		this.z *= vector.z;
	}
	public void div(double x, double y, double z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
	}
	public void div(Vector3 vector) {
		this.x /= vector.x;
		this.y /= vector.y;
		this.z /= vector.z;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
}
