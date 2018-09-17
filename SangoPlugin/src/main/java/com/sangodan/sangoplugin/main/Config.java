package com.sangodan.sangoplugin.main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.sangodan.sangoplugin.utils.PublicVars;

public class Config {

	static SangoPlugin plugin;
	
	static File dataFolder;
	
	static File buttonCommandsFile;
	static File worldsFile;
	
	static FileConfiguration buttonCommandsConfig;
	static FileConfiguration worldsConfig;
	
	public static void onEnable(SangoPlugin pl) {
		plugin = pl;
		dataFolder = pl.getDataFolder();
		buttonCommandsFile = new File(dataFolder, "buttonCommands.yml");
		worldsFile = new File(dataFolder, "worlds.yml");
		
		createAllFiles();
		
		buttonCommandsConfig = YamlConfiguration.loadConfiguration(buttonCommandsFile);
		worldsConfig = YamlConfiguration.loadConfiguration(worldsFile);
		
		// Command Buttons
		plugin.log("Loading command buttons...");
		List<String> strs = getButtonCommandsConfig().getStringList("buttonCommands");
		HashMap<String, String> map = new HashMap<String, String>();
		for(String s : strs) {
			String[] words = s.split(":");
			map.put(words[0], words[1]);
		}
		PublicVars.setCommands(map);
		plugin.log("Loaded command buttons.");
		

	}
	
	// ACCESSING FILES
	
	public static File getButtonCommandsFile() {
		return buttonCommandsFile;
	}
	
	public static File getWorldsFile() {
		return worldsFile;
	}
	// ACCESSING CONFIG
	
	public static FileConfiguration getButtonCommandsConfig() {
		return buttonCommandsConfig;
	}
	
	public static FileConfiguration getWorldsConfig() {
		return worldsConfig;
	}
	public static void createAllFiles() {
		if(!buttonCommandsFile.exists()) {
			plugin.log("Could not find buttonCommands.yml, creating it for you...");
			plugin.saveResource("buttonCommands.yml", true);
			plugin.log("Created buttonCommands.yml.");
			
		}
		
		if(!worldsFile.exists()){
			plugin.log("Could not find worlds.yml, creating it for you...");
			plugin.saveResource("worlds.yml", true);
			plugin.log("Created worlds.yml.");
		}
	}
	
	public static void onDisable() {

		// Button Commands
		plugin.log("Saving button commands...");
		HashMap<String, String> bCommands = PublicVars.getButtonCommands();
		List<String> st = getButtonCommandsConfig().getStringList("buttonCommands");
		st.clear();
		for(String s : bCommands.keySet()) {
			st.add(s + ":" + bCommands.get(s));
		}		
		getButtonCommandsConfig().set("buttonCommands", st);		

		plugin.log("Saved button commands.");


		
		try {
			getButtonCommandsConfig().save(buttonCommandsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
