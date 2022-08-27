package com.tb2dge.main.commands;

import java.util.LinkedList;
import java.util.Scanner;

import com.tb2dge.main.EngineSettings;

public class Commands implements Runnable {
	LinkedList<Command> cmds = new LinkedList<Command>();
	boolean running = false;
	Thread cmdThread;
	
	public Commands() {
		start();
	}
	
	public void addCommand(Command cmd) {
		cmds.add(cmd);
	}
	public void removeCommand(Command cmd) {
		cmds.remove(cmd);
	}
	
	public synchronized void start() {
		cmdThread = new Thread(this);
		cmdThread.start();
		running = true;
	}
	public synchronized void stop() {
		try {
			cmdThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		Scanner input = new Scanner(System.in);
		while(running) {
			String line = input.nextLine();
			if(line.startsWith(EngineSettings.CMD_PREFIX)) {
				for(Command cmd : cmds) {
					if(line.length() > 1+(line.split(" ")[0].substring(1).length())) {
						if(cmd.readInput(line.split(" ")[0].substring(1),
								line.substring((line.split(" ")[0].substring(1).length())+2).split(" ")))
						break;
					} else {
						if(cmd.readInput(line.split(" ")[0].substring(1),null))
						break;
					}
				}
			}
		}
		input.close();
		stop();
	}
}
