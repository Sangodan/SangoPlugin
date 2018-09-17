package com.sangodan.sangoplugin.command;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sangodan.sangoplugin.main.Config;

import net.md_5.bungee.api.ChatColor;

public class CommandPvpAltitude implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("PvpAltitude") || command.getAliases().contains(label.toLowerCase())) {
			List<String> altitudes = Config.getWorldsConfig().getStringList("pvp_altitudes");
			if (args.length == 1) {
				if (!(sender instanceof Player)) {
					return false;
				}
				Player player = (Player) sender;
				World world = player.getWorld();
				if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")) {
					Iterator<String> iterator = altitudes.iterator();
					while (iterator.hasNext()) {
						String s = iterator.next();
						if (s.contains(world.getName())) {
							iterator.remove();
						}
					}
					Config.getWorldsConfig().set("pvp_altitudes", altitudes);
					try {
						Config.getWorldsConfig().save(Config.getWorldsFile());
					} catch (IOException e) {
						e.printStackTrace();
					}
					player.sendMessage(
							ChatColor.GREEN + "All PVP restrictions removed from world " + world.getName() + ".");
					return true;
				}
				if (args[0].equalsIgnoreCase("help")) {
					String message = ChatColor.GOLD + "All PVP altitude commands:\n" + ChatColor.GOLD + "remove: "
							+ ChatColor.GREEN + "removes all pvp restrictions from the current world\n" + ChatColor.GOLD
							+ "above [Y]: " + ChatColor.GREEN + "stops people using pvp above the selected Y coord\n"
							+ ChatColor.GOLD + "below [Y]: " + ChatColor.GREEN + "stops people using pvp below the selected Y coord\n" + ChatColor.GOLD + 
							"between [YABOVE] [YBELOW]: " + ChatColor.GREEN + "stops people using pvp between the selected Y coords";
					player.sendMessage(message);
					return true;
				}
				return false;
			}
			if (args.length == 2) {
				if (!(sender instanceof Player)) {
					return false;
				}
				Player player = (Player) sender;
				World world = player.getWorld();
				if (args[0].equalsIgnoreCase("above")) {
					int aboveY;
					try {
						aboveY = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "Incorrect usage: /PvpAltitude above [NUMBER]");
						return true;
					}
					Iterator<String> iterator = altitudes.iterator();
					while (iterator.hasNext()) {
						String s = iterator.next();
						if (s.contains(world.getName())) {
							iterator.remove();
						}
					}
					String configLine = world.getName() + "/above/" + aboveY;
					altitudes.add(configLine);
					Config.getWorldsConfig().set("pvp_altitudes", altitudes);
					try {
						Config.getWorldsConfig().save(Config.getWorldsFile());
					} catch (IOException e) {
						e.printStackTrace();
					}
					player.sendMessage(
							ChatColor.GREEN + "PVP above Y: " + aboveY + " disabled in world " + world.getName() + ".");
					return true;
				}
				if (args[0].equalsIgnoreCase("below")) {
					int belowY;
					try {
						belowY = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "Incorrect usage: /PvpAltitude below [NUMBER]");
						return true;
					}
					Iterator<String> iterator = altitudes.iterator();
					while (iterator.hasNext()) {
						String s = iterator.next();
						if (s.contains(world.getName())) {
							iterator.remove();
						}
					}
					String configLine = world.getName() + "/below/" + belowY;
					altitudes.add(configLine);
					Config.getWorldsConfig().set("pvp_altitudes", altitudes);
					try {
						Config.getWorldsConfig().save(Config.getWorldsFile());
					} catch (IOException e) {
						e.printStackTrace();
					}
					player.sendMessage(
							ChatColor.GREEN + "PVP below Y: " + belowY + " disabled in world " + world.getName() + ".");
					return true;
				}
				return false;
			}
			if (args.length == 3) {
				if(!(sender instanceof Player)) {
					return false;
				}
				Player player = (Player) sender;
				World world = player.getWorld();
				if(args[0].equalsIgnoreCase("between")) {
					int yAbove;
					int yBelow;
					try {
						yAbove = Integer.parseInt(args[1]);
						yBelow = Integer.parseInt(args[2]);
					} catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "Incorrect usage: /PvpAltitude between [TOP] [BOTTOM]");
						return true;
					}
					Iterator<String> iterator = altitudes.iterator();
					while (iterator.hasNext()) {
						String s = iterator.next();
						if (s.contains(world.getName())) {
							iterator.remove();
						}
					}
					String configLine = world.getName() + "/between/" + yAbove + ":" + yBelow;
					altitudes.add(configLine);
					Config.getWorldsConfig().set("pvp_altitudes", altitudes);
					try {
						Config.getWorldsConfig().save(Config.getWorldsFile());
					} catch (IOException e) {
						e.printStackTrace();
					}
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}

}
