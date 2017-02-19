package com.clubobsidian.obsidianengine.command;

import java.util.Arrays;
import java.util.HashMap;

import com.clubobsidian.obsidianengine.ObsidianEngine;
import com.clubobsidian.obsidianengine.event.command.CommandExecuteEvent;
import com.clubobsidian.obsidianengine.user.User;

public class CommandDispatcher {

	private HashMap<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();
	private HashMap<CommandExecutor, Command> commandMap = new HashMap<CommandExecutor, Command>();
	
	protected boolean registerCommand(CommandExecutor executor, Command cmd)
	{
		if(this.commandExists(cmd.getName()))
			return false;
		
		this.forceRegisterCommand(executor, cmd);
		return true;
	}
	
	protected void forceRegisterCommand(CommandExecutor executor, Command cmd)
	{
		this.commands.put(cmd.getName(), executor);
		this.commandMap.put(executor, cmd);
	}
	
	public boolean commandExists(String label)
	{
		return this.commands.keySet().contains(label);
	}
	
	public synchronized void dispatchCommand(User user, String command)
	{
		char ch = command.charAt(0);
		if(ch == '!' || ch == '/')
		{
			command = command.substring(1);
		}
		
		String[] args = command.split(" ");
		if(args.length > 0)
		{
			String name = args[0];
			if(this.commandExists(name))
			{
				CommandExecutor executor = this.commands.get(name);
				if(args.length > 1)
				{
					args = Arrays.copyOfRange(args, 1, args.length);
				}
				else
				{
					args = new String[0];
				}
				Command cmd = this.commandMap.get(executor);
				CommandExecuteEvent event = new CommandExecuteEvent(name, args);
				if(!user.hasPermission(cmd.getPermission()))
				{
					event.setCancelled(true);
				}
				ObsidianEngine.getEventManager().dispatchEvent(event);
				if(!event.isCancelled())
				{
					executor.onCommand(user, cmd, args);
				}
			}
			else
			{
				user.sendMessage("Unknown command " + name + " please try again");
			}
		}
	}
}