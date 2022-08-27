package com.tb2dge.main.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.tb2dge.main.Game;
import com.tb2dge.main.util.enums.MouseType;

public class MouseInput implements MouseListener, MouseMotionListener, MouseWheelListener {
	Game game;
	
	public MouseInput(Game game) {
		this.game = game;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		game.input(e,MouseType.MouseWheelMoved,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
		game.engineInput(e,MouseType.MouseWheelMoved,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		game.input(e,MouseType.MouseDragged,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
		game.engineInput(e,MouseType.MouseDragged,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		game.input(e,MouseType.MouseMoved,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
		game.engineInput(e,MouseType.MouseMoved,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		game.input(e,MouseType.MouseClicked,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
		game.engineInput(e,MouseType.MouseClicked,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
	}
	@Override
	public void mousePressed(MouseEvent e) {
		game.input(e,MouseType.MousePressed,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
		game.engineInput(e,MouseType.MousePressed,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		game.input(e,MouseType.MouseReleased,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
		game.engineInput(e,MouseType.MouseReleased,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		game.input(e,MouseType.MouseEntered,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
		game.engineInput(e,MouseType.MouseEntered,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
	}
	@Override
	public void mouseExited(MouseEvent e) {
		game.input(e,MouseType.MouseExited,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
		game.engineInput(e,MouseType.MouseExited,(int)((double)e.getX()*game.windowScale),(int)((double)e.getY()*game.windowScale));
	}
}
