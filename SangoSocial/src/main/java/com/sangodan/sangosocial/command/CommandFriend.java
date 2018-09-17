package com.sangodan.sangosocial.command;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sangodan.sangosocial.friend.Friends;

public class CommandFriend implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("friend") || command.getAliases().contains(label.toLowerCase())) {
			if(!(sender instanceof Player)) {
				return false;
			}
			Player player = (Player) sender;
			if(args.length == 1) {
				if(args[0].equals("list")) {
					player.sendMessage(Friends.listFriends(player));
					return true;
				}
				for (Player pl : Bukkit.getOnlinePlayers()) {
					if (pl.getName().equals(args[0])) {
						Player p = pl;
						if(!Friends.canBeFriends(player, p)) {
							return true;
						}
						HashSet<UUID> u = Friends.getFriendRequests(p);
						if(u.contains(player.getUniqueId())) {
							Friends.acceptFriend(player, p);
							player.sendMessage(p.getDisplayName() + ChatColor.GREEN + " is now your friend!");
							p.sendMessage(player.getDisplayName() + ChatColor.GREEN + " is now your friend!");
							return true;
						}
						Friends.requestFriend(player, p);
						player.sendMessage(ChatColor.GREEN + "Requested " + p.getDisplayName() + ChatColor.GREEN + " to be your friend.");
						p.sendMessage(player.getDisplayName() + ChatColor.GREEN + " has requested to be your friend.");
						return true;
					}
				}
				return false;
			}
			if(args.length == 2) {
				if(args[0].equals("add") || args[0].equals("a")) {
					Player p = Bukkit.getPlayer(args[1]);
					if(p == null) {
						player.sendMessage(ChatColor.DARK_RED + "That player was not found.");
						return true;
					}
					if(!Friends.canBeFriends(player, p)) {
						return true;
					}
					HashSet<UUID> u = Friends.getFriendRequests(p);
					if(u.contains(player.getUniqueId())) {
						Friends.acceptFriend(player, p);
						player.sendMessage(p.getDisplayName() + ChatColor.GREEN + " is now your friend!");
						p.sendMessage(player.getDisplayName() + ChatColor.GREEN + " is now your friend!");
						return true;
					}
					Friends.requestFriend(player, p);
					player.sendMessage(ChatColor.GREEN + "Requested " + p.getDisplayName() + ChatColor.GREEN + " to be your friend.");
					p.sendMessage(player.getDisplayName() + ChatColor.GREEN + " has requested to be your friend.");
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}

}
