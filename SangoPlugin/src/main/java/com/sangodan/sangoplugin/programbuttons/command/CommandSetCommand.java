package com.sangodan.sangoplugin.programbuttons.command;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sangodan.sangoplugin.main.SangoPlugin;
import com.sangodan.sangoplugin.utils.PublicVars;
import com.sangodan.sangoplugin.utils.Utils;

public class CommandSetCommand implements CommandExecutor {

	SangoPlugin pl;

	public CommandSetCommand(SangoPlugin pl) {
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("setcommand") || command.getAliases().contains(label.toLowerCase())) {
			if (!(sender instanceof Player)) {
				return false;
			}
			Player player = (Player) sender;
			String cmd = "";
			int a = 0;
			for (String s : args) {
				a++;
				cmd += s;
				if (!(a == args.length)) {
					cmd += " ";
				}
			}
			Block block = PublicVars.getButtonPlayer(player);
			if (block == null) {
				player.sendMessage(ChatColor.DARK_RED + "You haven't selected a button.");
				return true;
			}
			if (Utils.isEmptyOrNull(cmd)) {
				PublicVars.bCommands.remove(PublicVars.locify(block.getLocation()));
				player.sendMessage(ChatColor.GREEN + "Cleared the command of the button.");
				return true;
			}
			String[] strs;
			if (cmd.contains(" ")) {
				strs = cmd.split(" ");
			} else {
				strs = new String[] { cmd };
			}

			String[] ss = new String[] { "op", "deop", "ban", "unban", "sudo" };
			// SECURITY CHECKS
			List<String> forbidden = Arrays.asList(ss);

			boolean isAllowed = true;

			Iterator<String> iter = forbidden.iterator();
			while (iter.hasNext()) {
				String s = iter.next();
				if (strs[0].equals(s)) {
					isAllowed = false;
					break;
				}
			}
			if (cmd.equals("clearcommand")) {
				PublicVars.bCommands.remove(PublicVars.locify(block.getLocation()));
				player.sendMessage(ChatColor.GREEN + "Cleared the command of the button.");
				return true;
			}
			if (cmd.equals("viewcommand")) {
				String cm = PublicVars.getCommand(block.getLocation());
				player.sendMessage(ChatColor.GREEN + "This button contains the command: " + ChatColor.GOLD + "/" + cm);
				return true;
			}
			if (isAllowed) {
				player.sendMessage(ChatColor.GREEN + "Assigned command /" + cmd + " to the button.");
				PublicVars.bCommands.put(PublicVars.locify(block.getLocation()), cmd);
				return true;
			}
			player.sendMessage(ChatColor.DARK_RED + "That command is forbidden.");
			return true;
		}
		return false;
	}

}
