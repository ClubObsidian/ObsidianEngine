package com.clubobsidian.obsidianengine.permission;

import com.clubobsidian.obsidianengine.user.User;

public class Permission {
	
	public static boolean hasPermission(User user, String permission)
	{
		return user.hasPermission(permission); //Add support for more wildcard permissions later
	}
}