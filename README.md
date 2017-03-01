# ObsidianEngine - A platform agnostic module server

[![Build Status](https://travis-ci.org/ClubObsidian/ObsidianEngine.svg?branch=master)](https://travis-ci.org/ClubObsidian/ObsidianEngine)

ObsidianEngine is a modular based system to modify jars and more on runtime. ObsidianEngine can be used to inject class files for overriding classes in the main jar file. ObsidianEngine can also be used for asm bytecode editing or anything else you can really thing of. The module system is inspired by the way that Bukkit loads plugins. ObsidianEngine can also run in standalone mode to create your own platforms for game servers, providing administration to tools written in Java and much more.

# FAQ
* How do I use this?
 * The project will be fully documented in the wiki when the project is in a mature state.
 
 # Todo
 * Re-write the module loader to fix the issue with circular dependencies and load-order
 * Choose a different logger library

# Contributing
//Todo

# Credits
[ASM](http://forge.ow2.org/projects/asm/) - ASM library used for event4j.

[Apache Foundation](https://www.apache.org/) - For their great libraries, their logging library is used in ObsidianEngine due to its ease of integration.

[Bukkit](https://github.com/Bukkit/Bukkit) - For inspiring the project, I've taken a similar approach in design philosophy from the project

[Event4J](https://github.com/Techcable/Event4J) - For the event system used by ObsidianEngine, its a great ASM based event system compared to the commonly used reflection based system.

[JarPatcher](http://github.com/virustotalop/JarPatcher) - JarPatcher is the first project I worked on with ClassLoaders and it inspired me to make ObsidianEngine.

[okio](https://github.com/square/okio) - The sneakthrow code to replace lombok so it is not needed as a compile time dependency.

[SnakeYaml](https://bitbucket.org/asomov/snakeyaml) - For the easy to use configuration formatting library that so many Bukkit developers have either grown to love or hate. 

# License
Obsidian Engine is licensed under the permissive [MIT license.](LICENSE)

>The MIT License (MIT)

>Copyright (c) virustotalop 2016 & contributors

>Permission is hereby granted, free of charge, to any person obtaining a copy
>of this software and associated documentation files (the "Software"), to deal
>in the Software without restriction, including without limitation the rights
>to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
>copies of the Software, and to permit persons to whom the Software is
>furnished to do so, subject to the following conditions:

>The above copyright notice and this permission notice shall be included in all
>copies or substantial portions of the Software.

>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
>IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 >FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
>AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
>LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
>OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
>SOFTWARE.
