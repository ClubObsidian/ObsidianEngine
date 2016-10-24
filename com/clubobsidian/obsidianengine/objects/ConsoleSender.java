package com.clubobsidian.obsidianengine.objects;

import java.util.ArrayList;

public class ConsoleSender implements Sender {

	@Override
	public ArrayList<String> executableCommands() 
	{
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("*");
		return cmds;
	}

}
