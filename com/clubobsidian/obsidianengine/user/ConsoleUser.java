package com.clubobsidian.obsidianengine.user;

import java.util.ArrayList;

import com.clubobsidian.obsidianengine.permission.Permission;

public class ConsoleUser implements User {

	private ArrayList<Permission> permissions;
	
	@Override
	public ArrayList<Permission> getPermissions() 
	{
		if(this.permissions == null)
		{
			this.permissions = new ArrayList<Permission>();
			this.permissions.add(new Permission("*"));
		}
		return this.permissions;
	}

	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		
	}
}