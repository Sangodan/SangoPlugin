package com.sangodan.sangoapi.wrapper;

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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
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

	private SangoWorld(World world) {
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

	private SangoWorld(String worldName) {
		this(Bukkit.getWorld(worldName));
	}

	private SangoWorld(UUID uniqueID) {
		this(Bukkit.getWorld(uniqueID));
	}

	public static HashSet<SangoWorld> getWorlds() {
		return worlds;
	}

	/**
	 * Util method to check if the world is a minigame world.
	 * 
	 * @return true if the world's minigame isn't Minigame.DEFAULT
	 */
	public boolean isMinigame() {
		return minigame != Minigame.DEFAULT;
	}

	/**
	 * Sets the minigame for this SangoWorld.
	 * 
	 * @param minigame
	 */
	public void setMinigame(Minigame minigame) {
		this.minigame = minigame;
	}

	/**
	 * Gets this world's minigame.
	 * 
	 * @return the Minigame the world has.
	 */
	public Minigame getMinigame() {
		return minigame;
	}

	/**
	 * Unloads the world given.
	 * 
	 * @param world
	 */
	public static void unloadWorld(World world) {
		if (world != null) {
			for (Player p : world.getPlayers()) {
				p.kickPlayer("Unloading the world you were in.");
			}
			Bukkit.getServer().unloadWorld(world, false);
		}
	}

	/**
	 * Util method to set if anybody has ever been on this world.
	 * 
	 * @param has the world been populated	 */
	public void hasBeenPopulated(boolean b) {
		populated = b;
	}

	/**
	 * Util method to get the amount of players in survival, usually for Minigame uses.
	 * 
	 * @return the amount of players alive
	 */
	public int getAlivePlayers() {
		int amount = 0;
		for(Player p : world.getPlayers()) {
			if(p.getGameMode() == GameMode.SURVIVAL) {
				amount++;
			}
		}
		return amount;
	}
	
	/**
	 * Util method to get if anybody has ever been on this world.
	 * 
	 * @return has the world ever been populated
	 */
	public boolean hasBeenPopulated() {
		return populated;
	}

	/**
	 * Sets the world's current life
	 * 
	 * @param Life to set
	 */
	public void setLife(Life life) {
		this.life = life;
	}

	/**
	 * Gets the world's current life
	 * 
	 * @return The current Life for this world
	 */
	public Life getLife() {
		return life;
	}

	/**
	 * Destroys this SangoWorld.
	 * 
	 * NOTE: This does not affect the world the SangoWorld is attached to.
	 */
	public void delete() {
		APIMain.instance().getLogger().info("Deleted a sangoworld for " + world.getName());
		if (worlds.contains(this)) {
			worlds.remove(this);
		}
		stopRunnable();
		nullWorld();
	}

	/**
	 * Sets the world variable for this SangoWorld to null
	 * 
	 */
	public void nullWorld() {
		this.world = null;
	}

	/**
	 * Returns a SangoWorld for the world given.
	 * This replaces making an instance for the SangoWorld to stop duplication.
	 * If no SangoWorld for this world is found, it makes a new one.
	 * 
	 * @param World to get SangoWorld for
	 * @return The SangoWorld for the given world
	 */
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

	/**
	 * Starts the WorldRunnable attached to this world.
	 */
	public void startRunnable() {
		if (runnable != null) {
			runnable.runTaskTimer(APIMain.instance(), 0L, 1L);
		}
	}

	/**
	 * Stops the WorldRunnable attached to this world.
	 */
	public void stopRunnable() {
		if (runnable != null) {
			runnable.cancel();
		}
	}

	/**
	 * Sets the runnable for this world to use. 
	 * NOTE: DO NOT TOUCH UNLESS YOU KNOW WHAT YOU'RE DOING.
	 * @param runnable
	 */
	public void setRunnable(WorldRunnable runnable) {
		this.runnable = runnable;
	}

	/**
	 * Gets the WorldRunnable attached to this world.
	 * @return The WorldRunnable this world uses.
	 */
	public WorldRunnable getRunnable() {
		return runnable;
	}

	/**
	 * Util method to check if the world is a Spleef Minigame
	 * @return If the world's Minigame is Minigame.SPLEEF
	 */
	public boolean isSpleefMinigame() {
		return minigame == Minigame.SPLEEF;
	}
	
	/**
	 * Util method, removes the _0 bit of the world if it's a minigame.
	 * E.G. ArenaSpleef_0 becomes ArenaSpleef
	 * 
	 * @return The formatted name of this world
	 */
	public String getDisplayName() {
		if (world.getName().contains("_")) {
			return world.getName().substring(0, world.getName().indexOf("_"));
		}
		return world.getName();
	}

	/**
	 * Gets the {@link World} for this SangoWorld
	 * 
	 * @return The World
	 */
	public World getWorld() {
		return this.world;
	}

	/**
	 * Gets the {@link Chunk} at the given {@link Block}
	 * @param The Block to get the Chunk at
	 * @return The Chunk at the Block
	 */
	public Chunk getChunkAt(Block block) {
		return world.getChunkAt(block);
	}

	/**
	 * Gets the {@link Chunk} at the given x and z
	 * @param The x and z to get the Chunk at
	 * @return The Chunk at the x and z
	 */
	public Chunk getChunkAt(int x, int z) {
		return world.getChunkAt(x, z);
	}

	/**
	 * Gets the {@link Chunk} at the given {@link Location}
	 * @param The Location to get the Chunk at
	 * @return The Chunk at the Location
	 */
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

	/**
	 * Returns if the world has any {@link Player}s in it
	 * @return If the world is empty
	 */
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
	public void killallMobs() {
		for(Entity e : world.getEntities()) {
			if(e instanceof Monster) {
				e.remove();
			}
		}
	}
	public void killall(EntityType ent) {
		for(Entity e : world.getEntities()) {
			if(e.getType() == ent) {
				e.remove();
			}
		}
	}
}
