package com.sangodan.sangoplugin.utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PublicVars {

	public static void updatePlayerCoords(Player player) {
		playerCoords.put(player.getUniqueId(), player.getLocation());
	}

	public static Location getPlayerCoords(Player player, boolean print) {
		if (playerCoords.get(player.getUniqueId()) != null) {
			return playerCoords.get(player.getUniqueId());
		} else {
			if (print) {
				player.sendMessage(ChatColor.DARK_RED + "You don't have a Checkpoint Clay.");
			}
			return null;
		}
	}

	public static void removePlayerCoords(Player player) {
		if (playerCoords.get(player.getUniqueId()) != null) {
			playerCoords.put(player.getUniqueId(), null);
		}
	}

	/**
	 * Gets the button the player selected to put a command into.
	 * 
	 * @param player
	 * @return The block found for the player, or null if not found.
	 */
	public static Block getButtonPlayer(Player player) {
		UUID id = player.getUniqueId();
		Block block = button.get(id);
		return block;
	}

	public static void setButtonPlayer(Player player, Block block) {
		UUID id = player.getUniqueId();
		Block aBlock = block;
		button.put(id, aBlock);
	}

	public static HashMap<String, String> getButtonCommands() {
		return bCommands;
	}

	public static boolean hasCommand(Location loc) {
		String btn = locify(loc);
		if (bCommands.get(btn) == null) {
			return false;
		}
		return true;
	}

	public static String getCommand(Location loc) {
		String btn = locify(loc);
		return hasCommand(loc) ? bCommands.get(btn) : "";
	}

	public static void setCommands(HashMap<String, String> map) {
		bCommands = map;
	}

	public static String locify(Location loc) {
		String s = loc.getWorld().getName() + " " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " "
				+ loc.getPitch() + " " + loc.getYaw();
		return s;

	}

	public static Location locStr(String s) {
		String[] split = s.split(" ");
		if (split.length == 6) {
			Location loc = new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]),
					Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]),
					Float.parseFloat(split[5]));
			return loc;
		}
		return null;
	}

	public static HashMap<UUID, Location> playerCoords = new HashMap<UUID, Location>();

	public static HashMap<UUID, Block> button = new HashMap<UUID, Block>();

	public static HashMap<String, String> bCommands = new HashMap<String, String>();

	public static HashMap<String, Integer> worldCapacity = new HashMap<String, Integer>();

}
