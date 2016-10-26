package com.clubobsidian.obsidianengine.command;

import com.clubobsidian.obsidianengine.user.User;

public interface CommandExecutor {

	public boolean onCommand(User sender, Command cmd, String[] args);
}