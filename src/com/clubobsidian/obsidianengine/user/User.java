package com.clubobsidian.obsidianengine.user;

import java.util.ArrayList;

public abstract class User {

	public abstract ArrayList<String> getPermissions();
	public abstract void sendMessage(String message);
	
	public boolean hasPermission(final String permission)
	{
		final String finalPermission = permission.toLowerCase();
		if(this.getPermissions().contains("*"))
		{
			return true;
		}
		else
		{
			for(String p : this.getPermissions())
			{
				if(p.toLowerCase().equals(finalPermission))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean addPermission(String permission)
	{
		boolean hasPermission = this.hasPermission(permission);
		if(hasPermission)
		{
			return false;
		}
		else
		{
			this.getPermissions().add(permission);
			return true;
		}
	}
}