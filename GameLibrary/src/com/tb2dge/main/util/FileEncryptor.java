package com.tb2dge.main.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

public class FileEncryptor {
	static HashMap<String,String> encoderKey = new HashMap<String,String>();
	static HashMap<String,String> decoderKey = new HashMap<String,String>();
	String symbols = "!@#$%^&*|?-~()<>[]{}";
	String encrypts = "abcdefghijklmnopqrstuvwxyz1234567890;,.=";
	GameFile file;
	
	public FileEncryptor() {
		setupKey();
		String[] foundKeys = new String[encoderKey.size()];
		int index = 0;
		for (Entry<String,String> key : encoderKey.entrySet()) {
			boolean keyInArray = false;
			for(String foundKey : foundKeys) {
				if(foundKey == null) continue;
				if(foundKey.split(":")[1].equals(key.getValue())) {
					keyInArray = true;
					System.err.println("Warning: Duplicate key found! " + foundKey.split(":")[0] + " and " + key.getKey() + " are duplicates!");
				}
			}
			if(!keyInArray) {
				foundKeys[index] = key.getKey() + ":" + key.getValue();
				index++;
			}
		}
	}
	
	public void setupKey() {
		InputStream encryptKey = FileEncryptor.class.getResourceAsStream("/encryptKey.txt");
		if(encryptKey == null) {
			createEncryptKeyFolder();
			return;
		}
		Scanner fileReader = new Scanner(encryptKey);
		while (fileReader.hasNext()) {
			String line = fileReader.nextLine();
			if (line.equals(""))
				continue;
			if (line.contains("//"))
				continue;
			String[] contents = line.split(":");
			encoderKey.put(contents[0],contents[1]);
			decoderKey.put(contents[1],contents[0]);
		}
		fileReader.close();
	}
	public void createEncryptKeyFolder() {
		File resourceFolder = new File("resource/");
		resourceFolder.mkdirs();
		File encryptKeyDoc = new File("resource/encryptKey.txt");
		try {
			encryptKeyDoc.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LinkedList<String> currentEncrypts = new LinkedList<String>();
		String[] encryptLines = new String[encrypts.length()];
		int index = 0;
		for(char character : encrypts.toCharArray()) {
			String currentEncrypt = "";
			for(int i = 0; i < 14; i++) currentEncrypt+=getRandomChar();
			while(currentEncrypts.contains(currentEncrypt))
				for(int i = 0; i < 8; i++) currentEncrypt+=getRandomChar();
			currentEncrypts.add(currentEncrypt);
			encryptLines[index] = character + ":" + currentEncrypt;
			index++;
		}
		try {
			FileWriter writer = new FileWriter(encryptKeyDoc);
			for(String line : encryptLines) writer.write(line+"\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public char getRandomChar() {
		Random random = new Random();	
		char character = ' ';
		character = symbols.charAt(random.nextInt(symbols.length()));
		return character;
	}
	
	public static void encrypt(GameFile file) {
		String[] lines = file.read();
		String[] newLines = new String[lines.length];
		int stringIndex = 0;
		for(String line : lines) {
			newLines[stringIndex] = "";
			for(char character : line.toCharArray()) {
				if(encoderKey.containsKey(Character.toString(character))) {
					newLines[stringIndex] = 
							newLines[stringIndex] + encoderKey.get(Character.toString(character));
				}
			}
			stringIndex++;
		}
		file.write(newLines);
	}
	public static String[] decrypt(GameFile file) {
		String[] lines = file.read();
		String[] newLines = new String[lines.length];
		int stringIndex = 0;
		for(String line : lines) {
			newLines[stringIndex] = "";
			for(int i = 0; i < line.length() / 14; i++) {
				String key = line.substring(i*14,(i+1)*14);
				if(decoderKey.containsKey(key)) {
					newLines[stringIndex] = newLines[stringIndex] + decoderKey.get(key);
				} else {
					System.out.println("File corrupted! cant read!");
					return null;
				}
			}
			stringIndex++;
		}
		return newLines;
	}
}
