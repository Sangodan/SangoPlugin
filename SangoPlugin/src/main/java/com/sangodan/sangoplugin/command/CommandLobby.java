package com.sangodan.sangoplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sangodan.sangoapi.enums.Minigame;
import com.sangodan.sangoapi.wrapper.SangoPlayer;
import com.sangodan.sangoapi.wrapper.SangoWorld;

public class CommandLobby implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("Lobby")) {
			if(!(sender instanceof Player)) {
				return false;
			}				
			Player player = (Player) sender;
			SangoPlayer sp = SangoPlayer.get(player);
			SangoWorld world = SangoWorld.get(sp.getWorld());
			if(world.isMinigame()) {
				Minigame game = world.getMinigame();
				sp.teleport(Bukkit.getWorld(game.toString()).getSpawnLocation());
				return true;
			}
			sp.teleport(Bukkit.getWorld("Hub").getSpawnLocation());
			return true;
		}
		return false;
	}

}
