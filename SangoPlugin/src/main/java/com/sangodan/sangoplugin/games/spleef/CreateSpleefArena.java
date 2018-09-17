package com.sangodan.sangoplugin.games.spleef;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sangodan.sangoapi.classes.SangoWorld;
import com.sangodan.sangoplugin.games.GameUtils;

public class CreateSpleefArena implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("tpSpleef") || command.getAliases().contains("tpSpleef")) {
			Player player = null;
			if(args.length == 1) {
				player = Bukkit.getPlayer(args[0]);
				if(player == null) {
					return false;
				}
			} else {
				player = (Player) sender;
			}
			World playerWorld = player.getWorld();
			SangoWorld sw = SangoWorld.get(playerWorld);
			
			World world = GameUtils.getSpleef(-1);
			if(!sw.isMinigame()) {
				player.teleport(world.getSpawnLocation());
				player.sendMessage(ChatColor.GREEN + "Teleported you to a spleef game.");
				return true;
			}
			player.sendMessage(ChatColor.RED + "You cannot do that in a minigame.");
			return true;
		}
		return false;
	}
}
