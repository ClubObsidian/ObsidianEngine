package com.clubobsidian.obsidianengine.user;

import java.util.ArrayList;

public class ConsoleUser implements User {

	@Override
	public ArrayList<String> executableCommands() 
	{
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("*");
		return cmds;
	}

}
