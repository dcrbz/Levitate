<p align="center">
  <img src="http://media.ketrwu.de/levitate-header-3.png" alt="Levitate-Header">
  <br />
  <a href="https://raw.githubusercontent.com/KennethWussmann/Levitate/master/LICENSE"><img src="https://img.shields.io/badge/license-GPLv2-blue.svg" alt="GitHub license"></a>
  <a href="https://travis-ci.org/KennethWussmann/Levitate"><img src="https://travis-ci.org/KennethWussmann/Levitate.svg?branch=master" alt="Build Status"></a>
  <a href="https://github.com/KennethWussmann/Levitate/releases/latest"><img src="https://badge.fury.io/gh/KennethWussmann%2FLevitate.svg" alt="GitHub version"></a>
  <a href="https://www.paypal.me/ketrwu/0.99usd"><img src="https://img.shields.io/badge/style-USD%200.99-blue.svg?label=PayPal" alt="PayPal Donate"></a>
  <a href="https://gitter.im/KennethWussmann/Levitate"><img src="https://img.shields.io/badge/style-Join%20channel-organge.svg?label=Gitter" alt="Join Gitter"></a>

<br />
Levitate is an awesome and easy to use Command-Library for Bukkit/Spigot-Plugins.<br />
It allows you to register commands with arguments, permissions, TabCompletion and description in seconds. 
There also is no need to register it in the plugin.yml.
</p>

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
[x] Checks arguments to be valid
[x] Checks permission
[x] Registers command directly to Bukkit/Spigot without plugin.yml
[x] Supports command aliases
[x] Bukkit/Spigot-Version independent
[x] HelpMap with detailed Command-List
[x] TabComplete
[x] Undefined amount of arguments
[x] Extendable
[ ] Let me know your ideas :smile:

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

#License
Levitate is licensed under [GNU General Public License Version 2](https://github.com/KennethWussmann/Levitate/blob/master/LICENSE).
