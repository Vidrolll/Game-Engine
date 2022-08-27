package com.tb2dge.main.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.tb2dge.main.Game;
import com.tb2dge.main.util.enums.KeyType;

public class KeyInput implements KeyListener {
	Game game;
	
	public KeyInput(Game game) {
		this.game = game;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		game.input(e,KeyType.KeyTyped);
		game.engineInput(e,KeyType.KeyTyped);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		game.input(e,KeyType.KeyPressed);
		game.engineInput(e,KeyType.KeyPressed);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		game.input(e,KeyType.KeyReleased);
		game.engineInput(e,KeyType.KeyReleased);
	}
}
