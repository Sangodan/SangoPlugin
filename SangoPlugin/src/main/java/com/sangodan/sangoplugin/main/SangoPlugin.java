package com.sangodan.sangoplugin.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.sangodan.sangoapi.wrapper.SangoWorld;
import com.sangodan.sangoplugin.tickevent.TickHandler;
import com.sangodan.sangoplugin.utils.Settings;
import com.sangodan.sangoplugin.utils.Utils;

public class SangoPlugin extends JavaPlugin {

	private static SangoPlugin instance;
	private static Logger logger;
	public static PluginDescriptionFile pdfFile;
	public static TickHandler tickHandler;
	
	@Override
	public void onEnable() {
		instance = this;
		pdfFile = getDescription();
		logger = getLogger();
		tickHandler = new TickHandler();
		Registers.onEnable(this);
		Config.onEnable(this);
		Settings.onEnable(this);
		tickHandler.startServerTick();
		logger.info(pdfFile.getName() + " is locked and loaded: version " + pdfFile.getVersion());
	}

	@Override
	public void onDisable() {

		Config.onDisable();

		for(World w : Bukkit.getWorlds()) {
			SangoWorld sWorld = SangoWorld.get(w);
			if(sWorld.isSpleefMinigame()) {
				Utils.deleteWorld(w);
			}
		}
		tickHandler.stopServerTick();
		logger.info(pdfFile.getName() + "'s ammo has run out.");
	}

	public void log(String string) {
		logger.info(string);
	}
	
	public static SangoPlugin instance() {
		return instance;
	}
	
	public static TickHandler getTickHandler() {
		return tickHandler;
	}

}
