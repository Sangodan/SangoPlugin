package com.sangodan.sangoapi.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.sangodan.sangoapi.event.SangoChangeEventHandler;

public class APIMain extends JavaPlugin {

	private static APIMain instance;
	private static Logger logger;
	public static PluginDescriptionFile pdfFile;
	
	@Override
	public void onEnable() {
		instance = this;
		pdfFile = getDescription();
		logger = getLogger();
		logger.info(pdfFile.getName() + " is locked and loaded: version " + pdfFile.getVersion());
		
		Bukkit.getPluginManager().registerEvents(new SangoChangeEventHandler(), this);
	}

	@Override
	public void onDisable() {
		logger.info(pdfFile.getName() + "'s ammo has run out.");
	}

	public void log(String string) {
		logger.info(string);
	}
	
	public static APIMain instance() {
		return instance;
	}
}
