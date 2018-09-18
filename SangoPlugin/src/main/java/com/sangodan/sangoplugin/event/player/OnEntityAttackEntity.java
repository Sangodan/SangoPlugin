package com.sangodan.sangoplugin.event.player;

import java.util.Random;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

import com.sangodan.sangoapi.wrapper.SangoPlayer;
import com.sangodan.sangoapi.wrapper.SangoWorld;
import com.sangodan.sangoplugin.utils.Settings;

import net.md_5.bungee.api.ChatColor;

public class OnEntityAttackEntity implements Listener {

	@EventHandler
	public void onAttack(EntityDamageByEntityEvent event) {
		Entity attacker = event.getDamager();
		Entity defender = event.getEntity();
		SangoWorld world = SangoWorld.get(attacker.getWorld());
		// If both people are players
		if (attacker instanceof Player && defender instanceof Player) {
			SangoPlayer sAttacker = SangoPlayer.get((Player) attacker);
			SangoPlayer sDefender = SangoPlayer.get((Player) defender);
			if (sDefender.wouldDie(event.getFinalDamage()) || sDefender.isInVoid()) {
				if (world.getName().equals(Settings.worldPvp)) {
					event.setCancelled(true);
					world.spigot().strikeLightningEffect(sDefender.getLocation(), false);
					sDefender.regen();
					sDefender.dropInventory(5);
					sDefender.teleport(world.getSpawnLocation(), true);
					sDefender.spectatorSurvival();
					for (Player p : world.getPlayers()) {

						if (event.getCause() == DamageCause.VOID) {
							p.sendMessage(sDefender.getDisplayName() + ChatColor.GOLD + " was yeeted into the void by "
									+ sAttacker.getDisplayName());
						}
						if (event.getCause() == DamageCause.LAVA || event.getCause() == DamageCause.FIRE_TICK
								|| event.getCause() == DamageCause.FIRE) {
							p.sendMessage(sDefender.getDisplayName() + ChatColor.GOLD + " got roasted by "
									+ sAttacker.getDisplayName());
						}
						if (event.getCause() == DamageCause.ENTITY_ATTACK) {
							p.sendMessage(sDefender.getDisplayName() + ChatColor.GOLD + " was slapped by "
									+ sAttacker.getDisplayName());
						}
						if (event.getCause() == DamageCause.PROJECTILE) {
							p.sendMessage(sDefender.getDisplayName() + ChatColor.GOLD + " was sniped by "
									+ sAttacker.getDisplayName());
						}
					}
				}
			}
		}
		// If only the defender is taking damage.
		else if (defender instanceof Player) {
			
			if(world.isSpleefMinigame()) {
				if (attacker.getType() == EntityType.SNOWBALL) {
					Random rand = new Random();
					int chance = 16;
					int r = rand.nextInt(chance);
					if (r == chance - 1) {
						Vector kb = attacker.getLocation().toVector().subtract(defender.getLocation().toVector());
						defender.setVelocity(kb);
					}
				} else {
					event.setDamage(0);
					event.setCancelled(true);
				}
			}
		}
		if(defender.getType() == EntityType.VILLAGER) {
			attacker.sendMessage("Do not beat up lobby selectors, dude!");
			event.setCancelled(true);
		}
	}
}
