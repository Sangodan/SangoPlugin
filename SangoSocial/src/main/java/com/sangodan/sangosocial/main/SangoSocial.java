package com.sangodan.sangosocial.main;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class SangoSocial extends JavaPlugin {
	
	private static SangoSocial instance;
	private static Logger logger;
	public static PluginDescriptionFile pdfFile;
	
	public void onEnable() {
		instance = this;
		logger = getLogger();
		pdfFile = getDescription();
		logger.info(pdfFile.getName() + " is working: Get Ready To Party! (V" + pdfFile.getVersion() + ")");
		
		Registers.onEnable(this);
		Config.onEnable(this);
	}
	
	public void onDisable() {
		logger.info(pdfFile.getName() + " is shutting down.");
		Config.onDisable();
	}
	
	public static SangoSocial instance() {
		return instance;
	}
}
