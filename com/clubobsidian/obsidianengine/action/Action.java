package com.clubobsidian.obsidianengine.action;

import com.clubobsidian.obsidianengine.user.User;

public interface Action {

	public String getPermission();
	public String getName();
	public User canExecute();
	
}
