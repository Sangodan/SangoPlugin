package com.sangodan.sangoplugin.event.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OnItemRightClick implements Listener {

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		World world = player.getWorld();
		if (event.hasItem() && world.getName().equals("Hub")) {
			ItemStack itemStack = event.getItem();
			if (itemStack.getType() == Material.DIAMOND) {
				Inventory inv = Bukkit.createInventory(player, 9, ChatColor.DARK_GREEN + "Select Game");
				ItemStack snowball = new ItemStack(Material.SNOW_BALL, 1);
				ItemMeta meta = snowball.getItemMeta();
				meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Spleef");
				snowball.setItemMeta(meta);
				inv.setItem(0, snowball);
				ItemStack ladder = new ItemStack(Material.LADDER, 1);
				ItemMeta ladderMeta = ladder.getItemMeta();
				ladderMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Parkour");
				ladder.setItemMeta(ladderMeta);
				inv.setItem(1, ladder);
				ItemStack grass = new ItemStack(Material.GRASS, 1);
				ItemMeta grassMeta = grass.getItemMeta();
				grassMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Survival");
				grass.setItemMeta(grassMeta);
				inv.setItem(2, grass);
				ItemStack sword = new ItemStack(Material.IRON_SWORD, 1);
				ItemMeta swordMeta = sword.getItemMeta();
				swordMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "PvP");
				sword.setItemMeta(swordMeta);
				inv.setItem(3, sword);
				player.openInventory(inv);

			}
		}

	}
}
