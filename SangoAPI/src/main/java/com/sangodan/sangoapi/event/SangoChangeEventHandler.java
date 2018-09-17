package com.sangodan.sangoapi.event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SangoChangeEventHandler implements Listener {
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		if(!event.getFrom().getWorld().equals(event.getTo().getWorld())) {
			SangoChangedWorldEvent e = new SangoChangedWorldEvent(event.getPlayer(), event.getFrom(), event.getTo());
			Bukkit.getPluginManager().callEvent(e);
		}
	}
}
