package com.sangodan.sangoplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sangodan.sangoplugin.utils.Settings;

public class CommandMoreChickens implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("moreChickens") || command.getAliases().contains(label.toLowerCase())) {
			Player player = (Player) sender;
			if(args.length == 1) {
				try {
					byte amount = Byte.parseByte(args[0]);
					if (amount <= 60 && amount >= 0) {
						Settings.moreChickens = amount;
						player.sendMessage(ChatColor.GREEN + "Turned the egg hatching rate to " +
								Byte.toString(amount) + " chickens.");
						return true;
					} else {
						player.sendMessage(ChatColor.DARK_RED + "The number must be between 0 and 60.");
						return true;
					}
				} catch (NumberFormatException nfe) {
					return false;
				}
			}
			return false;
		}
		return false;
	}

}
