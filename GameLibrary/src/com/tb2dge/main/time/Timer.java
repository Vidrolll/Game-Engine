package com.tb2dge.main.time;

import com.tb2dge.main.EngineSettings;

public class Timer {
	Runnable[] functions;
	Timers th;
	boolean loop;
	long ticks;
	double time;
	
	public Timer(long ticks, boolean looping, Runnable... functions) {
		loop = looping;
		this.ticks = ticks;
		this.functions = functions;
		Timers.addTimer(this);
	}
	public void update() {
		time+=1.0*EngineSettings.getUpdateScale();
		if(time >= ticks) {
			for(Runnable function : functions) function.run();
			if(!loop)
				Timers.removeTimer(this);
			else
				time = 0;
		}
	}
}
