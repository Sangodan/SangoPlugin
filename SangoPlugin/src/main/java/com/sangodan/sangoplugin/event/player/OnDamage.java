package com.sangodan.sangoplugin.event.player;

import java.util.Iterator;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.sangodan.sangoapi.classes.SangoPlayer;
import com.sangodan.sangoapi.classes.SangoWorld;
import com.sangodan.sangoapi.enums.Life;
import com.sangodan.sangoplugin.games.GameUtils;
import com.sangodan.sangoplugin.main.Config;
import com.sangodan.sangoplugin.utils.Settings;

public class OnDamage implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		SangoWorld world = SangoWorld.get(entity.getWorld());
		List<String> altitudes = Config.getWorldsConfig().getStringList("pvp_altitudes");
		String altitude = "";
		Iterator<String> iterator = altitudes.iterator();
		while (iterator.hasNext()) {
			String s = iterator.next();
			if (s.contains(world.getName())) {
				altitude = s;
			}
		}
		if (entity instanceof Player) {
			Player player = (Player) event.getEntity();
			if (!altitude.equals("")) {
				String[] a = altitude.split("/");
				String worldname = a[0];
				String setting = a[1];
				String yPos = a[2];
				if (worldname.equals(world.getName())) {
					if (setting.equals("above")) {
						int y = Integer.parseInt(yPos);
						if (player.getLocation().getBlockY() > y && event.getCause() != DamageCause.VOID) {
							event.setCancelled(true);
						}
					}
					if (setting.equals("below")) {
						int y = Integer.parseInt(yPos);
						if (player.getLocation().getBlockY() < y && event.getCause() != DamageCause.VOID) {
							event.setCancelled(true);
						}
					}
					if (setting.equals("between")) {
						String[] b = yPos.split(":");
						int y1 = Integer.parseInt(b[0]);
						int y2 = Integer.parseInt(b[1]);
						if (player.getLocation().getBlockY() < y1 && player.getLocation().getBlockY() > y2
								&& event.getCause() != DamageCause.VOID) {
							event.setCancelled(true);
						}
					}
				}
			}
			List<Player> players = world.getPlayers();
			SangoPlayer sp = SangoPlayer.get(player);
			if (world.getName().equals(Settings.worldPvp)) {
				if (sp.getGameMode() != GameMode.SPECTATOR && (sp.wouldDie(event.getFinalDamage()) || sp.isInVoid())) {
					if (event.getCause() != DamageCause.ENTITY_ATTACK) {
						for (Player p : players) {
							GameUtils.sendDeathMessage(event.getCause(), player, p);
						}
						sp.teleport(world.getSpawnLocation(), true);
						sp.spectatorSurvival();
						world.getWorld().spigot().strikeLightningEffect(sp.getLocation(), false);
						sp.dropInventory(5);
						sp.regen();

						event.setCancelled(true);
					}
				}
			}
			if (world.isMinigame()) {
				if (world.getLife() != Life.STARTED) {
					event.setDamage(0);
					event.setCancelled(true);
				} else if (event.getCause() == DamageCause.ENTITY_EXPLOSION
						|| event.getCause() == DamageCause.BLOCK_EXPLOSION) {

					event.setCancelled(true);
				} else if (event.getCause() == DamageCause.PROJECTILE) {
					;
				} else if (player.getGameMode() == GameMode.SURVIVAL && sp.wouldDie(event.getDamage())
						|| sp.isInVoid()) {
					event.setCancelled(true);
					for (Player p : players) {
						GameUtils.sendDeathMessage(event.getCause(), player, p);
					}
					world.playSound(world.getSpawnLocation(), Sound.AMBIENCE_THUNDER, 150, 1);
					player.teleport(world.getSpawnLocation());
					sp.setSpectator();
					sp.regen();
				}
			}

			if (!world.getWorld().hasMetadata("Vanilla") && !world.getName().equals(Settings.worldPvp)) {
				if (event.getCause() == DamageCause.FALL || event.getCause() == DamageCause.ENTITY_ATTACK) {
					event.setCancelled(true);
				}
			}
		}
		if (Settings.lobbies.contains(world.getName())) {
			event.setCancelled(true);
		}
		if (event.getEntityType() == EntityType.VILLAGER) {
			event.setCancelled(true);
		}
	}
}