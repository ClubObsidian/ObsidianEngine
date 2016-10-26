package com.clubobsidian.obsidianengine.permission;

import com.clubobsidian.obsidianengine.user.User;

public class Permission {

	private String permission;
	
	public Permission(String permission) 
	{
		this.permission = permission;
	}

	public String getPermission()
	{
		return this.permission;
	}
	
	public static boolean hasPermission(User user, String permission)
	{
		for(Permission perm : user.getPermissions())
		{
			return permission.equals(perm.getPermission()) || permission.equals("*"); //Add support for more wildcard permissions later
		}
		return false;
	}
}