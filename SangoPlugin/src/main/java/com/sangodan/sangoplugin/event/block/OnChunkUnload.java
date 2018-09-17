package com.sangodan.sangoplugin.event.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.sangodan.sangoplugin.utils.ChunkUtils;

public class OnChunkUnload implements Listener {

	@EventHandler
	public void onUnload(ChunkUnloadEvent event) {
		if (ChunkUtils.unloadableChunks.contains(event.getChunk())) {
			event.setCancelled(true);
		}
	}
}
