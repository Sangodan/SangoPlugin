package com.sangodan.sangoplugin.programbuttons.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.sangodan.sangoplugin.utils.PublicVars;

public class ButtonRightClick implements Listener {

	@EventHandler
	public void onButtonClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack item;
		Block block;
		Material material;
		// If right-click
		if (action == Action.RIGHT_CLICK_BLOCK) {
			block = event.getClickedBlock();
			material = block.getType();
			item = event.hasItem() ? event.getItem() : null;
			// If button
			if (material == Material.STONE_BUTTON) {
				// If item is editor
				if (item != null && item.getType() == Material.WOOD_HOE && item.hasItemMeta()
						&& item.getItemMeta().hasLore()
						&& item.getItemMeta().getLore().contains("Used for programmable buttons.")) {

					PublicVars.setButtonPlayer(player, block);
					player.sendMessage(ChatColor.GREEN + "Set button for assigning command to.");

				} else if (PublicVars.hasCommand(block.getLocation())) {
					String cmd = PublicVars.getCommand(block.getLocation());
					if (cmd.contains("@a")) {
						for (Player p : Bukkit.getOnlinePlayers()) {
							String com = cmd.replaceAll("@a", p.getName());
							player.getServer().dispatchCommand(player.getServer().getConsoleSender(), com);
						}
					} else if (cmd.contains("@p")) {
						Location blockLoc = block.getLocation();
						Player player1 = player;
						double distance = -1;
						for (Player p : player.getWorld().getPlayers()) {
							Location playerLoc = p.getLocation();
							double dis = Math.abs(blockLoc.distance(playerLoc));
							if (distance == -1 || dis < distance) {
								distance = dis;
								player1 = p;
							}
						}
						String com = cmd.replaceAll("@p", player1.getName());
						player.getServer().dispatchCommand(player.getServer().getConsoleSender(), com);

					} else if (cmd.contains("@r")) {
						List<Player> onlinePlayers = new ArrayList<Player>(Bukkit.getOnlinePlayers());
						int lll = onlinePlayers.size();
						Random rand = new Random();
						int p = rand.nextInt(lll);
						Player playerer = onlinePlayers.get(p);
						String com = cmd.replaceAll("@r", playerer.getName());

						player.getServer().dispatchCommand(player.getServer().getConsoleSender(), com);
					} else {
						player.getServer().dispatchCommand(player.getServer().getConsoleSender(), cmd);
					}
				}
			}
		}
	}
}
