package com.tb2dge.main.player;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.tb2dge.main.util.enums.KeyType;
import com.tb2dge.main.util.enums.MouseType;

public class PlayerHandler {
	PlayerBase player;
	
	public PlayerHandler() {
		
	}
	
	public PlayerBase getPlayer() {
		return player;
	}
	public void spawnPlayer(PlayerBase player) {
		this.player = player;
	}
	public void removePlayer() {
		player = null;
	}
	public void update() {
		if(player != null)player.update();
		if(player != null)player.collision();
	}
	public void render(Graphics2D g) {
		if(player != null)player.render(g);
	}
	public void input(KeyEvent e, KeyType kt) {
		if(player != null)player.input(e,kt);
	}
	public void input(MouseEvent e, MouseType mt) {
		if(player != null)player.input(e,mt);
	}
}
