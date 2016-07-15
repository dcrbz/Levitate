<p align="center">
  <img src="https://i.imgur.com/Ln11ksG.png" alt="Levitate-Header">
  <br />
  <a href="https://raw.githubusercontent.com/KennethWussmann/Levitate/master/LICENSE"><img src="https://img.shields.io/badge/license-GPLv2-blue.svg" alt="GitHub license"></a>
  <a href='http://ci.ketrwu.de/job/Levitate-Java-8/branch/master/'><img src='http://ci.ketrwu.de/job/Levitate-Java-8/branch/master/badge/icon'></a>
  <a href="https://github.com/KennethWussmann/Levitate/releases/latest"><img src="https://img.shields.io/badge/style-1.5.4-A68FA1.svg?label=version" alt="GitHub version"></a>
  <a href="http://ci.ketrwu.de/job/Levitate-Java-8/branch/master/javadoc/"><img src="https://img.shields.io/badge/style-latest-yellow.svg?label=JavaDoc" alt="JavaDoc"></a>
  <a href="https://github.com/KennethWussmann/Levitate/wiki"><img src="https://img.shields.io/badge/Wiki-Read%20me-358AA6.svg" alt="Wiki"></a>
  <a href="https://www.paypal.me/ketrwu/0.99usd"><img src="https://img.shields.io/badge/style-USD%200.99-blue.svg?label=Donate" alt="PayPal Donate"></a>
  
 
<br />
Levitate is an awesome and easy to use Command-Library for Bukkit/Spigot-Plugins.<br />
It allows you to register commands with arguments, permissions, TabCompletion and description in seconds. 
There also is no need to register it in the plugin.yml.
</p>

# Getting started 
Add the dependency to your project using maven:
```XML
<dependencies>
    <dependency>
      <groupId>de.ketrwu</groupId>
      <artifactId>levitate</artifactId>
      <version>1.5.4-SNAPSHOT</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
      <id>ketrwu-repo</id>
      <url>http://repo.ketrwu.de/repository/maven-snapshots</url>
    </repository>
</repositories>
```

Write your first command in seconds: 
```Java
public void onEnable() {
	Levitate levitate = new Levitate(this);
	levitate.registerCommands(this);
}
	
@Command(syntax = "?kill <player[online]>", readable = "/kill <Player>", description = "Kill a player", permission = "player.kill", aliases = {"die"})
public void killCommand(CommandSender sender, String cmd, ParameterSet args) {
	args.getPlayer(0).setHealth(0);
	sender.sendMessage("Player has been killed!");
}
```
Please check [the wiki](https://github.com/KennethWussmann/Levitate/wiki) for further reading. You can download Levitate [here](https://github.com/KennethWussmann/Levitate/wiki/1.-Getting-started#download-levitate) if you don't want to use Maven.

# Features
- [x] Checks arguments to be valid
- [x] Checks permission
- [x] Registers command directly to Bukkit/Spigot without plugin.yml
- [x] Supports command aliases
- [x] Bukkit/Spigot-Version independent
- [x] HelpMap with detailed Command-List
- [x] TabComplete
- [x] Undefined amount of arguments
- [x] Extendable
- [ ] [Let me know your ideas](https://github.com/KennethWussmann/Levitate/issues/new?labels=Feature-Request&body=Please describe your feature in detail. May use examples to explain your feature.) :smile:

# Documentation
All important features are documented in [the wiki](https://github.com/KennethWussmann/Levitate/wiki). 

# License
Levitate is licensed under [GNU General Public License Version 2](https://github.com/KennethWussmann/Levitate/blob/master/LICENSE).

