package com.sangodan.sangoplugin.event.world;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldUnloadEvent;

import com.sangodan.sangoapi.classes.SangoWorld;

public class OnWorldUnload implements Listener {

	@EventHandler
	public void onUnload(WorldUnloadEvent event) {

		SangoWorld world = SangoWorld.get(event.getWorld());
		world.delete();
	}
}
