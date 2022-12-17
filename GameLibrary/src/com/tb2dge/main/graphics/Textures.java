package com.tb2dge.main.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.tb2dge.main.util.Console;

public class Textures {
	public static HashMap<String, BufferedImage> textures = new HashMap<String, BufferedImage>();

	public Textures() {
		setupTextures();
	}

	public static void setupTextures() {
		InputStream textureFile = Textures.class.getResourceAsStream("/textures/textures.txt");
		if(textureFile == null) {
			createTextureFolder();
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
			BufferedImage image = null;
			try {
				image = ImageIO.read(Textures.class.getResourceAsStream("/textures/" + contents[1] + ".png"));
			} catch (Exception e) {
				Console.log("Couldnt find " + contents[0]);
			}
			if (!(image == null))
				textures.put(contents[0], image);
			else
				System.err.println("Could not find file " + contents[0] + "!");
		}
		fileReader.close();
	}
	public static HashMap<String, BufferedImage> setupTextures(String textureFilePath) {
		HashMap<String, BufferedImage> textures = new HashMap<String, BufferedImage>();
		InputStream textureFile = Textures.class.getResourceAsStream("/textures/"+textureFilePath+".txt");
		if(textureFile == null) {
			createTextureFolder();
			return null;
		}
		Scanner fileReader = new Scanner(textureFile);
		while (fileReader.hasNext()) {
			String line = fileReader.nextLine();
			if (line.equals(""))
				continue;
			if (line.contains("//"))
				continue;
			String[] contents = line.split(":");
			BufferedImage image = null;
			try {
				image = ImageIO.read(Textures.class.getResourceAsStream("/textures/" + contents[1] + ".png"));
			} catch (Exception e) {
				Console.log("Couldnt find " + contents[0]);
			}
			if (!(image == null))
				textures.put(contents[0], image);
			else
				System.err.println("Could not find file " + contents[0] + "!");
		}
		fileReader.close();
		return textures;
	}
	public static void createTextureFolder() {
		File resourceFolder = new File("resource/textures/");
		resourceFolder.mkdirs();
		File texturesDoc = new File("resource/textures/textures.txt");
		try {
			texturesDoc.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static BufferedImage getTexture(String texture) {
		if (textures.get(texture) != null) {
			return textures.get(texture);
		} else {
			System.err.println("Couldnt find texture " + texture + "!");
		}
		return null;
	}
}
