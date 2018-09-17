package com.sangodan.sangosocial.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.sangodan.sangosocial.command.CommandFriend;
import com.sangodan.sangosocial.command.CommandParty;
import com.sangodan.sangosocial.event.OnPlayerQuit;
import com.sangodan.sangosocial.event.OnWorldChange;

public class Registers {

	public static void onEnable(SangoSocial pl) {
		registerCommands(pl);
		registerEvents(pl);
	}
	
	public static void registerCommands(SangoSocial pl) {
		pl.getCommand("party").setExecutor(new CommandParty());
		pl.getCommand("friend").setExecutor(new CommandFriend());
	}
	
	public static void registerEvents(SangoSocial pl) {
		PluginManager pm  = Bukkit.getPluginManager();
		pm.registerEvents(new OnPlayerQuit(), pl);
		pm.registerEvents(new OnWorldChange(), pl);
	}
}
