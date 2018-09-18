package com.sangodan.sangoplugin.event.block;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.sangodan.sangoapi.wrapper.SangoPlayer;
import com.sangodan.sangoplugin.main.SangoPlugin;
import com.sangodan.sangoplugin.utils.ChunkUtils;

public class WildSignRightClick implements Listener {

	@EventHandler
	public void onSignRightClick(PlayerInteractEvent event) {
		
		//Setup
		SangoPlayer player = SangoPlayer.get(event.getPlayer());
		Block block;
		Random rand = new Random();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			block = event.getClickedBlock();
			if(block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
				Sign s = (Sign) block.getState();
				
				//Wild Sign
				boolean isWild = s.getLine(0).equalsIgnoreCase("[Wild]") ? true : false;
				if(isWild) {
					player.sendMessage(ChatColor.GOLD + "Loading a wild for you:");
					Location l1 = s.getLocation();
					World world = l1.getWorld();
					int x = l1.getBlockX();
					int z = l1.getBlockZ();
					boolean isSafe = false;
					int count = 0;
					while (!isSafe && count < 20) {
						boolean isxp = rand.nextBoolean();
						boolean iszp = rand.nextBoolean();
						if(isxp) {
							x += rand.nextInt(6000) + 500;
						} else {
							x -= rand.nextInt(6000) + 500;
						}
						if(iszp) {
							z += rand.nextInt(6000) + 500;
						} else {
							z -= rand.nextInt(6000) + 500;
						}
						Location loc = new Location(world, x, 40, z);
						loc.getChunk().load(true);
						
						loc.setY(world.getHighestBlockYAt(loc) + 1);
						if(loc.getBlock().isEmpty()) {
							isSafe = true;
							ChunkUtils.loadChunksAt(loc, 4,  200L);
							player.sendMessage(ChatColor.GREEN + "Found a wild for you, teleporting...");
							new BukkitRunnable() {
								@Override
								public void run() {
									player.teleport(loc);
								}
							}.runTaskLater(SangoPlugin.instance(), 40L);
							
						} else {
							count++;
						}
					}
					if (!isSafe) {
						player.sendMessage(ChatColor.DARK_RED + "Could not find a safe place to teleport you to. Please retry.");
					}					
				}
			}
		}
	}
}
