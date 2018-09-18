package com.sangodan.sangoplugin.event.world;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.sangodan.sangoapi.classes.SangoWorld;
import com.sangodan.sangoapi.enums.Life;
import com.sangodan.sangoapi.enums.Minigame;
import com.sangodan.sangoplugin.main.SangoPlugin;
import com.sangodan.sangoplugin.utils.Settings;

public class OnWorldLoad implements Listener {
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent event) {
		SangoWorld world = SangoWorld.get(event.getWorld());
		List<String> vanillaWorlds = Settings.worldsVanilla;
		Bukkit.getLogger().info("WorldLoadEvent called for world " + world.getName());
		if (vanillaWorlds.contains(world.getName())) {
			world.getWorld().setMetadata("Vanilla", new FixedMetadataValue(SangoPlugin.instance(), true));
		}

		if (world.getName().contains("Spleef_")) {
			world.setAutoSave(false);
			world.getWorld().setDifficulty(Difficulty.PEACEFUL);
			world.killallMobs();
			world.getWorld().setGameRuleValue("doMobSpawning", "false");
			world.getWorld().setDifficulty(Difficulty.NORMAL);
			world.setMinigame(Minigame.SPLEEF);
			world.setLife(Life.LOBBY);
		}
		
		world.startRunnable();
	}
}
