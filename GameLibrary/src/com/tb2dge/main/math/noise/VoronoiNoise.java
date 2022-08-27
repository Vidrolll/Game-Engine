package com.tb2dge.main.math.noise;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.tb2dge.main.math.Vector2;

public class VoronoiNoise {
	Random rand;
	long seed;
	
	public VoronoiNoise(long seed) {
		this.seed = seed;
		this.rand = new Random(seed);
	}
	public VoronoiNoise() {
		Random seed = new Random();
		this.seed = seed.nextLong();
		this.rand = new Random(this.seed);
	}
	
	public long getSeed() {
		return seed;
	}
	
	public BufferedImage toBufferedImage(double[][] data, int intensity) {
		BufferedImage image = new BufferedImage(data.length,data[0].length,BufferedImage.TRANSLUCENT);
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
		    	double value = (data[x][y]*intensity);
		    	if(value > 1) value = 1;
		    	if(value < -1) value = -1;
		    	int color = (int)((255/2)*(value+1));
		    	image.setRGB(x, 0, new Color(color,color,color).getRGB());
			}
		}
		return image;
	}
	
	public double[][] generateVoronoiCells(int width, int height, int pointCount) {
		double[][] grid = new double[width][height];
		Vector2[] points = new Vector2[pointCount];
		double multiplier = 1/(new Vector2(width,height).dist(0,0)/5);
		double fixMultiplier = 1.0/255;
		for(int i = 0; i < points.length; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			points[i] = new Vector2(x,y);
		}
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				Vector2 pos = new Vector2(x,y);
				Vector2 closest = points[0];
				for(int i = 1; i < points.length; i++) {
					double newDist = pos.dist(points[i]);
					if(newDist < closest.dist(pos)) closest = points[i];
				}
				grid[x][y] = (pos.dist(closest)*multiplier)+(rand.nextDouble()*fixMultiplier);
				if(grid[x][y] > 1) grid[x][y] = 1;
				if(grid[x][y] < 0) grid[x][y] = 0;
			}
		}
		return grid;
	}
	public double[][] generateVoronoiCells(int width, int height, int pointCount, int closestCount) {
		double[][] grid = new double[width][height];
		Vector2[] points = new Vector2[pointCount];
		double multiplier = 1/(new Vector2(width,height).dist(0,0)/5);
		double fixMultiplier = 1.0/255;
		for(int i = 0; i < points.length; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			points[i] = new Vector2(x,y);
		}
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				Vector2 pos = new Vector2(x,y);
				Vector2[] closest = new Vector2[3];
				closest[0] = points[0];
				closest[1] = points[1];
				closest[2] = points[2];
				for(int i = 1; i < points.length; i++) {
					double newDist = pos.dist(points[i]);
					if(newDist < closest[0].dist(pos)) closest[0] = points[i];
					else if(newDist < closest[1].dist(pos)) closest[1] = points[i];
					else if(newDist < closest[2].dist(pos)) closest[2] = points[i];
				}
				grid[x][y] = (pos.dist(closest[closestCount])*multiplier)+(rand.nextDouble()*fixMultiplier);
				if(grid[x][y] > 1) grid[x][y] = 1;
				if(grid[x][y] < 0) grid[x][y] = 0;
			}
		}
		return grid;
	}
	public double[][] generateVoronoiPanes(int width, int height, int pointCount) {
		double[][] grid = new double[width][height];
		Vector2[] points = new Vector2[pointCount];
		double multiplier = 1.0/(double)pointCount;
		for(int i = 0; i < points.length; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			points[i] = new Vector2(x,y);
		}
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				Vector2 pos = new Vector2(x,y);
				Vector2 closest = points[0];
				int index = 0;
				for(int i = 1; i < points.length; i++) {
					double newDist = pos.dist(points[i]);
					if(newDist < closest.dist(pos)) {
						closest = points[i];
						index = i;
					}
				}
				grid[x][y] = (index*multiplier);
				if(grid[x][y] > 1) grid[x][y] = 1;
				if(grid[x][y] < 0) grid[x][y] = 0;
			}
		}
		return grid;
	}
}
