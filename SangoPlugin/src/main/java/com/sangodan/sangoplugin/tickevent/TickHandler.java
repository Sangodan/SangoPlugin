package com.sangodan.sangoplugin.tickevent;

import org.bukkit.scheduler.BukkitRunnable;

import com.sangodan.sangoplugin.main.SangoPlugin;

public class TickHandler {

	private BukkitRunnable onServerTick = new BukkitRunnable() {
		@Override
		public void run() {

		}
	};

	public void startServerTick() {
		onServerTick.runTaskTimer(SangoPlugin.instance(), 0L, 1L);
	}

	public void stopServerTick() {
		onServerTick.cancel();
	}
	
	// When world is made, make a worldRunnable for that world.
	// Then, start that runnable.
	
	// When world is unloaded, cancel the worldRunnable

	/**
	 * Returns the Tick Handler for the given world.
	 * 
	 * @param world
	 *            The world to get the Tick Handler for
	 * @return The Tick Handler found
	 */

}
