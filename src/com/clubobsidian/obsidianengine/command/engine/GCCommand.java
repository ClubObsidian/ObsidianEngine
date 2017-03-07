package com.clubobsidian.obsidianengine.command.engine;

import com.clubobsidian.obsidianengine.command.Command;
import com.clubobsidian.obsidianengine.command.CommandExecutor;
import com.clubobsidian.obsidianengine.user.User;

public class GCCommand implements CommandExecutor {

	@Override
	public boolean onCommand(User sender, Command cmd, String[] args) 
	{
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		sender.sendMessage("Max memory: " + (runtime.maxMemory() / 1024) + "Kb");
		sender.sendMessage("Total memory: " + (runtime.totalMemory() / 1024) + "kb");
		sender.sendMessage("Free Memory: " + (runtime.freeMemory() / 1024) + "Kb");
		return true;
	}
}