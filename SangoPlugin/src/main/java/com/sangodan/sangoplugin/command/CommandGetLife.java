package com.sangodan.sangoplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sangodan.sangoapi.classes.SangoWorld;
import com.sangodan.sangoapi.enums.Life;

import net.md_5.bungee.api.ChatColor;

public class CommandGetLife implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("getlife")) {
			if (!(sender instanceof Player)) {
				return false;
			}
			Player player = (Player) sender;
			SangoWorld world = SangoWorld.get(player.getWorld());
			if (world.getLife() != Life.NOLIFE) {
				player.sendMessage(ChatColor.GOLD + "Current Life in the Life cycle for world " + ChatColor.AQUA + ""
						+ ChatColor.BOLD + world.getName() + ChatColor.RESET + ChatColor.GOLD + ": " + ChatColor.GREEN + "" + ChatColor.BOLD + world.getLife().toString());
				return true;
			}
			player.sendMessage(ChatColor.RED + "World " + world.getName() + " has no life cycle.");
			return true;

		}
		return false;
	}

}
