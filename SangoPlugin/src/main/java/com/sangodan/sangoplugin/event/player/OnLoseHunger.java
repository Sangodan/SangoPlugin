package com.sangodan.sangoplugin.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class OnLoseHunger implements Listener {

	@EventHandler
	public void onLoseHunger(FoodLevelChangeEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(!player.getWorld().hasMetadata("Vanilla")) {
				event.setCancelled(true);
				event.setFoodLevel(20);
			}
		}
	}
}
