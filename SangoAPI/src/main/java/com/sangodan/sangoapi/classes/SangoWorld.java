package com.sangodan.sangoapi.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Spigot;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sangodan.sangoapi.enums.Life;
import com.sangodan.sangoapi.enums.Minigame;
import com.sangodan.sangoapi.event.WorldTickEvent;
import com.sangodan.sangoapi.main.APIMain;

public class SangoWorld {

	private static HashSet<SangoWorld> worlds = new HashSet<SangoWorld>();
	private World world;
	private WorldRunnable runnable;
	private Minigame minigame = Minigame.DEFAULT;
	private Life life = Life.NOLIFE;
	private boolean populated = false;

	private int lobbyTicks = -1;
	private int startingTicks = -1;

	private SangoScoreboard board = null;

	public SangoWorld(World world) {
		APIMain.instance().getLogger().info("Made a sangoworld for " + world.getName());
		this.world = world;
		this.runnable = new WorldRunnable(world) {
			@Override
			public void run() {
				WorldTickEvent event = new WorldTickEvent(world);
				Bukkit.getPluginManager().callEvent(event);
			}
		};
		if (world.getName().contains("Spleef_")) {
			lobbyTicks = 400;
			startingTicks = 60;
		}
		worlds.add(this);
	}

	public SangoWorld(String worldName) {
		this(Bukkit.getWorld(worldName));
	}

	public SangoWorld(UUID uniqueID) {
		this(Bukkit.getWorld(uniqueID));
	}

	public HashSet<SangoWorld> getWorlds() {
		return worlds;
	}

	public boolean isMinigame() {
		return minigame != Minigame.DEFAULT;
	}

	public void setMinigame(Minigame minigame) {
		this.minigame = minigame;
	}

	public Minigame getMinigame() {
		return minigame;
	}

	public static void unloadWorld(World world) {
		if (world != null) {
			for (Player p : world.getPlayers()) {
				p.kickPlayer("Unloading the world you were in.");
			}
			Bukkit.getServer().unloadWorld(world, false);
		}
	}

	public void hasBeenPopulated(boolean b) {
		populated = b;
	}

	public int getAlivePlayers() {
		int amount = 0;
		for(Player p : world.getPlayers()) {
			if(p.getGameMode() == GameMode.SURVIVAL) {
				amount++;
			}
		}
		return amount;
	}
	
	public boolean hasBeenPopulated() {
		return populated;
	}

	public void setLife(Life life) {
		this.life = life;
	}

	public Life getLife() {
		return life;
	}

	public void delete() {
		APIMain.instance().getLogger().info("Deleted a sangoworld for " + world.getName());
		if (worlds.contains(this)) {
			worlds.remove(this);
		}
		stopRunnable();
		nullWorld();
	}

	public void nullWorld() {
		this.world = null;
	}

	public static SangoWorld get(World world) {
		Iterator<SangoWorld> it = worlds.iterator();
		SangoWorld sWorld = null;
		while (it.hasNext()) {
			SangoWorld w = it.next();
			if (w == null) {
				it.remove();
			} else if (w.getName().equals(world.getName())) {
				sWorld = w;
				break;
			}
		}
		if (sWorld == null) {
			sWorld = new SangoWorld(world);
		}
		return sWorld;
	}

	public void startRunnable() {
		if (runnable != null) {
			runnable.runTaskTimer(APIMain.instance(), 0L, 1L);
		}
	}

	public void stopRunnable() {
		if (runnable != null) {
			runnable.cancel();
		}
	}

	public void setRunnable(WorldRunnable runnable) {
		this.runnable = runnable;
	}

	public WorldRunnable getRunnable() {
		return runnable;
	}

	public boolean isSpleefMinigame() {
		return minigame == Minigame.SPLEEF;
	}

	public String getDisplayName() {
		if (world.getName().contains("_")) {
			return world.getName().substring(0, world.getName().indexOf("_"));
		}
		return world.getName();
	}

	public World getWorld() {
		return this.world;
	}

	public Chunk getChunkAt(Block block) {
		return world.getChunkAt(block);
	}

	public Chunk getChunkAt(int x, int z) {
		return world.getChunkAt(x, z);
	}

	public Chunk getChunkAt(Location loc) {
		return world.getChunkAt(loc);
	}

	public void loadChunk(Chunk c) {
		world.loadChunk(c);
	}

	public boolean loadChunk(int x, int z, boolean generate) {
		return world.loadChunk(x, z, generate);
	}

	public void loadChunk(int x, int z) {
		world.loadChunk(x, z);
	}

	public boolean unload(boolean bool) {
		return Bukkit.unloadWorld(world, bool);
	}

	public boolean unloadChunk(Chunk c) {
		return world.unloadChunk(c);
	}

	public boolean unloadChunk(int x, int z) {
		return world.unloadChunk(x, z);
	}

	public String getName() {
		return world.getName();
	}

	public void setAutoSave(boolean b) {
		world.setAutoSave(b);
	}

	public List<Player> getPlayers() {
		return world.getPlayers();
	}

	public Location getSpawnLocation() {
		return world.getSpawnLocation();
	}

	public void playSound(Location loc, Sound sound, float volume, float pitch) {
		world.playSound(loc, sound, volume, pitch);
	}

	public boolean isEmpty() {
		return world.getPlayers().isEmpty();
	}

	public List<SangoPlayer> getSangoPlayers() {
		List<SangoPlayer> players = new ArrayList<SangoPlayer>();
		for (Player p : getPlayers()) {
			SangoPlayer sp = SangoPlayer.get(p);
			players.add(sp);
		}
		return players;
	}

	public void sendMessage(String s) {
		for (Player p : getPlayers()) {
			p.sendMessage(s);
		}
	}

	public void kickPlayers(String reason) {
		for (Player p : getPlayers()) {
			p.kickPlayer(reason);
		}
	}

	public void sendPlayersToHub() {
		for (SangoPlayer p : getSangoPlayers()) {
			p.teleport(Bukkit.getWorld("Hub").getSpawnLocation());
		}
	}

	public boolean isGameOver() {
		boolean isGameOver = false;
		int amountLeft = getAlivePlayers();
		if (amountLeft <= 1)
			isGameOver = true;
		return isGameOver;
	}

	public Spigot spigot() {
		return world.spigot();
	}

	public int getLobbyTicks() {
		return lobbyTicks;
	}

	public int getStartingTicks() {
		return startingTicks;
	}

	public void setLobbyTicks(int ticks) {
		lobbyTicks = ticks;
	}

	public void setStartingTicks(int ticks) {
		startingTicks = ticks;
	}

	public void setBoard(SangoScoreboard board) {
		this.board = board;
	}

	public SangoScoreboard getBoard() {
		return board;
	}
}
