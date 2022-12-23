package com.tb2dge.main.util.json;

import java.util.ArrayList;
import java.util.HashMap;

public class JSONObject {
	HashMap<String,String> data = new HashMap<String,String>();
	HashMap<String,JSONArray> arrays = new HashMap<String,JSONArray>();
	
	public JSONObject(String[] data) {
		setupData(data);
	}
	
	public void setupData(String[] data) {
		for(int i = 0; i < data.length; i++) {
			String line = data[i];
			if(line.contains(":")&&!line.contains("[")) {
				String key = line.substring(1,line.indexOf(":")-1);
				int endIndex = (!line.endsWith(",")) ? 1 : 2;
				String value = line.substring(line.indexOf(":")+2,line.length()-endIndex);
				this.data.put(key,value);
			} else if(line.contains(":")) {
				ArrayList<String> lines = new ArrayList<String>();
				String key = line.substring(1,line.replace(" ", "").indexOf(":")-1);
				i++;
				line = data[i];
				while(!line.endsWith("]") && !line.endsWith("],")) {
					int endIndex = line.length()-1;
					if(line.endsWith(",")) endIndex--;
					lines.add(line.substring(1,endIndex));
					i++;
					line = data[i];
				}
				String[] linesArray = new String[lines.size()];
				lines.toArray(linesArray);
				this.arrays.put(key,new JSONArray(linesArray));
			}
		}
	}
	
	public String get(String key) {
		return data.get(key);
	}
	public int getInt(String key) {
		return Integer.parseInt(data.get(key));
	}
	public double getDouble(String key) {
		return Double.parseDouble(data.get(key));
	}
	public boolean getBool(String key) {
		return Boolean.parseBoolean(data.get(key));
	}
	public JSONArray getArray(String key) {
		return arrays.get(key);
	}
}
