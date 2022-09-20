package com.tb2dge.main;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;

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

/**
 * 
 * @author Vidroll
 *
 * The first class to use in the game engine. To create a runnable game,
 * make a main class that extends this class.
 * 
 * To add a window to your game make one using gui.windows.MainWindow.java
 * upon creation of a window it will automatically connect it to your game.
 * 
 * to set a resolution use the method setResScale(int,int,int,int,double);
 * the parameters would first be your resolution in the x direction (1920 being the usual standard)
 * and the resolution in the y axis (1080 being the usual standard)
 * next you would input the window size X and the window size Y.
 * the double being the computer screens scale. the MainWindow class has a function
 * to return this value using MainWindow.getWindowScale();
 * 
 * Override the setup() method for any game setup required
 * 
 * Other engine features to include start at line 211
 */

public class Game extends Canvas implements Runnable {
	protected HashMap<String,Menu> menus = new HashMap<String,Menu>();
	private static final long serialVersionUID = 1L;
	public static String gameName;

	protected boolean running = false;
	public boolean showGUI = true; 
	protected Thread thread;
	protected boolean printFPS = true;
	protected boolean printUpdates = false;
	public double resX = 1;
	public double resY = 1;
	public double windowX = 1;
	public double windowY = 1;
	boolean garbageCollection = false;
	public double windowScale = 1;
	
	public boolean antialiasing = false;
	public Object RENDERING = RenderingHints.VALUE_RENDER_DEFAULT;
	public Object INTERPOLATION = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
	
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
		int updates = 0;
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
					updates++;
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
				if(printUpdates)Console.log(updates);
				if(garbageCollection)System.gc();
				timer += 1000;
				frames = 0;
				updates = 0;
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
	public void printUpdates(boolean value) {
		printUpdates = value;
	}
	public static String getGameName() {
		return gameName;
	}
	public void setAntialiasing(boolean antialiasing) {
		this.antialiasing = antialiasing;
	}
	public void setRendering(Object rendering) {
		this.RENDERING = rendering;
	}
	public void setInterpolation(Object interpolation) {
		this.INTERPOLATION = interpolation;
	}
	
	public void draw() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			if(this.getFocusCycleRootAncestor() != null)
				this.createBufferStrategy(2);
			return;
		}
		do {
			try {
				Graphics2D g = (Graphics2D)bs.getDrawGraphics();
				if(antialiasing)g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,INTERPOLATION);
				g.setRenderingHint(RenderingHints.KEY_RENDERING,RENDERING);
				g.scale(windowX / resX * 1.0, windowY / resY * 1.0);
				drawBackground(g);
				EngineClasses.camera.render(g);
				drawForeground(g);
				EngineClasses.obh.render(g);
				EngineClasses.eh.render(g);
				for(Entry<String,Menu> menu : menus.entrySet()) {
					Menu tempMenu = menu.getValue();
					if(tempMenu.isShowing()) tempMenu.engineRender(g);
				}
				g.dispose();
				g = (Graphics2D)bs.getDrawGraphics();
				//g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//				g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_SPEED);
				g.scale(windowX / resX * 1.0, windowY / resY * 1.0);
				if(showGUI)EngineClasses.gh.render(g);
				drawGUIOverlay(g);
				EngineClasses.logo.render(g);
				g.dispose();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				bs.show();
			}
		} while(bs.contentsLost());
	}
	//Override this method to add updates to your game
	public void update() {}
	//Override this method to draw the background (first layer drawn)
	public void drawBackground(Graphics2D g) {}
	//Override this method to draw the foreground (second layer drawn)
	public void drawForeground(Graphics2D g) {}
	//Override this method to draw the gui (last layer drawn, not effected by camera effects)
	public void drawGUIOverlay(Graphics2D g) {}
	//Override this method to add keyboard input. input sent to the machine automatically feeds into this method
	public void input(KeyEvent e, KeyType kt) {}
	//Override this method to add mouse input. input sent to the machine automatically feeds into this method
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
