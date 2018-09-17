package com.sangodan.sangoapi.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SangoChangedWorldEvent extends Event {

	private Player player;
	private Location from;
	private Location to;

	public SangoChangedWorldEvent(Player player, Location from, Location to) {
		this.from = from;
		this.to = to;
	}

	public Player getPlayer() {
		return player;
	}

	public Location getFrom() {
		return from;
	}

	public Location getTo() {
		return to;
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
