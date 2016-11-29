package com.clubobsidian.obsidianengine.user;

import java.util.ArrayList;

import com.clubobsidian.obsidianengine.permission.Permission;

public interface User {

	public ArrayList<String> getPermissions();
	public void sendMessage(String message);
}
