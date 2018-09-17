package com.sangodan.sangoplugin.event.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;

import com.sangodan.sangoplugin.utils.Settings;

public class EggThrow implements Listener {
	
	@EventHandler
	public void onEggThrow(PlayerEggThrowEvent event) {
		byte amount =  Settings.moreChickens;
		byte hatch = event.getNumHatches();
		byte total = (byte) (hatch + amount);
		if (total > 0) {
			event.setNumHatches(total);
			event.setHatching(true);
		} else {
			event.setHatching(false);
		}

	}

}
