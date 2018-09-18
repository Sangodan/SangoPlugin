package com.sangodan.sangoplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sangodan.sangoapi.wrapper.SangoPlayer;

import net.md_5.bungee.api.ChatColor;

public class CommandTpSilent implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("tpsilent")) {
			if (args.length == 1) {
				if (!(sender instanceof Player)) {
					return false;
				}
				SangoPlayer player = SangoPlayer.get((Player) sender);
				Player player2 = Bukkit.getPlayer(args[0]);
				if (player2 == null) {
					player.sendMessage(ChatColor.RED + "That player isn't online.");
					return true;
				}
				player.teleport(player2.getLocation());
				player.sendMessage(ChatColor.GOLD + "Silently teleported to " + player2.getDisplayName());
				// player2.sendMessage(player.getDisplayName() + ChatColor.GOLD + " teleported to you.");
				return true;
			}
			if (args.length == 2) {
				Player player = Bukkit.getPlayer(args[0]);
				Player player2 = Bukkit.getPlayer(args[1]);
				if (player == null || player2 == null) {
					sender.sendMessage(ChatColor.RED + "Those players aren't online.");
					return true;
				}
				SangoPlayer sPlayer = SangoPlayer.get(player);
				sPlayer.teleport(player2.getLocation());
				sPlayer.sendMessage(ChatColor.GOLD + "Silently teleported to " + player2.getDisplayName());
				// player2.sendMessage(sPlayer.getDisplayName() + ChatColor.GOLD + " teleported to you.");
				return true;
			}
			if (args.length == 3) {
				if (!(sender instanceof Player)) {
					return false;
				}
				SangoPlayer player = SangoPlayer.get((Player) sender);
				int x;
				int y;
				int z;
				try {
					x = Integer.parseInt(args[0]);
					y = Integer.parseInt(args[1]);
					z = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "X, Y and Z must all be numbers.");
					return true;
				}
				player.teleport(x, y, z);
				player.sendMessage(ChatColor.GOLD + "Teleported you to " + x + ", " + y + ", " + z);
				return true;
			}
			if (args.length == 4) {
				Player player = Bukkit.getPlayer(args[0]);
				if (player == null) {
					sender.sendMessage(ChatColor.RED + "That player is not online.");
					return true;
				}
				int x;
				int y;
				int z;
				try {
					x = Integer.parseInt(args[1]);
					y = Integer.parseInt(args[2]);
					z = Integer.parseInt(args[3]);
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + "X, Y and Z must all be numbers.");
					return true;
				}
				SangoPlayer sp = SangoPlayer.get(player);
				sp.teleport(x, y, z);
				return true;
			}
			return false;
		}
		return false;
	}

}
