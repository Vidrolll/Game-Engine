package com.tb2dge.main.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class Lang {
	public static HashMap<String, String> languages = new HashMap<String, String>();
	
	public Lang() {
		setupLanguages();
	}
	
	public void setupLanguages() {
		InputStream languageFile = Lang.class.getResourceAsStream("/lang/languages.lang");
		if(languageFile == null) {
			createLanguageFolder();
			return;
		}
		Scanner fileReader = new Scanner(languageFile);
		while (fileReader.hasNext()) {
			String line = fileReader.nextLine();
			if (line.equals(""))
				continue;
			if (line.contains("//"))
				continue;
			String[] contents = line.split(":");
			InputStream langFile = Lang.class.getResourceAsStream("/lang/" + contents[1]);
			Scanner langScanner = new Scanner(langFile);
			while(langScanner.hasNext()) {
				line = langScanner.nextLine();
				if (line.equals(""))
					continue;
				if (line.contains("//"))
					continue;
				String[] textContents = line.split(":");
				languages.put(contents[0]+"-"+textContents[0], textContents[1]);
			}
			langScanner.close();
		}
		fileReader.close();
	}
	public void createLanguageFolder() {
		File resourceFolder = new File("resource/lang/");
		resourceFolder.mkdirs();
		File languageDoc = new File("resource/lang/languages.lang");
		try {
			languageDoc.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getText(String language, String text) {
		return languages.get(language + "-" + text);
	}
}
