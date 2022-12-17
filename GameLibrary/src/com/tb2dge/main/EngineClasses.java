package com.tb2dge.main;

import com.tb2dge.main.camera.Camera;
import com.tb2dge.main.commands.Commands;
import com.tb2dge.main.graphics.Textures;
import com.tb2dge.main.graphics.animation.Animations;
import com.tb2dge.main.gui.GUIHandler;
import com.tb2dge.main.gui.TeamLogo;
import com.tb2dge.main.sound.Sounds;
import com.tb2dge.main.time.Timers;
import com.tb2dge.main.util.FileEncryptor;
import com.tb2dge.main.util.Lang;
import com.tb2dge.main.util.Settings;
import com.tb2dge.main.util.handlers.EntityHandler;
import com.tb2dge.main.util.handlers.ObjectHandler;

/**
 * EngineClasses contains all the major important classes to be able to access at anytime
 * from any class within the game. This includes things like texures, sounds, and the main file
 * of the game itself.
 * 
 * @author Vidroll
 *
 */

public class EngineClasses {
	public volatile static Sounds sounds;
	public volatile static Textures textures;
	public volatile static Animations animations;
	public volatile static Settings settings;
	public volatile static FileEncryptor fileEncryptor;
	public volatile static Lang lang;
	public volatile static Timers timers;
	public volatile static Camera camera;
	public volatile static TeamLogo logo;
	public volatile static EntityHandler eh;
	public volatile static ObjectHandler obh;
	public volatile static GUIHandler gh;
	public volatile static Commands cmds;
	public volatile static Game main;
}
