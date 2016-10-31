package com.clubobsidian.obsidianengine.event.command;

import com.clubobsidian.obsidianengine.event.Event;

public class CommandExecuteEvent extends Event {
	
	private String commandName;
	private String[] args;
	
	public CommandExecuteEvent(String commandName, String[] args)
	{
		this.commandName = commandName;
		this.args = args;
	}

	public String getCommandName()
	{
		return this.commandName;
	}
	
	public String[] getArgs()
	{
		return this.args;
	}
}