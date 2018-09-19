package com.sangodan.sangoplugin.event.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sangodan.sangoapi.enums.Minigame;
import com.sangodan.sangoapi.event.PlayerMinigameDeathEvent;
import com.sangodan.sangoapi.wrapper.SangoPlayer;
import com.sangodan.sangoapi.wrapper.SangoWorld;
import com.sangodan.sangoplugin.utils.Settings;

public class OnPlayerMinigameDeath implements Listener {

	@EventHandler
	public void onDeath(PlayerMinigameDeathEvent event) {
		SangoPlayer player = event.getSangoPlayer();
		SangoWorld world = event.getSangoWorld();
		world.spigot().strikeLightningEffect(player.getLocation(), false);
		if(world.getMinigame() == Minigame.SPLEEF) {
			player.teleportVanilla(world.getSpawnLocation());
			player.setSpectator();
			player.regen();
		}
		if(world.getName().equals(Settings.worldPvp)) {
			player.regen();
			player.dropInventory(5);
			player.teleport(world.getSpawnLocation(), true);
			player.spectatorSurvival();
		}
		if(world.getName().equals("Survival")) {
			
		}
	}
}
