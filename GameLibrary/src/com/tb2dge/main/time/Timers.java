package com.tb2dge.main.time;

import java.util.LinkedList;

public class Timers {
	public static LinkedList<Timer> timers = new LinkedList<Timer>();
	
	public static void addTimer(Timer timer) {
		timers.add(timer);
	}
	public static void removeTimer(Timer timer) {
		timers.remove(timer);
	}
	public void update() {
		for(int i = 0; i < timers.size(); i++) timers.get(i).update();
	}
}
