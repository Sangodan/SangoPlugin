package com.sangodan.sangoplugin.games;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import com.sangodan.sangoapi.classes.SangoPlayer;
import com.sangodan.sangoapi.classes.SangoWorld;
import com.sangodan.sangoapi.enums.Life;
import com.sangodan.sangoapi.enums.Minigame;
import com.sangodan.sangoplugin.main.SangoPlugin;
import com.sangodan.sangoplugin.utils.Settings;
import com.sangodan.sangoplugin.utils.Utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;

public class GameUtils {

	public static int spleefLobbies = 0;

	public static boolean isGameOver(World world) {
		boolean isGameOver = true;
		List<Player> players = world.getPlayers();
		int amountLeft = 0;
		for (Player p : world.getPlayers()) {
			if (!p.getGameMode().equals(GameMode.SPECTATOR)) {
				isGameOver = false;
				break;
			}
		}
		for (Player p : players) {
			if (p.getGameMode().equals(GameMode.SURVIVAL)) {
				amountLeft++;
			}
		}
		if (amountLeft == 0 || amountLeft == 1)
			isGameOver = true;
		if (world.getPlayers().isEmpty())
			isGameOver = true;
		return isGameOver;
	}

	public static void destroyBlocks(int radius, Material type, Location start) {
		int yPos = start.getBlockY();
		int xPos = start.getBlockX();
		int zPos = start.getBlockZ();
		World world = start.getWorld();
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				x += xPos;
				z += zPos;
				Block block = world.getBlockAt(new Location(world, x, yPos, z));
				if (block.getType() == type) {
					block.setType(Material.AIR);
				}
			}
		}
	}

	public static World getLobby(World world) {
		if (world.hasMetadata("Minigame")) {
			List<MetadataValue> meta = world.getMetadata("Minigame");
			String s = "";
			for (MetadataValue m : meta) {
				if (m.getOwningPlugin().getName().equals(SangoPlugin.instance().getDescription().getName())) {
					s = m.asString();
				}
			}
			World w = Bukkit.getWorld(s);
			if (w == null) {
				return Bukkit.getWorld("Hub");
			}
			return w;
		}
		return Bukkit.getWorld("Hub");
	}

	public static void closeGame(World world, boolean sendMessage) {

		String winner = "Nobody won.";
		for (Player p : world.getPlayers()) {
			if (p.getGameMode() == GameMode.SURVIVAL) {
				winner = "Winner: " + p.getName();
			}
		}
		String gameOverTitle = ChatColor.GOLD + "Game Over!";
		String winnerEndTitle = ChatColor.GREEN + winner;
		PacketPlayOutTitle timePacket = new PacketPlayOutTitle(20, 40, 20);
		PacketPlayOutTitle gameOver = new PacketPlayOutTitle(EnumTitleAction.TITLE,
				ChatSerializer.a(ComponentSerializer.toString(new TextComponent(gameOverTitle))));
		PacketPlayOutTitle winnerEnd = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE,
				ChatSerializer.a(ComponentSerializer.toString(new TextComponent(winnerEndTitle))));
		for (Player p : world.getPlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(timePacket);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(gameOver);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(winnerEnd);
		}
		Random rand = new Random();
		int n = rand.nextInt(Settings.worldsSpleef.size());
		BukkitRunnable task = new BukkitRunnable() {

			@Override
			public void run() {
				World sWorld = getSpleef(n);
				for (Player p : world.getPlayers()) {
					SangoPlayer sp = SangoPlayer.get(p);
					sp.teleport(sWorld.getSpawnLocation(), false);
					sp.spectatorSurvival();
				}
			}
		};
		task.runTaskLater(SangoPlugin.instance(), 20 * 4);
		new BukkitRunnable() {
			@Override
			public void run() {
				Utils.deleteWorld(world);
			}
		}.runTaskLater(SangoPlugin.instance(), 20 * 6);
	}

	/**
	 * Returns a spleef minigame world, or creates one if none are available.
	 * 
	 * @return The world to teleport to, or null if there are no spleef maps.
	 */
	public static World getSpleef(int mapNo) {
		List<String> worlds = Settings.worldsSpleef;
		Random rand = new Random();
		int r = 0;
		if (mapNo == -1) {
			r = rand.nextInt(worlds.size());
		} else {
			r = mapNo;
		}
		String sp = worlds.get(r);
		if (worlds.size() == 0) {

			return null;
		}
		// If a minigame is available

		for (World world : Bukkit.getWorlds()) {
			SangoWorld sw = SangoWorld.get(world);
			if (sw.isMinigame()) {
				if ((sw.getMinigame() == Minigame.SPLEEF && (world.getName().contains(sp) || mapNo == -1))) {
					if (world.getPlayers().size() < 8 && sw.getLife() == Life.LOBBY) {
						File file = new File(Bukkit.getWorldContainer(), world.getName());
						if (file.exists()) {
							return world;
						} else {
							Bukkit.unloadWorld(world, false);
						}
					}
				}
			}
		}

		World sourceWorld = Bukkit.getWorld(sp);
		String name = sp + "_" + Integer.toString(0);

		// World creation
		for (int i = 0; i <= spleefLobbies + 1; i++) {
			name = sp + "_" + Integer.toString(i);

			// Make world
			if (Bukkit.getWorld(name) == null) {

				Utils.copyWorld(sourceWorld, name);
				spleefLobbies++;
				break;
			}
		}
		World world = Bukkit.getWorld(name);
		return world;
	}

	/**
	 * Sends a death message about a player's death
	 * 
	 * @param cause
	 *            - How the player died
	 * @param player
	 *            - The player who died
	 * @param p
	 *            - The player to send the message to
	 */
	public static void sendDeathMessage(DamageCause cause, Player player, Player p) {
		if (cause == DamageCause.LAVA || cause == DamageCause.FIRE_TICK || cause == DamageCause.FIRE) {
			p.sendMessage(player.getDisplayName() + ChatColor.GOLD + " got SMOKED");
		} else if (cause == DamageCause.VOID) {
			p.sendMessage(player.getDisplayName() + ChatColor.GOLD + " fell into the abyss.");
		} else if (cause == DamageCause.FALL) {
			p.sendMessage(player.getDisplayName() + ChatColor.GOLD + " attempted to teleport back to the arena.");
		} else if (cause == DamageCause.ENTITY_EXPLOSION) {
			p.sendMessage(player.getDisplayName() + ChatColor.GOLD + " was blown up by a terrorist.");
		} else {
			p.sendMessage(player.getDisplayName() + ChatColor.GOLD
					+ " died for a reason Sangodan hasn't coded a death message for yet.");
		}
	}
}
