package com.tb2dge.main;

public class EngineSettings {
	public volatile static String CMD_PREFIX = "!";
	public volatile static int FPS_LIMIT = 9999;
	public volatile static double UPDATE_RATE = 60;
	public volatile static double UPDATE_BASE = 60;
	
	public static double getUpdateScale() {
		return 1.0*(UPDATE_BASE/UPDATE_RATE);
	}
}
