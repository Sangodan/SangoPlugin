package com.sangodan.sangoplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandFillInventory implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("fillInventory") || command.getAliases().contains(label.toLowerCase())) {
			Player senderPlayer = (Player) sender;
			Server server = senderPlayer.getServer();
			if(args.length == 2) {
				Player targetPlayer = server.getPlayer(args[0]);
				if (targetPlayer == null) {
					return false;
				}
				@SuppressWarnings("deprecation")
				Material material = Material.getMaterial(Integer.parseInt(args[1]));
				if(material == null) {
					return false;
				}
				ItemStack stack = new ItemStack(material, material.getMaxStackSize());
				Inventory targetInv = targetPlayer.getInventory();
				targetInv.clear();
				for (int i = 0; i < targetInv.getSize(); i++) {
					targetInv.setItem(i, stack);
				}
				String itemName = stack.hasItemMeta() ? stack.getItemMeta().getDisplayName() : stack.getType().toString();
				senderPlayer.sendMessage(ChatColor.GREEN + "You successfully filled " + ChatColor.GOLD + targetPlayer.getName() +
						"\'s" + ChatColor.GREEN +" inventory with " + itemName + ".");
				return true;
				
			} else if (args.length == 3) {
				Player targetPlayer = server.getPlayer(args[0]);
				if (targetPlayer == null) {
					return false;
				}
				Material material = Material.matchMaterial(args[1]);
				if(material == null) {
					return false;
				}
				short meta = Short.parseShort(args[2]);
				ItemStack stack = new ItemStack(material, material.getMaxStackSize(), meta);
				Inventory targetInv = targetPlayer.getInventory();
				targetInv.clear();
				for (int i = 0; i < targetInv.getSize(); i++) {
					targetInv.setItem(i, stack);
				}
				String itemName = stack.hasItemMeta() ? stack.getItemMeta().getDisplayName() : stack.getType().toString();
				senderPlayer.sendMessage(ChatColor.GREEN + "You successfully filled " + ChatColor.GOLD + targetPlayer.getName() +
						"\'s" + ChatColor.GREEN +" inventory with " + itemName + ".");
				return true;
				
			}
			return false;
		}
		return false;
	}

}
