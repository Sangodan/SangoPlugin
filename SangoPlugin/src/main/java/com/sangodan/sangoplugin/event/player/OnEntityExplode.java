package com.sangodan.sangoplugin.event.player;

import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class OnEntityExplode implements Listener {

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		if (event.getEntity() != null) {
			if (event.getEntity().getType() == EntityType.FIREBALL
					|| event.getEntity().getType() == EntityType.CREEPER) {
				Iterator<Block> iterator = event.blockList().iterator();
				while (iterator.hasNext()) {
					Block b = iterator.next();
					if (b.getType() != Material.SNOW_BLOCK) {
						iterator.remove();
					}
				}
			}
		}
	}
}
