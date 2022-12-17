package com.tb2dge.main.graphics.animation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import com.tb2dge.main.util.json.JSONException;
import com.tb2dge.main.util.json.JSONFile;

public class Animations {
	public static HashMap<String,JSONFile> animations = new HashMap<String,JSONFile>();
	
	public Animations() {
		setupAnimations();
	}
	
	public void setupAnimations() {
		InputStream textureFile = Animations.class.getResourceAsStream("/animations/animations.txt");
		if(textureFile == null) {
			createAnimationsFolder();
			return;
		}
		Scanner fileReader = new Scanner(textureFile);
		while (fileReader.hasNext()) {
			String line = fileReader.nextLine();
			if (line.equals(""))
				continue;
			if (line.contains("//"))
				continue;
			String[] contents = line.split(":");
			JSONFile file = null;
			try {
				file = new JSONFile("/animations/" + contents[1] + ".json");
			} catch (JSONException e) {}
			if (!(file == null))
				animations.put(contents[0], file);
			else
				System.err.println("Could not find file " + contents[1] + "!");
		}
		fileReader.close();
	}
	public void createAnimationsFolder() {
		File resourceFolder = new File("resource/animations/");
		resourceFolder.mkdirs();
		File animationsDoc = new File("resource/animations/animations.txt");
		try {
			animationsDoc.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
