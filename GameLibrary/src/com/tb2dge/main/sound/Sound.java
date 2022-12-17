package com.tb2dge.main.sound;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	long pausedTime;
	Clip sound;
	boolean playing;
	int loops;
	
	public Sound(Clip sound) {
		this.sound = sound;
	}
	
	public void play() {
		sound.setMicrosecondPosition(0);
		sound.start();
		sound.loop(loops);
		playing = true;
	}
	public void play(long microseconds) {
		sound.setMicrosecondPosition(microseconds);
		sound.start();
		sound.loop(loops);
		playing = true;
	}
	public void pause() {
		pausedTime = sound.getMicrosecondPosition();
		sound.stop();
		playing = false;
	}
	public void resume() {
		if(isPlaying()&&loops==0) pause();
		sound.setMicrosecondPosition(pausedTime);
		sound.start();
		sound.loop(loops);
		playing = true;
	}
	public void stop() {
		sound.stop();
		playing = false;
	}
	public void setPosition(long microseconds) {
		sound.setMicrosecondPosition(microseconds);
	}
	public long getPosition() {
		return sound.getMicrosecondPosition();
	}
	public long getLength() {
		return sound.getMicrosecondLength();
	}
	public boolean isPlaying() {
		return sound.isActive();
	}
	public void loop(int loops) {
		this.loops = loops;
	}
	public void setVolume(float volume) {
		FloatControl volumeControl = (FloatControl)sound.getControl(FloatControl.Type.MASTER_GAIN);
		volumeControl.setValue(volume);
	}
	public float getVolume() {
		FloatControl volumeControl = (FloatControl)sound.getControl(FloatControl.Type.MASTER_GAIN);
		return volumeControl.getValue();
	}
	public void setPan(float pan) {
		FloatControl panControl = (FloatControl)sound.getControl(FloatControl.Type.PAN);
		panControl.setValue(pan);
	}
}
