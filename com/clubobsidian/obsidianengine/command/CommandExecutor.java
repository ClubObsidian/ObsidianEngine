package com.clubobsidian.obsidianengine.command;

import com.clubobsidian.obsidianengine.objects.Sender;

public interface CommandExecutor {

	public boolean onCommand(Sender sender, Command cmd, String[] args);
}