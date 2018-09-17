package com.sangodan.sangoplugin.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.sangodan.sangoplugin.main.SangoPlugin;

public class ChunkUtils {

	public static List<Chunk> unloadableChunks = new ArrayList<Chunk>();
	public static void loadChunksAt(Location loc, int radius, long stayLoaded) {
		Chunk c = loc.getChunk();
		if(!c.isLoaded()) c.load(true);
		int x = c.getX();
		int z = c.getZ();
		for(int a = -radius; a <= radius; a++) {
			for(int b = -radius; b <= radius; b++) {
				Chunk xc = loc.getWorld().getChunkAt(x + a, z + b);
				if(!xc.isLoaded()) xc.load(true);
				unloadableChunks.add(xc);
				new BukkitRunnable() {
					@Override
					public void run() {
						unloadableChunks.remove(xc);
					}
				}.runTaskLater(SangoPlugin.instance(), stayLoaded);
			}
		}
	}
}
