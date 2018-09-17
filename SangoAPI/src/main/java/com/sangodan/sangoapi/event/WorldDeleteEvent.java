package com.sangodan.sangoapi.event;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.sangodan.sangoapi.classes.SangoWorld;

public class WorldDeleteEvent extends Event {

	private World world;
	private SangoWorld sangoWorld;

	public WorldDeleteEvent(World world) {
		this.world = world;
		this.sangoWorld = SangoWorld.get(world);
	}
	
	public World getWorld() {
		return world;
	}
	
	public SangoWorld getSangoWorld() {
		return sangoWorld;
	}

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
