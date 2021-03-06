package com.clubobsidian.obsidianengine.command.engine;

import com.clubobsidian.obsidianengine.ObsidianEngine;
import com.clubobsidian.obsidianengine.command.Command;
import com.clubobsidian.obsidianengine.command.CommandExecutor;
import com.clubobsidian.obsidianengine.user.User;

public class StopCommand implements CommandExecutor {

	@Override
	public boolean onCommand(User sender, Command cmd, String[] args) 
	{
		ObsidianEngine.getLogger().info("Stopping ObsidianEngine now...");
		System.exit(0);
		return true;
	}

}
