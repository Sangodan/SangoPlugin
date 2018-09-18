package com.sangodan.sangoplugin.event.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.sangodan.sangoapi.wrapper.SangoWorld;
import com.sangodan.sangoplugin.utils.Settings;

public class OnCreatureSpawn implements Listener {
	@EventHandler
	public void onSpawn(CreatureSpawnEvent event) {
		Entity entity = event.getEntity();
		SangoWorld world = SangoWorld.get(entity.getWorld());
		if(world.getName().equals(Settings.worldPvp)) {
			if(entity.getType() == EntityType.VILLAGER) {
				entity.setCustomName("Kit Selector");
				entity.setCustomNameVisible(true);
			}
		}
		
		if(world.isMinigame()) {
			if(entity.getType() == EntityType.VILLAGER) {
				entity.setCustomName("Lobby Selector");
				entity.setCustomNameVisible(true);
			}
		}
	}
}
