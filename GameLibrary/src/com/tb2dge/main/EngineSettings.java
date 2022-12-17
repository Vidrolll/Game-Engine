package com.tb2dge.main;

/**
 * EngineSettings is a class that contains all the information on how you want your game to run.
 * Changing these values changes how the game itself is ran. All values are 
 * static making it easy to change them.
 * 
 * @author Vidroll
 *
 */

public class EngineSettings {
	/**The prefix that indicates a console command. This is the character it'll look
	 * for at the first letter of every console input. At default this is set to "!".*/
	public volatile static String CMD_PREFIX = "!";
	/**The FPS limit of the engine. This tells the {@link FramerateLimit} class how fast its
	 * allowed to update. At default its 9999, or essentially infinite.*/
	public volatile static int FPS_LIMIT = 9999;
	/**The update rate. Update rate tells the engine how many times to run any given "update" function
	 * per second. At default its 60.*/
	public volatile static double UPDATE_RATE = 60;
	/**The update base. This value is used to calculate an update scale. Or how much faster/slower 
	 * the game is currently updating. This way if you slow down the engine update speed to conserve
	 * on computer power, everything updates the same.*/
	public volatile static double UPDATE_BASE = 60;
	
	/**Variable incrementation multiplied by this value will increment at the same rate even if the
	 * update rate is changed.*/
	public static double getUpdateScale() {
		return 1.0*(UPDATE_BASE/UPDATE_RATE);
	}
}
