package com.sangodan.sangoplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sangodan.sangoplugin.utils.Settings;

public class CommandSwitchWorldIndependent implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("switchCheckpointWorldIndependence") || command.getAliases().contains(label.toLowerCase())) {
			boolean indep = Settings.worldIndependent;
			Settings.worldIndependent = !indep;
			Player player = (Player) sender;
			if(Settings.worldIndependent) {
				player.sendMessage(ChatColor.GREEN + "Checkpoint Clay world independence enabled.");
			} else {
				player.sendMessage(ChatColor.DARK_RED + "Checkpoint Clay world independence disabled.");		
			}
			return true;
		}
		return false;
	}

}
