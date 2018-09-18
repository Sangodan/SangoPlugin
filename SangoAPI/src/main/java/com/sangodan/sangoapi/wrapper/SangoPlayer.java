package com.sangodan.sangoapi.wrapper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;
import org.bukkit.scheduler.BukkitRunnable;

import com.sangodan.sangoapi.main.APIMain;

public class SangoPlayer {

	private static final int teleportDelay = 10;
	private static HashSet<SangoPlayer> players = new HashSet<SangoPlayer>();
	private final Player player;

	public SangoPlayer(Player p) {
		this.player = p;
	}
	
	public SangoPlayer(String s) {
		this(Bukkit.getPlayer(s));
	}

	public SangoPlayer(UUID id) {
		this(Bukkit.getPlayer(id));
	}
	
	public void spectatorSurvival() {
		setSpectator();
		new BukkitRunnable() {
			@Override
			public void run() {
				setSurvival();
			}
		}.runTaskLater(APIMain.instance(), teleportDelay);
	}

	public static SangoPlayer get(Player p) {
		SangoPlayer player = null;
		Iterator<SangoPlayer> it = players.iterator();
		while (it.hasNext()) {
			SangoPlayer splayer = it.next();
			if (splayer.getPlayer().isOnline() == false || splayer == null || splayer.exists()) {
				it.remove();
			} else if (splayer.getName().equals(p.getName())) {
				player = splayer;
			}
		}
		if (player == null) {
			player = new SangoPlayer(p);
		}
		return player;
	}

	public boolean exists() {
		return player != null;
	}
	
	public boolean canBuild() {
		return player.hasPermission("SangoBuild.build");
	}

	public void kill() {
		player.setHealth(0.0);
	}
	
	public void respawn() {
		player.spigot().respawn();
	}
	
	public void updateInventory() {
		player.updateInventory();
	}

	public Server getServer() {
		return player.getServer();
	}

	public UUID getUUID() {
		return player.getUniqueId();
	}

	public boolean isInVoid() {
		return (player.getLocation().getBlockY() < 0);
	}

	public boolean wouldDie(double damage) {
		return (player.getHealth() - damage) <= 0 && player.getGameMode() == GameMode.SURVIVAL;
	}

	public void clearChat() {
		for (int i = 0; i < 20; i++)
			player.sendMessage("");
	}

	public boolean hasPermission(Permission p) {
		return player.hasPermission(p);
	}

	public boolean hasPermission(String s) {
		return player.hasPermission(s);
	}
	
	public boolean isPermissionSet(Permission p) {
		return player.isPermissionSet(p);
	}
	
	public boolean isPermissionSet(String s) {
		return player.isPermissionSet(s);
	}
	
	public void regen() {
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
	}

	public Location getLocation() {
		return this.player.getLocation();
	}

	public void updateHealth() {
		this.player.setHealth(this.player.getHealth());
	}

	public GameMode getGameMode() {
		return player.getGameMode();

	}

	public void setGameMode(GameMode mode) {
		player.setGameMode(mode);
	}

	public void setSurvival() {
		player.setGameMode(GameMode.SURVIVAL);
	}

	public void setCreative() {
		player.setGameMode(GameMode.CREATIVE);
	}

	public void setAdventure() {
		player.setGameMode(GameMode.ADVENTURE);
	}

	public void setSpectator() {
		player.setGameMode(GameMode.SPECTATOR);
	}

	public void teleport(Location loc) {
		teleport(loc, false);

	}

	public ItemStack[] getArmor() {
		return player.getInventory().getArmorContents();
	}

	public void setArmor(ItemStack[] stack) {
		player.getInventory().setArmorContents(stack);
	}

	public void teleportVanilla(Location loc) {
		player.teleport(loc);
	}
	
	public void teleport(Location loc, boolean playSound) {
		Chunk chunk = loc.getChunk();
		World world = chunk.getWorld();
		int loadDistance = 2;
		int chX = chunk.getX();
		int chZ = chunk.getZ();
		for (int x = -loadDistance; x <= loadDistance; x++) {
			for (int z = -loadDistance; z <= loadDistance; z++) {
				Chunk c = world.getChunkAt(chX + x, chZ + z);
				c.load();
			}
		}
		if(player.isDead()) {
			respawn();
		}
		chunk.load();
		if (playSound)
			player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
		new BukkitRunnable() {
			@Override
			public void run() {
				player.teleport(loc);
				if (playSound)
					player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
			}
		}.runTaskLater(APIMain.instance(), teleportDelay);

	}

	public double getHealth() {
		return player.getHealth();
	}

	public void setHealth(double d) {
		player.setHealth(d);
	}

	public PlayerInventory getInventory() {
		return player.getInventory();
	}

	public void dropInventory() {
		for (ItemStack i : player.getInventory().getContents()) {
			if (i != null && i.getType() != Material.AIR) {
				player.getWorld().dropItemNaturally(player.getLocation(), i);
				player.getInventory().remove(i);
			}
		}
		for (ItemStack i : player.getInventory().getArmorContents()) {
			if (i != null && i.getType() != Material.AIR) {
				player.getWorld().dropItemNaturally(player.getLocation(), i);
			}
		}
		player.getInventory().setArmorContents(new ItemStack[] { null, null, null, null });
	}

	public void dropInventory(int randomizer) {
		Random rand = new Random();
		for (ItemStack i : player.getInventory().getContents()) {
			if (i != null && i.getType() != Material.AIR) {
				int x = rand.nextInt(randomizer);
				if (x == randomizer - 1) {
					player.getWorld().dropItemNaturally(player.getLocation(), i);
				}
				player.getInventory().remove(i);
			}
		}
		for (ItemStack i : player.getInventory().getArmorContents()) {
			if (i != null && i.getType() != Material.AIR) {
				int x = rand.nextInt(randomizer);
				if (x == randomizer - 1) {
					player.getWorld().dropItemNaturally(player.getLocation(), i);
				}
			}
		}
		player.getInventory().setArmorContents(new ItemStack[] { null, null, null, null });
	}

	public void teleport(int x, int y, int z) {
		Location loc = new Location(this.getWorld(), x, y, z);
		this.teleport(loc);
	}

	public void teleport(World world, int x, int y, int z) {
		Location loc = new Location(world, x, y, z);
		this.teleport(loc);
	}

	public void clear() {
		player.getInventory().clear();
	}

	public void clearAll() {
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[] { null, null, null, null });
	}

	public Player getPlayer() {
		return this.player;
	}

	public void blankScoreboard() {
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}

	public void sendMessage(String string) {
		player.sendMessage(string);
	}

	public String getName() {
		return this.player.getName();
	}

	public String getDisplayName() {
		return this.player.getDisplayName();
	}

	public World getWorld() {
		return this.player.getWorld();
	}
}
