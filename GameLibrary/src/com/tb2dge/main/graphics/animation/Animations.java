package com.tb2dge.main.graphics.animation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import com.tb2dge.main.Game;
import com.tb2dge.main.graphics.Textures;

public class Animations {
	public static HashMap<String, InputStream> animations = new HashMap<String, InputStream>();
	public static Game game;

	public Animations(Game game) {
		setupAnimations();
		Animations.game = game;
	}

	public void setupAnimations() {
		InputStream animFile = Textures.class.getResourceAsStream("/animations/animations.txt");
		if(animFile == null) {
			createAnimFolder();
			return;
		}
		Scanner fileReader = new Scanner(animFile);
		while (fileReader.hasNext()) {
			String line = fileReader.nextLine();
			if (line.equals(""))
				continue;
			if (line.contains("//"))
				continue;
			String[] contents = line.split(":");
			InputStream animStream = Animations.class.getResourceAsStream("/animations/" + contents[1]);
			animations.put(contents[0], animStream);
		}
		fileReader.close();
	}
	public void createAnimFolder() {
		File animFolder = new File("resource/animations/");
		animFolder.mkdirs();
		File animDoc = new File("resource/animations/animations.txt");
		try {
			animDoc.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static InputStream getAnimationFile(String anim) {
		return animations.get(anim);
	}
}
