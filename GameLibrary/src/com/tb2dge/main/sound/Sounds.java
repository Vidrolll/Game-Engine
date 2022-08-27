package com.tb2dge.main.sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sounds {
	public static HashMap<String, InputStream> sounds = new HashMap<String, InputStream>();
	static boolean muted = false;
	
	public Sounds() {
		setupSounds();
	}

	public void setupSounds() {
		InputStream soundFile = Sound.class.getResourceAsStream("/sounds/sounds.txt");
		if (soundFile == null) {
			createSoundFolder();
			return;
		}
		Scanner fileReader = new Scanner(soundFile);
		while (fileReader.hasNext()) {
			String line = fileReader.nextLine();
			if (line.equals(""))
				continue;
			if (line.contains("//"))
				continue;
			String[] contents = line.split(":");
			InputStream audioFile = Sound.class.getResourceAsStream("/sounds/" + contents[1] + ".wav");
			InputStream audioSource = new BufferedInputStream(audioFile);
			sounds.put(contents[0], audioSource);
		}
		fileReader.close();
	}

	public void createSoundFolder() {
		File resourceFolder = new File("resource/sounds/");
		resourceFolder.mkdirs();
		File soundsDoc = new File("resource/sounds/sounds.txt");
		try {
			soundsDoc.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Sound getSound(String sound) {
		if (muted)return null;
		try{
			InputStream audioFile = sounds.get(sound);
			AudioInputStream audio = AudioSystem.getAudioInputStream(audioFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			return new Sound(clip);
		} catch (IOException|UnsupportedAudioFileException|LineUnavailableException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
		} finally {}
		return null;
	}
}
