package com.sangodan.sangoplugin.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {
	public Messages() {}
	
	public static void NIPM(Player player) {
		player.sendMessage(ChatColor.DARK_RED + "You aren't in a party.");
	}
}
