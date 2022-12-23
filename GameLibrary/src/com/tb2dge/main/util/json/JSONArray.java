package com.tb2dge.main.util.json;

public class JSONArray {
	String[] data;
	
	public JSONArray(String[] data) {
		this.data = data;
	}
	
	public String[] getData() {
		return data;
	}
	public String get(int index) {
		return data[index];
	}
	public int getInt(int index) {
		return Integer.parseInt(data[index]);
	}
	public double getDouble(int index) {
		return Double.parseDouble(data[index]);
	}
}
