package com.sangodan.sangobuild.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sangodan.sangobuild.event.block.BlockEvents;

public class SangoBuild extends JavaPlugin {
	private static SangoBuild instance;
	private static Logger logger;
	public static PluginDescriptionFile pdfFile;
	
	public void onEnable() {
		instance = this;
		logger = getLogger();
		pdfFile = getDescription();
		logger.info(pdfFile.getName() + " will shelter your world! (V" + pdfFile.getVersion() + ")");
		registerEvent();
	}
	
	public void onDisable() {
		logger.info(pdfFile.getName() + " is shutting down.");
	}
	
	public static SangoBuild instance() {
		return instance;
	}
	public void registerCommand() {
		
	}
	
	public void registerEvent() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new BlockEvents(), this);
		
	}
}
