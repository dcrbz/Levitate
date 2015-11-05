# Levitate 
<img align="center" src="http://media.ketrwu.de/levitate-header.png" alt="Levitate-Header">

[![GitHub license](https://img.shields.io/badge/license-GPLv2-blue.svg)](https://raw.githubusercontent.com/KennethWussmann/Levitate/master/LICENSE) [![Build Status](https://travis-ci.org/KennethWussmann/Levitate.svg?branch=master)](https://travis-ci.org/KennethWussmann/Levitate) [![GitHub version](https://badge.fury.io/gh/KennethWussmann%2FLevitate.svg)](https://github.com/KennethWussmann/Levitate/releases/latest) [![PayPal Donate](https://img.shields.io/badge/style-USD%200,99-blue.svg?label=PayPal)](https://www.paypal.me/ketrwu/0,99usd) [![Join Gitter](https://img.shields.io/badge/style-Join%20channel-organge.svg?label=Gitter)](https://gitter.im/KennethWussmann/Levitate)
Levitate is an awesome and easy to use Command-Library for Bukkit/Spigot-Plugins.<br>
It allowes you to register commands with arguments, permissions, TabCompletion and description in seconds. There also is no need to register it in the plugin.yml.

#Example
This is a simple kill command:
```Java
public void onEnable() {
	Levitate levitate = new Levitate(this);
	levitate.register(new CommandInformation("/kill <player[online]>", "kill.player", "Kill a player"), new CommandHandler() {
				
		@Override
		public void execute(CommandSender sender, String command, ParameterSet args) {
			Player p = args.getPlayer(0);
			p.setHealth(0);
			sender.sendMessage("Player " + p.getName() + " has been killed!");
		}
				
	});
}
```

#Features
* Checks arguments to be valid
* Checks permission
* Registers command directly to Bukkit/Spigot without plugin.yml
* Supports command aliases
* Bukkit/Spigot-Version independent
* HelpMap with detailed Command-List
* TabComplete
* Undefined amount of arguments
* Extendable

#Getting started
Please check [the wiki](https://github.com/KennethWussmann/Levitate/wiki) to get started!

#Download
You can get the lastest Levitate-Artifact at:
* [GitHub Releases](https://github.com/KennethWussmann/Levitate/releases/latest) (Only Java-8)
* [Maven](https://github.com/KennethWussmann/Levitate/wiki/1.-Getting-started#maven) (Only Java-8)
* [Jenkins Java-8](http://ci.ketrwu.de/job/Levitate-Java-8/lastSuccessfulBuild/)
* [Jenkins Java-7](http://ci.ketrwu.de/job/Levitate-Java-7/lastSuccessfulBuild/)

#JavaDoc
You can find the lastest JavaDocs [here](http://ci.ketrwu.de/job/Levitate-Java-8/javadoc/).

#Planned features
* Let me know your ideas :smile:

#License
Levitate is licensed under [GNU General Public License Version 2](https://github.com/KennethWussmann/Levitate/blob/master/LICENSE).
