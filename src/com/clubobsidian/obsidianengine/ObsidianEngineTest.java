package com.clubobsidian.obsidianengine;

import java.io.File;

import com.clubobsidian.obsidianengine.yaml.FileConfiguration;

public class ObsidianEngineTest {

	public static void main(String[] args)
	{
		//args = new String[] {"test"};
		//ObsidianEngine.main(args);
		FileConfiguration yaml = FileConfiguration.loadFile(new File("test.yml"));
		System.out.println("Trees are supported: " + yaml.getString("need.to.support.trees"));
		System.out.println("How many: " + yaml.getInt("need.to.support.howmany"));
	}	
}