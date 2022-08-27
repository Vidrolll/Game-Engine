 package com.tb2dge.main.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GameFile {
	String fileName;
	String location;
	String folder;
	
	File file;
	
	public GameFile(String fileName, String location, String folder) {
		this.fileName = fileName;
		this.location = location;
		this.folder = folder;
		setupFile();
	}
	
	public void setupFile() {
		if(location == FileLocation.APPDATA)
			file = new File(FileLocation.APPDATA + folder + "/" + fileName);
	}
	public void createFile() {
		if(location == FileLocation.APPDATA) {
			File folder = new File(FileLocation.APPDATA + this.folder + "/");
			folder.mkdir();
			try {
				file.createNewFile();
				setupFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public Scanner getScanner() {
		try {
			if(location == FileLocation.APPDATA) {
				File file = new File(FileLocation.APPDATA + folder + "/" + fileName);
				return new Scanner(file);
			}
			if(location == FileLocation.RESOURCE_FOLDER) {
				InputStream file = this.getClass().getResourceAsStream(FileLocation.RESOURCE_FOLDER + folder + fileName);
				return new Scanner(file);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String[] read() {
		List<String> lines = new LinkedList<String>();
		if(getScanner() == null) return null;
		Scanner scanner = getScanner();
		while(scanner.hasNext()) lines.add(scanner.nextLine());
		String[] linesArray = new String[lines.size()];
		for(int i = 0; i < lines.size(); i++)
			linesArray[i] = lines.get(i);
		return linesArray;
	}
	public void write(String line) {
		if(location != FileLocation.APPDATA) return;
		try {
			FileWriter writer = new FileWriter(this.file);
			writer.write(line);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void write(int line, String lineString) {
		if(location != FileLocation.APPDATA) return;
		String[] lines = null;
		if(line < read().length)
			lines = read();
		else {
			lines = new String[line+1];
			for(int i = 0; i < read().length; i++) lines[i] = read()[i]; 
		}
		lines[line] = lineString;
		try {
			FileWriter writer = new FileWriter(this.file);
			for(String currentLine : lines) writer.write(currentLine + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void write(String... lines) {
		if(location != FileLocation.APPDATA) return;
		try {
			FileWriter writer = new FileWriter(this.file);
			for(String currentLine : lines) writer.write(currentLine + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
