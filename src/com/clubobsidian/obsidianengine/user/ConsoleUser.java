package com.clubobsidian.obsidianengine.user;

import java.util.ArrayList;

import com.clubobsidian.obsidianengine.ObsidianEngine;

public class ConsoleUser extends User {

	private ArrayList<String> permissions;
	
	@Override
	public ArrayList<String> getPermissions() 
	{
		if(this.permissions == null)
		{
			this.permissions = new ArrayList<String>();
			this.permissions.add("*");
		}
		return this.permissions;
	}

	@Override
	public void sendMessage(String message) 
	{
		ObsidianEngine.getLogger().info(message);
	}
}