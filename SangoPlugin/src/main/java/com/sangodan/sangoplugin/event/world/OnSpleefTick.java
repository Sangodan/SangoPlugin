package com.sangodan.sangoplugin.event.world;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sangodan.sangoapi.enums.Life;
import com.sangodan.sangoapi.event.WorldTickEvent;
import com.sangodan.sangoapi.wrapper.SangoScoreboard;
import com.sangodan.sangoapi.wrapper.SangoWorld;
import com.sangodan.sangoplugin.games.GameUtils;
import com.sangodan.sangoplugin.utils.Utils;

import net.md_5.bungee.api.ChatColor;

public class OnSpleefTick implements Listener {

	@EventHandler
	public void onTick(WorldTickEvent event) {
		try {
			SangoWorld world = event.getSangoWorld();
			if(!world.hasBeenPopulated() && !world.isEmpty()) {
				world.hasBeenPopulated(true);
			}
			if (world.isSpleefMinigame()) {
				int ticksTillGameStart = event.getLobbyTicks();
				int ticksTillStart = event.getStartingTicks();
				int secondsTillGameStart = (int) Math.ceil(ticksTillGameStart / 20);
				SangoScoreboard board = event.getBoard();
				if (board == null) {
					board = new SangoScoreboard(ChatColor.GOLD + "" + ChatColor.BOLD + "Spleef",
							world.getDisplayName().replaceAll("Spleef", ""), 8, 2, secondsTillGameStart,
							world.getWorld());
					world.setBoard(board);
				}
				
				// LOBBY
				if (world.getLife() == Life.LOBBY) {
					for (Player p : world.getPlayers()) {
						if (!p.getScoreboard().equals(board.getScoreboard())) {
							board.addPlayer(p);
						}
						if (ticksTillGameStart % 5 == 0) {
							board.setGameStart(secondsTillGameStart);
							board.update();
						}

					}
					if (world.getPlayers().size() > 1) {
						// Game has more than one person and is in lobby phase
						if (ticksTillGameStart < 1) {
							world.setLife(Life.STARTING);
							GameUtils.destroyBlocks(50, Material.STAINED_GLASS,
									world.getSpawnLocation().subtract(0, 1, 0));
							world.playSound(world.getSpawnLocation(), Sound.EXPLODE, 150, 1);
							board.update();
							for (Player p : world.getPlayers()) {
								board.removePlayer(p);
							}
						}
						ticksTillGameStart--;
						world.setLobbyTicks(ticksTillGameStart);
					} else if (world.getPlayers().size() == 1) {
						world.setLobbyTicks(400);
					}
					if (world.isEmpty() && world.hasBeenPopulated()) {
						Utils.deleteWorld(world.getWorld());
					}

					
				// STARTING
				} else if (world.getLife() == Life.STARTING) {

					if (ticksTillStart < 1) {
						world.playSound(world.getSpawnLocation(), Sound.NOTE_PIANO, 100, 1);
						world.setLife(Life.STARTED);
					} else {
						if (ticksTillStart % 20 == 0) {
							for (Player p : world.getPlayers()) {
								p.playSound(p.getLocation(), Sound.NOTE_BASS, 100, 1);
							}
						}
					}
					ticksTillStart--;
					world.setStartingTicks(ticksTillStart);
					
				// STARTED
				} else if (world.getLife() == Life.STARTED) {
					boolean isOver = world.isGameOver();
					if (isOver) {
						world.playSound(world.getSpawnLocation(), Sound.FIREWORK_BLAST, 150, 1);
						world.playSound(world.getSpawnLocation(), Sound.FIREWORK_BLAST2, 150, 1);
						world.setLife(Life.ENDED);
						GameUtils.closeGame(world.getWorld(), false);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
