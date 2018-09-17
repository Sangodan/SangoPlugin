package com.sangodan.sangoplugin.event.block;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.sangodan.sangoplugin.utils.PublicVars;
import com.sangodan.sangoplugin.utils.Settings;

public class BlockRightClick implements Listener {

	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block block;
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			block = event.getClickedBlock();
			Material material = block.getType();
			
			if(player.isSneaking()) {
				;
			} else {
				if (material == Material.CLAY) {
					if (!Settings.checkpointClay) {
						player.sendMessage(ChatColor.DARK_RED + "Checkpoint Clay is disabled.");
					} else {
						PublicVars.updatePlayerCoords(player);
						player.sendMessage(ChatColor.GREEN + "Checkpoint saved.");
					}
					event.setCancelled(true);
				} else if (material == Material.COAL_BLOCK) {
					boolean hasCheckPoint;
					boolean sameWorld = false;
					if(PublicVars.getPlayerCoords(player, false) == null) {
						hasCheckPoint = false;
					} else {
						hasCheckPoint = true;
						sameWorld = Settings.worldIndependent ? player.getLocation().getWorld().equals(PublicVars.getPlayerCoords(player, false).getWorld()) : true;
					}
					if(hasCheckPoint) {
						if (!Settings.checkpointClay) {
							player.sendMessage(ChatColor.DARK_RED + "Checkpoint Clay is disabled.");
						} else if (!sameWorld){
							player.sendMessage(ChatColor.DARK_RED + "You are in a different world to your last checkpoint.");
							PublicVars.removePlayerCoords(player);
						} else {
							if(PublicVars.getPlayerCoords(player, false) == null) {
								;
							} else {
								player.teleport(PublicVars.getPlayerCoords(player, true));
								player.sendMessage(ChatColor.GREEN + "Sent back to your saved coordinates.");
							}
						}
					}
					event.setCancelled(true);
				}
			}
		}	
	}
}
