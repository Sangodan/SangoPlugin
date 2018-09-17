package com.sangodan.sangoplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CommandGetWorld implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("getworld")) {
			if(!(sender instanceof Player)) {
				return false;
			}
			Player player = (Player) sender;
			String world = player.getWorld().getName();
			player.sendMessage(ChatColor.GOLD + "You are in world: " + ChatColor.DARK_AQUA + world);
			return true;
		}
		return false;
	}

}
