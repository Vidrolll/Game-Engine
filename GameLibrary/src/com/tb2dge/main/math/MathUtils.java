package com.tb2dge.main.math;

import java.util.ArrayList;

import com.tb2dge.main.util.ArrayUtils;

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
	public static int[] getFactors(long num) {
		ArrayList<Integer> factors = new ArrayList<Integer>();
		for(double i = 1.0; i < num; i++) {
			if(Math.floor(num/i)==num/i)
				factors.add((int)(num/i));
		}
		return ArrayUtils.toArray(factors);
	}
	public static int[] getCommonFactors(long num1, long num2) {
		ArrayList<Integer> commonFactors = new ArrayList<Integer>();
		int[] num1Factors = (getFactors(num1).length > getFactors(num2).length) ? 
				getFactors(num1) : getFactors(num2);
		int[] num2Factors = (getFactors(num1).length > getFactors(num2).length) ? 
				getFactors(num2) : getFactors(num1);
		for(int i = 0; i < num1Factors.length; i++) {
			if(ArrayUtils.indexOf(num2Factors,num1Factors[i])!=-1)
				commonFactors.add(num1Factors[i]);
		}
		return ArrayUtils.toArray(commonFactors);
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
	public static int greatest(int...nums) {
		int greatestNum = nums[0];
		for(int num : nums) if(num > greatestNum) greatestNum = num;
		return greatestNum;
	}
	public static double greatest(double...nums) {
		double greatestNum = nums[0];
		for(double num : nums) if(num > greatestNum) greatestNum = num;
		return greatestNum;
	}
	public static int smallest(int...nums) {
		int smallestNum = nums[0];
		for(int num : nums) if(num < smallestNum) smallestNum = num;
		return smallestNum;
	}
	public static double smallest(double...nums) {
		double smallestNum = nums[0];
		for(double num : nums) if(num < smallestNum) smallestNum = num;
		return smallestNum;
	}
}
