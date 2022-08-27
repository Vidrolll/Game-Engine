package com.tb2dge.main.math;

public class MathUtils {
	public static double getDegrees(int x1, int y1, int x2, int y2) {
		int yLength = y2 - y1;
		int xLength = x2 - x1;
		int difference = 0;
		if(x2-x1>0&&y2-y1<0)difference=0;
		if(x2-x1>0&&y2-y1>0)difference=90;
		if(x2-x1<0&&y2-y1>0)difference=180;
		if(x2-x1<0&&y2-y1<0)difference=270;
		if(x2-x1<0&&y2-y1==0)difference=270;
		if(x2-x1==0&&y2-y1>0)difference=180;
		if(x2-x1==0&&y2-y1==0) return 0;
		double hyp = Math.hypot(x1-x2, y1-y2);
		double asin = yLength/hyp;
		double acos = xLength/hyp;
		double radians = Math.abs(Math.asin(asin))+Math.toRadians(difference);
		if(difference == 0 || difference == 180) radians = Math.abs(Math.asin(acos))+Math.toRadians(difference);
		return Math.toDegrees(radians);
	}
	private static float sign(Vector2 p1,Vector2 p2,Vector2 p3) {
		return (float)((p1.x-p3.x)*(p2.y-p3.y)-(p2.x-p3.x)*(p1.y-p3.y));
	}
	public static boolean pointInPolygon(Vector2 pt, Vector2...vertices) {
		float[] d = new float[vertices.length];
		boolean has_neg = false, has_pos = false;
		for(int i = 0; i < vertices.length; i++) {
			int i1 = i+1;
			if(i1==vertices.length)i1=0;
			d[i] = sign(pt,vertices[i],vertices[i1]);
			if(d[i]<0) has_neg = true;
			if(d[i]>0) has_pos = true;
		}
		return !(has_neg && has_pos);
	}
	public static double map(double actualMin, double actualMax, double newMin, double newMax, double currentValue) {
		double newValue = 0;
		return newValue;
	}
	public static double greatest(double...nums) {
		double greatestNum = nums[0];
		for(double num : nums) if(num > greatestNum) greatestNum = num;
		return greatestNum;
	}
}
