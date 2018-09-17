package com.sangodan.sangoplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sangodan.sangoplugin.utils.Settings;

public class CommandSwitchCheckpointClay implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("switchCheckpointClay") || command.getAliases().contains(label.toLowerCase())) {
			boolean checkpointClay = Settings.checkpointClay;
			checkpointClay = !checkpointClay;
			Settings.checkpointClay = checkpointClay;
			Player player = (Player) sender;
			if(checkpointClay) {
				player.sendMessage(ChatColor.GREEN + "Checkpoint Clay has been Enabled.");
			} else {
				player.sendMessage(ChatColor.DARK_RED + "Checkpoint Clay has been Disabled.");
			}
			return true;
		}
		return false;
	}

}
