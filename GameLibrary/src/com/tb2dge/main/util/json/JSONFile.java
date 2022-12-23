package com.tb2dge.main.util.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JSONFile {
	File jsonFile;
	
	public JSONFile(String path) throws JSONException {
		this(new File(path));
	}
	public JSONFile(File file) throws JSONException {
		if(!file.toString().endsWith(".json"))
			throw new JSONException("Inputted file isnt a JSON file!");
		this.jsonFile = file;
	}
	
	public JSONObject[] getObjects() {
		ArrayList<JSONObject> array = new ArrayList<JSONObject>();
		try {
			Scanner fileReader = new Scanner(jsonFile);
			while(fileReader.hasNext()) {
				String line = fileReader.nextLine();
				if(line.replace(" ", "").startsWith("//")) continue;
				if(line.contains("{")) {
					if(firstChar(line)!=-1)
						line = line.substring(firstChar(line));
					ArrayList<String> objectData = new ArrayList<String>();
					while(!line.contains("}")) {
						if(!line.contains("{"))objectData.add(line);
						line = fileReader.nextLine();
						if(firstChar(line)!=-1)
							line = line.substring(firstChar(line));
					}
					String[] data = new String[objectData.size()];
					objectData.toArray(data);
					array.add(new JSONObject(data));
				}
			}
			fileReader.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		JSONObject[] objectArray = new JSONObject[array.size()];
		array.toArray(objectArray);
		return objectArray;
	}
	
	public int firstChar(String string) {
		char[] chars = string.toCharArray();
		boolean lastWhiteSpace = false;
		for(int i = 0; i < string.length(); i++) {
			if(Character.isWhitespace(chars[i])) {
				lastWhiteSpace = true;
			} else if(lastWhiteSpace) {
				return i;
			}
		}
		return -1;
	}
}
