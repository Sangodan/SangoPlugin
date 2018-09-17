package com.sangodan.sangoplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sangodan.sangoapi.classes.SangoPlayer;

public class CommandHub implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("Hub")) {
			if(!(sender instanceof Player)) {
				return false;
			}
			Player player = (Player) sender;
			SangoPlayer sp = SangoPlayer.get(player);
			sp.teleport(Bukkit.getWorld("Hub").getSpawnLocation());
			player.sendMessage(ChatColor.GREEN + "Teleported you to the Hub.");
			return true;
		}
		return false;
	}

	
}
