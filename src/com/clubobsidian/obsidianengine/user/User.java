package com.clubobsidian.obsidianengine.user;

import java.util.ArrayList;

public abstract class User {

	public abstract ArrayList<String> getPermissions();
	public abstract void sendMessage(String message);
	
	public boolean hasPermission(final String permission)
	{
		if(permission == null)
			return false;
		
		final String finalPermission = permission.toLowerCase().trim();
		if(this.getPermissions().contains("*"))
		{
			return true;
		}
		else
		{
			for(String p : this.getPermissions())
			{	
				if(p.endsWith("*") && p.contains(".") && finalPermission.charAt(finalPermission.charAt(finalPermission.length() - 1)) != '*')
				{
					String pStart = finalPermission.substring(0, finalPermission.lastIndexOf("." + 1));
					if(finalPermission.startsWith(pStart))
					{
						return true;
					}
				}
				else if(p.toLowerCase().equals(finalPermission))
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