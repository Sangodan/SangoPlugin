package com.sangodan.sangoplugin.event.player;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class OnJoin implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.setGameMode(GameMode.SURVIVAL);
		player.teleport(Bukkit.getWorld("Hub").getSpawnLocation());
		
		player.getInventory().clear();
		ItemStack stack = new ItemStack(Material.DIAMOND, 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + "Game Select");
		stack.setItemMeta(meta);
		player.getInventory().setItem(0, stack);
	}
}
