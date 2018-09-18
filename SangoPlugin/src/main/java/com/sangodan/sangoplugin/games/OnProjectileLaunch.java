package com.sangodan.sangoplugin.games;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.sangodan.sangoapi.enums.Minigame;
import com.sangodan.sangoapi.wrapper.SangoWorld;

public class OnProjectileLaunch implements Listener {

	@EventHandler
	public void onProjectileLaunch(final ProjectileLaunchEvent event) {
		Entity entity = event.getEntity();
		World world = entity.getWorld();
		EntityType entityType = entity.getType();
		SangoWorld sw = SangoWorld.get(world);
		if(entityType == EntityType.SPLASH_POTION) {
			ThrownPotion potion = (ThrownPotion) entity;
			potion.remove();
		}
		if(sw.isMinigame()) {
			Minigame game = sw.getMinigame();
			if(game == Minigame.SPLEEF) {
				if(entityType == EntityType.SNOWBALL) {
					Snowball snowball = (Snowball) entity;
					Random rand = new Random();
					int r = rand.nextInt(32);
					if(r == 31) {
						ProjectileSource thrower = snowball.getShooter();
						if(thrower instanceof Player) {
							Player player = (Player) thrower;
							player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "FIREBALL!");
							Location spawnAt = player.getEyeLocation().toVector().add(player.getEyeLocation().getDirection().multiply(2)).toLocation(player.getWorld());
							Fireball fireball = world.spawn(spawnAt, Fireball.class);
							fireball.setDirection(snowball.getVelocity());
							fireball.setIsIncendiary(false);
							fireball.setBounce(false);
							fireball.setYield(2F);
							fireball.getDirection().multiply(10);
						}
						snowball.remove();
					} else {
						snowball.setVelocity(snowball.getVelocity().multiply(1.5));
					}
				}
			}
		}
	}
}
