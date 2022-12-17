package com.tb2dge.main.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import com.tb2dge.main.Game;

public class Settings {
	private static HashMap<String,String> settings = new HashMap<String,String>();
	private static LinkedList<String> settingsText = new LinkedList<String>();
	
	public Settings() {
		setupSettings();
	}
	
	public static void setupSettings() {
		File file = new File(System.getenv("APPDATA") + "/" + Game.getGameName() + "/settings.txt");
		InputStream settingsFile = null;
		try {
			settingsFile = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(settingsFile == null) {
			setupSettingsFile();
			return;
		}
		Scanner fileReader = new Scanner(settingsFile);
		while (fileReader.hasNext()) {
			String line = fileReader.nextLine();
			settingsText.add(line);
			if (line.equals(""))
				continue;
			if (line.contains("//"))
				continue;
			String[] contents = line.split(":");
			settings.put(contents[0], contents[1]);
		}
		fileReader.close();
	}
	public static void setupSettingsFile() {
		File resourceFolder = new File(System.getenv("APPDATA") + "/" + Game.getGameName());
		resourceFolder.mkdirs();
		File settingsDoc = new File(System.getenv("APPDATA") + "/" + Game.getGameName() + "/settings.txt");
		try {
			settingsDoc.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getSetting(String key) {
		if(settings.get(key) != null)
			return settings.get(key);
		else
			System.err.println("Couldnt find setting " + key + "!");
		return null;
	}
	public static int getInt(String key) {
		return Integer.parseInt(getSetting(key));
	}
	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(getSetting(key));
	}
	public static void createSetting(String key, String value) {
		if(settings.get(key) != null) {
			updateSetting(key,value);
			return;
		}
		try {
			File file = new File(System.getenv("APPDATA") + "/" + Game.getGameName() + "/settings.txt");
			FileWriter writer = new FileWriter(file);
			for(int i = 0; i < settingsText.size(); i++) {
				String line = settingsText.get(i);
				writer.write(line + "\n");
			}
			writer.write(key + ":" + value);
			writer.close();
			settingsText.clear();
			settings.clear();
			setupSettings();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void updateSetting(String key, String value) {
		try {
			File file = new File(System.getenv("APPDATA") + "/" + Game.getGameName() + "/settings.txt");
			FileWriter writer = new FileWriter(file);
			for(int i = 0; i < settingsText.size(); i++) {
				String line = settingsText.get(i);
				if (line.equals("")) {
					writer.write(line + "\n");
					continue;
				}
				if (line.contains("//")) {
					writer.write(line + "\n");
					continue;
				}
				String[] contents = line.split(":");
				if(contents[0].equals(key)) {
					writer.write(key + ":" + value + "\n");
				} else {
					writer.write(line + "\n");
				}
			}
			writer.close();
			settingsText.clear();
			settings.clear();
			setupSettings();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
