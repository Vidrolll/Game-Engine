package com.tb2dge.main.util;

import com.tb2dge.main.Game;

public class FileLocation {
	public static final String APPDATA = System.getenv("APPDATA") + "/" + Game.getGameName() + "/";
	public static final String RESOURCE_FOLDER = "/";
}
