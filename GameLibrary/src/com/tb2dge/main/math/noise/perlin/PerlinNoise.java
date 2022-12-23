package com.tb2dge.main.math.noise.perlin;

import java.util.Random;

import com.tb2dge.main.math.Vector2;

public class PerlinNoise {
	long seed;
	int tileSize;
	
	public PerlinNoise(long seed) {
		this.seed = seed;
		this.tileSize = 10;
	}
	public PerlinNoise() {
		this(new Random().nextInt());
	}
	
	public void setTileSize(int size) {
		this.tileSize = size;
	}
	public void setSeed(long seed) {
		this.seed = seed;
	}
	
	public float[][] generatePerlin(float x, float y, int width, int height, float frequency) {
		float[][] perlin = new float[width][height];
		for(int x1 = 0; x1 < width; x1++) {
			for(int y1 = 0; y1 < width; y1++) {
				perlin[x1][y1] = perlin(x+((float)x1/tileSize),y+((float)y1/tileSize),frequency);
			}
		}
		return perlin;
	}
	
	public float[][] generateTurbulencePerlin(float x, float y, int width, int height, float frequency, float turbulence) {
		float[][] perlin = new float[width][height];
		for(int x1 = 0; x1 < width; x1++) {
			for(int y1 = 0; y1 < width; y1++) {
				perlin[x1][y1] = perlinTurbulence(x+((float)x1/tileSize),y+((float)y1/tileSize),frequency,turbulence);
			}
		}
		return perlin;
	}
	
	public float[][] generatePerlin(float x, float y, int width, int height) {
		return generatePerlin(x,y,width,height,1);
	}
	public float[][] generatePerlin(int width, int height) {
		return generatePerlin(0,0,width,height,1);
	}
	public float[][] generateTurbulencePerlin(float x, float y, int width, int height, float turbulence) {
		return generateTurbulencePerlin(x,y,width,height,1,turbulence);
	}
	public float[][] generateTurbulencePerlin(int width, int height, float turbulence) {
		return generateTurbulencePerlin(0,0,width,height,1,turbulence);
	}
			
	public float perlin(float x, float y, float frequency) {
		int x0 = fastFloor(x);
		int x1 = x0+1;
		int y0 = fastFloor(y);
		int y1 = y0+1;
		float wx = x-(float)x0;
		float wy = y-(float)y0;
		float d0,d1,i0,i1,i2;
		d0 = dotProduct(x0,y0,x,y);
		d1 = dotProduct(x1,y0,x,y);
		i0 = interpolation(d0,d1,wx);
		d0 = dotProduct(x0,y1,x,y);
		d1 = dotProduct(x1,y1,x,y);
		i1 = interpolation(d0,d1,wx);
		i2 = interpolation(i0,i1,wy)*frequency;
		if(i2>1)i2=1;if(i2<-1)i2=-1;
		return i2;
	}
	
	public float perlinTurbulence(float x, float y, float frequency,float turbulence) {
		float t = 0;
		do {
			t+=perlin(x*turbulence,y*turbulence,frequency)/turbulence;
			turbulence*=0.5f;
		} while(turbulence >= 1);
		return t;
	}
	
	private static int fastFloor(double x) {
		int xi = (int)x;
		return x<xi ? xi-1 : xi;
	}
	
	public Vector2 getGradientVector(int x, int y) {
		long a = x, b = y;
		a *= 2384157443L+seed; b ^= a;
		b *= 1911520717L+seed; a ^= b;
		a *= 2048419325L+seed;
		float value = a * (float)Math.PI;
		return new Vector2((float)Math.cos(value),(float)Math.sin(value));
	}
	
	public float dotProduct(int gx, int gy, float x, float y) {
		Vector2 grad = getGradientVector(gx,gy);
		float dx = (float)(x-gx);
		float dy = (float)(y-gy);
		return (float)((grad.getX()*dx)+(grad.getY()*dy));
	}
	
	public float interpolation(float a0, float a1, float w) {
		return (float)((a1 - a0) * ((w * (w * 6.0 - 15.0) + 10.0) * w * w * w) + a0);
	}
}
