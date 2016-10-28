package com.clubobsidian.obsidianengine.command;

import com.clubobsidian.obsidianengine.ObsidianEngine;
import com.clubobsidian.obsidianengine.user.User;

public class CommandBuilder {

	private CommandExecutor executor;
	private String permission = null;
	private User user = null;
	private boolean forceRegister = false;
	private String name = null;
	
	public CommandBuilder setExecutor(CommandExecutor executor)
	{
		this.executor = executor;
		return this;
	}
	
	public CommandBuilder setPermission(String permission)
	{
		this.permission = permission;
		return this;
	}
	
	public CommandBuilder setUser(User user)
	{
		this.user = user;
		return this;
	}
	
	public CommandBuilder setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public CommandBuilder setForceRegister(boolean forceRegister)
	{
		this.forceRegister = forceRegister;
		return this;
	}
	
	public void build()
	{
		Command command = new Command()
		{
			@Override
			public User canExecute()
			{
				return user;
			}
			
			@Override
			public String getPermission()
			{
				return permission;
			}

			@Override
			public String getName() 
			{
				return name;
			}
		};
		
		if(this.forceRegister)
		{
			ObsidianEngine.getCommandDispatcher().forceRegisterCommand(this.executor, command);
		}
		else
		{
			ObsidianEngine.getCommandDispatcher().registerCommand(this.executor, command);
		}
	}
}
