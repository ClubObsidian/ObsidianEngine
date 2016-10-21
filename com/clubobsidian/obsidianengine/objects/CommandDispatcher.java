package com.clubobsidian.obsidianengine.objects;

import java.util.HashMap;

public class CommandDispatcher {

	private HashMap<String, Command> commands = new HashMap<String, Command>();
	
	public boolean registerCommand(String label, Command cmd)
	{
		if(this.commands.keySet().contains(label))
			return false;
		
		this.forceRegisterCommand(label, cmd);
		return true;
	}
	
	public void forceRegisterCommand(String label, Command cmd)
	{
		this.commands.put(label, cmd);
	}
	
	public boolean commandExists(String label)
	{
		return this.commands.keySet().contains(label);
	}
}
