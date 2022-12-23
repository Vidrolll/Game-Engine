package com.tb2dge.main.util.json;

import java.util.ArrayList;

import com.tb2dge.main.util.ArrayUtils;
import com.tb2dge.main.util.GameFile;

public class JSONWriter {
	ArrayList<String> lines = new ArrayList<String>();
	GameFile file;
	int line;
	
	public JSONWriter(GameFile file) {
		this.file = file;
	}
	
	public void startObject() {
		lines.add("{");
	}
	public void endObject() {
		lines.add("}");
	}
	public void addValue(String key, String value, boolean last) {
		String endToken = (last) ? "" : ",";
		lines.add("    \""+key+"\":\""+value+"\""+endToken);
	}
	public void addArray(String key, String...values) {
		lines.add("    \""+key+"\":[");
		for(int i = 0; i < values.length; i++) {
			String endToken = (i != values.length-1) ? "," : "";
			lines.add("        \""+values[i]+"\""+endToken);
		}
		lines.add("    ]");
	}
	
	public void createFile() {
		file.write(ArrayUtils.toArray(lines,String.class));
	}
}
