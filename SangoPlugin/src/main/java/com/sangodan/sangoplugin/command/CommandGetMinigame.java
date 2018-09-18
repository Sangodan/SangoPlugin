package com.sangodan.sangoplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sangodan.sangoapi.wrapper.SangoWorld;

public class CommandGetMinigame implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("getminigame") || command.getAliases().contains(label.toLowerCase())) {
			if(!(sender instanceof Player)) {
				return false;
			}	
			Player player = (Player) sender;
			SangoWorld world = SangoWorld.get(player.getWorld());
			player.sendMessage("Minigame for " + world.getName() + ": " + world.getMinigame().toString());
		}
		return false;
	}

}
