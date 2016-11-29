package com.clubobsidian.obsidianengine.permission;

import com.clubobsidian.obsidianengine.user.User;

public class Permission {
	
	public static boolean hasPermission(User user, String permission)
	{
		for(String perm : user.getPermissions())
		{
			return permission.equals(perm) || permission.equals("*"); //Add support for more wildcard permissions later
		}
		return false;
	}
}