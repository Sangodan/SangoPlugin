package com.sangodan.sangoplugin.utils;

import java.util.List;

import com.sangodan.sangoplugin.main.Config;
import com.sangodan.sangoplugin.main.SangoPlugin;

public class Settings {
	public static boolean checkpointClay = false;
	public static boolean worldIndependent = false;
	public static byte moreChickens = 0;
	
	public static List<String> worldsSpleef;
	public static List<String> worldsVanilla;
	public static List<String> lobbies;
	
	public static String worldPvp;
	
	
	public static void onEnable(SangoPlugin pl) {
		checkpointClay = pl.getConfig().getBoolean("checkpoint_clay");
		worldIndependent = pl.getConfig().getBoolean("clay_independent");
		worldsSpleef = Config.getWorldsConfig().getStringList("spleef");
		worldsVanilla = Config.getWorldsConfig().getStringList("vanilla");
		lobbies = Config.getWorldsConfig().getStringList("lobbies");
		worldPvp = Config.getWorldsConfig().getString("pvp");
	}
}
