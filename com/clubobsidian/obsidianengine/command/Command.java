package com.clubobsidian.obsidianengine.command;

import com.clubobsidian.obsidianengine.user.User;

public class Command {

	private String name;
	
	public String getName()
	{
		return this.name;
	}
	
	public boolean canExecuteCommand(User sender)
	{
		if(sender.executableCommands().contains("*"))
		{
			return true;
		}
		else
		{
			return sender.executableCommands().contains(this.getName());
		}
	}
	
}
