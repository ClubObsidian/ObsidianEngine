package com.clubobsidian.obsidianengine.command;

import com.clubobsidian.obsidianengine.objects.Sender;

public class Command {

	private String name;
	
	public String getName()
	{
		return this.name;
	}
	
	public boolean canExecuteCommand(Sender sender)
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
