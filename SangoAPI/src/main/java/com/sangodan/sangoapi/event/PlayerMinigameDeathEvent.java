package com.sangodan.sangoapi.event;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.sangodan.sangoapi.enums.Minigame;
import com.sangodan.sangoapi.wrapper.SangoPlayer;
import com.sangodan.sangoapi.wrapper.SangoWorld;

public class PlayerMinigameDeathEvent extends Event {

	private Player player;
	private SangoPlayer sangoPlayer;
	private World world;
	private SangoWorld sangoWorld;
	private Minigame minigame;

	public PlayerMinigameDeathEvent(Player player) {
		this.player = player;
		this.sangoPlayer = SangoPlayer.get(player);
		this.world = player.getWorld();
		this.sangoWorld = SangoWorld.get(world);
		this.minigame = sangoWorld.getMinigame();

	}

	public Player getPlayer() {
		return player;
	}

	public Minigame getMinigame() {
		return minigame;
	}
	
	public SangoPlayer getSangoPlayer() {
		return sangoPlayer;
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
