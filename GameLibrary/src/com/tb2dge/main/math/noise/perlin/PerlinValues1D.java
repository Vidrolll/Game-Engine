package com.tb2dge.main.math.noise.perlin;

public class PerlinValues1D {
	double[] values;
	int index = -1;
	public PerlinValues1D(double[] values) {
		this.values = values;
	}
	public double next() {
		index++;
		return values[index];
	}
}
