package com.sangodan.sangoapi.classes;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldRunnable extends BukkitRunnable {

	private World world;
	
	public WorldRunnable(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	@Override
	public void run() {

	}
}
