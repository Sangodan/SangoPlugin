package com.sangodan.sangoplugin.programbuttons.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandEditor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("buttonedit") || command.getAliases().contains(label.toLowerCase())) {
			if(!(sender instanceof Player)) {
				return false;
			}
			Player player = (Player) sender;
			ItemStack editor = new ItemStack(Material.WOOD_HOE, 1);
			ItemMeta meta = editor.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("Used for programmable buttons.");
			meta.setDisplayName(ChatColor.AQUA + "Button Programmer");
			meta.setLore(lore);
			editor.setItemMeta(meta);
			PlayerInventory inventory = player.getInventory();
			if(inventory.firstEmpty() == -1) {
				player.sendMessage(ChatColor.DARK_RED + "Your inventory is full. Please clear a slot.");
				return true;
			}
			inventory.addItem(editor);
			player.sendMessage(ChatColor.GREEN + "Received the button editor.");
			return true;
		}
		return false;
	}

}
