package com.tb2dge.main.gui.windows;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainWindow {
	public JFrame window;
	boolean fullscreen = false;
	int width, height;
	Component main;
	String title;
	
	public MainWindow(String title, int width, int height, Component main) {
		window = new JFrame(title);
		window.setMinimumSize(new Dimension(width,height));
		window.setMaximumSize(new Dimension(width,height));
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.pack();
		window.add(main);
		this.title = title;
		this.width = width;
		this.height = height;
		this.main = main;
	}
	public void setUndecorated(boolean undecorated) {
		window.dispose();
		window.setUndecorated(undecorated);
		window.setVisible(true);
	}
	public void toggleFullscreen() {
		fullscreen = !fullscreen;
		window.dispose();
		if(fullscreen) {
			window = new JFrame(title);
			window.setMinimumSize(new Dimension(width,height));
			window.setMaximumSize(new Dimension(width,height));
			window.setLocationRelativeTo(null);
			window.setExtendedState(JFrame.MAXIMIZED_BOTH);
			window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			window.setUndecorated(true);
			window.setVisible(true);
			window.add(main);
		} else {
			window = new JFrame(title);
			window.setMinimumSize(new Dimension(width,height));
			window.setMaximumSize(new Dimension(width,height));
			window.setLocationRelativeTo(null);
			window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			window.setVisible(true);
			window.pack();
			window.add(main);
		}
	}
	public void addComponent(Component comp) {
		window.add(comp);
		window.pack();
	}
	public void removeComponent(Component comp) {
		window.remove(comp);
		window.pack();
	}
	public void addContent(Component comp) {
		window.getContentPane().add(comp);
		window.pack();
	}
	public void removeContent(Component comp) {
		window.getContentPane().remove(comp);
		window.pack();
	}
	public double getWindowScale() {
		double height1 = Toolkit.getDefaultToolkit().getScreenSize().height;
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		double height2 = gd.getDisplayMode().getHeight();
		return height2/height1;
	}
}
