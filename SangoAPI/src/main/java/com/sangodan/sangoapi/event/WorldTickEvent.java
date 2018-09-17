package com.sangodan.sangoapi.event;

import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.sangodan.sangoapi.classes.SangoScoreboard;
import com.sangodan.sangoapi.classes.SangoWorld;

public class WorldTickEvent extends Event {

	World world;
	SangoWorld sWorld;
	int lobbyTicks;
	int startingTicks;
	SangoScoreboard board;
	
	public WorldTickEvent(World world) {
		this.world = world;
		this.sWorld = SangoWorld.get(world);
		this.lobbyTicks = sWorld.getLobbyTicks();
		this.startingTicks = sWorld.getStartingTicks();
		this.board = sWorld.getBoard();
	}
	
	public World getWorld() {
		return world;
	}
	
	public SangoScoreboard getBoard() {
		return board;	
	}
	
	public int getLobbyTicks() {
		return lobbyTicks;
	}
	
	public int getStartingTicks() {
		return startingTicks;
	}
	
	public List<Player> getPlayers() {
		return world.getPlayers();
	}
	
	public SangoWorld getSangoWorld() {
		return sWorld;
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
