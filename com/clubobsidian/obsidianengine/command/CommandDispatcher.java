package com.clubobsidian.obsidianengine.command;

import java.util.HashMap;

import com.clubobsidian.obsidianengine.user.User;

public class CommandDispatcher {

	private HashMap<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();
	
	public boolean registerCommand(String label, CommandExecutor cmd)
	{
		if(this.commands.keySet().contains(label))
			return false;
		
		this.forceRegisterCommand(label, cmd);
		return true;
	}
	
	public void forceRegisterCommand(String label, CommandExecutor cmd)
	{
		this.commands.put(label, cmd);
	}
	
	public boolean commandExists(String label)
	{
		return this.commands.keySet().contains(label);
	}
	
	public void dispatchCommand(User user, String command)
	{
		//TODO - parse out command
		//TODO - remove prefixes to give option for in-game commands
		
	}
}
