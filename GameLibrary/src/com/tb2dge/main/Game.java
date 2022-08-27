package com.tb2dge.main;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.Map.Entry;

import com.tb2dge.main.camera.Camera;
import com.tb2dge.main.commands.Commands;
import com.tb2dge.main.graphics.Textures;
import com.tb2dge.main.graphics.animation.Animations;
import com.tb2dge.main.gui.GUIHandler;
import com.tb2dge.main.gui.Menu;
import com.tb2dge.main.gui.TeamLogo;
import com.tb2dge.main.sound.Sounds;
import com.tb2dge.main.time.Timers;
import com.tb2dge.main.util.Console;
import com.tb2dge.main.util.FileEncryptor;
import com.tb2dge.main.util.KeyInput;
import com.tb2dge.main.util.Lang;
import com.tb2dge.main.util.MouseInput;
import com.tb2dge.main.util.Settings;
import com.tb2dge.main.util.enums.KeyType;
import com.tb2dge.main.util.enums.MouseType;
import com.tb2dge.main.util.handlers.EntityHandler;
import com.tb2dge.main.util.handlers.ObjectHandler;

public class Game extends Canvas implements Runnable {
	protected HashMap<String,Menu> menus = new HashMap<String,Menu>();
	private static final long serialVersionUID = 1L;
	public static String gameName;

	protected boolean running = false;
	public boolean showGUI = true; 
	protected Thread thread;
	protected boolean printFPS = true;
	public double resX = 1;
	public double resY = 1;
	public double windowX = 1;
	public double windowY = 1;
	boolean garbageCollection = false;
	public double windowScale = 1;
	
	public Game(String gameName) {
		Game.gameName = gameName;
		new EngineClasses();
		EngineClasses.main = this;
		EngineClasses.timers = new Timers();
		EngineClasses.camera = new Camera();
		EngineClasses.logo = new TeamLogo(this);
		EngineClasses.eh = new EntityHandler();
		EngineClasses.obh = new ObjectHandler();
		EngineClasses.gh = new GUIHandler();
		EngineClasses.cmds = new Commands();
		setup();
		addKeyListener(new KeyInput(this));
		addMouseListener(new MouseInput(this));
		addMouseMotionListener(new MouseInput(this));
		addMouseWheelListener(new MouseInput(this));
		start();
	}
	
	public void setupResources() {
		EngineClasses.sounds = new Sounds();
		EngineClasses.textures = new Textures();
		EngineClasses.animations = new Animations(this);
		EngineClasses.settings = new Settings();
		EngineClasses.fileEncryptor = new FileEncryptor();
		EngineClasses.lang = new Lang();
	}
	
	public void setup() {}
	
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double ns = 1000000000 / EngineSettings.UPDATE_RATE;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (frames < EngineSettings.FPS_LIMIT) {
				while (delta >= 1) {
					EngineClasses.logo.update();
					EngineClasses.timers.update();
					EngineClasses.camera.update();
					EngineClasses.eh.update();
					EngineClasses.obh.update();
					EngineClasses.gh.update();
					for(Entry<String,Menu> menu : menus.entrySet())
						if(menu.getValue().isShowing()) menu.getValue().update();
					update();
					delta--;
				}
				draw();
				frames++;
				FramerateLimit.sync(EngineSettings.FPS_LIMIT);
			} else {
				if(printFPS)Console.log(frames);
				timer += 1000;
				frames = 0;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				if(printFPS)Console.log(frames);
				if(garbageCollection)System.gc();
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}
	public void garbageCollection(boolean garbageCollection) {
		this.garbageCollection = garbageCollection;
	}
	public synchronized void start() {
		thread = new Thread(this);
		running = true;
		thread.start();
	}
	public void addMenu(Menu menu, String menuName) {
		menus.put(menuName, menu);
	}
	public Menu getMenu(String menuName) {
		return menus.get(menuName);
	}
	public void setMenu(String menuName) {
		for(Entry<String,Menu> menu : menus.entrySet())menu.getValue().setShowing(false);
		menus.get(menuName).setShowing(true);
	}
	public void showGUI(boolean show) {
		showGUI = show;
	}
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void setResScale(double resX, double resY, double windowX, double windowY, double windowScale) {
		this.resX = resX;
		this.resY = resY;
		this.windowX = windowX/windowScale;
		this.windowY = windowY/windowScale;
		this.windowScale = windowScale;
	}
	public void printFPS(boolean value) {
		printFPS = value;
	}
	public static String getGameName() {
		return gameName;
	}
	public void draw() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		try {
			Graphics2D g = (Graphics2D)bs.getDrawGraphics();
			g.scale(windowX / resX * 1.0, windowY / resY * 1.0);
			drawBackground(g);
			EngineClasses.camera.render(g);
			drawForeground(g);
			EngineClasses.obh.render(g);
			EngineClasses.eh.render(g);
			g.dispose();
			g = (Graphics2D)bs.getDrawGraphics();
			g.scale(windowX / resX * 1.0, windowY / resY * 1.0);
			for(Entry<String,Menu> menu : menus.entrySet()) {
				Menu tempMenu = menu.getValue();
				if(tempMenu.isShowing()) tempMenu.engineRender(g);
			}
			if(showGUI)EngineClasses.gh.render(g);
			drawGUIOverlay(g);
			EngineClasses.logo.render(g);
			g.dispose();
			bs.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void update() {}
	public void drawBackground(Graphics2D g) {}
	public void drawForeground(Graphics2D g) {}
	public void drawGUIOverlay(Graphics2D g) {}
	public void input(KeyEvent e, KeyType kt) {}
	public void input(MouseEvent e, MouseType mt, int mouseX, int mouseY) {}
	public void engineInput(KeyEvent e, KeyType kt) {
		for(Entry<String,Menu> menu : menus.entrySet())if(menu.getValue().isShowing())
			menu.getValue().engineInput(e,kt);
		EngineClasses.gh.input(e,kt);
		EngineClasses.logo.skip();
	}
	public void engineInput(MouseEvent e, MouseType mt, int mouseX, int mouseY) {
		for(Entry<String,Menu> menu : menus.entrySet())if(menu.getValue().isShowing())
			menu.getValue().engineInput(e,mt,mouseX,mouseY);
		EngineClasses.gh.input(e,mt,mouseX,mouseY);
		if(mt==MouseType.MouseClicked) EngineClasses.logo.skip();
	}
}
