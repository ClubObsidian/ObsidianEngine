package com.clubobsidian.obsidianengine.okio;

/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//This code is from the okio repository from the Util.java class
//This is being used so lombok is not a compile time dependency

public class SneakyThrow {

	public static void sneakyRethrow(Throwable t) 
	{
		SneakyThrow.<Error>sneakyThrow2(t);
	}

	@SuppressWarnings("unchecked")
	private static <T extends Throwable> void sneakyThrow2(Throwable t) throws T 
	{
		throw (T) t;
	}
}