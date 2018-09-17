package com.sangodan.sangoplugin.event.player;

import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.sangodan.sangoplugin.utils.ConfigMaker;

public class OnSurvivalLeave implements Listener {

	private void save(Player p, World w, Location loc) throws IOException {
		if (w.getName().equals("Survival")) {
			ConfigMaker c = new ConfigMaker(p.getName(), "/survivalinv/");
			c.set("inventory.armor", p.getInventory().getArmorContents());
			c.set("inventory.content", p.getInventory().getContents());
			if(loc != null) {
				if(loc.getWorld().getName().equals("Survival")) {
					String s = loc.getX() + "," + loc.getY() + ","
							+ loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
					c.set("world.location", s);
				}
			}
			c.save();
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onKick(PlayerKickEvent event) {
		try {
			save(event.getPlayer(), event.getPlayer().getWorld(), event.getPlayer().getLocation());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onTeleport(PlayerTeleportEvent event) {
		if(!event.getFrom().getWorld().equals(event.getTo().getWorld())) {
			if(event.getFrom().getWorld().getName().equals("Survival")) {
				try {
					save(event.getPlayer(), event.getFrom().getWorld(), event.getFrom());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onDisconnect(PlayerQuitEvent event) {
		try {
			save(event.getPlayer(), event.getPlayer().getWorld(), event.getPlayer().getLocation());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
