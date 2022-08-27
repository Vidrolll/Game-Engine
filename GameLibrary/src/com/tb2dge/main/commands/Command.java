package com.tb2dge.main.commands;

public class Command {
	String[] inputs;
	CMDRun<String> output;
	public Command(CMDRun<String> output, String...inputs) {
		this.output = output;
		this.inputs = inputs;
	}
	public boolean readInput(String cmd, String[] args) {
		boolean valid = false;
		for(String input : inputs)
			if(cmd.equals(input)) valid = true;
		if(!valid) return false;
		output.run(args);
		return true;
	}
}
