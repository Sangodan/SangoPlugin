package com.sangodan.sangoplugin.event.player;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.sangodan.sangoplugin.main.SangoPlugin;

public class OnPlayerTeleport implements Listener {

	private SangoPlugin plugin = SangoPlugin.instance();
	
	private Server server = plugin.getServer();
	
        // Try increasing this. May be dependent on lag.
	//private final int TELEPORT_FIX_DELAY = 50; // ticks

	@SuppressWarnings("unused")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event) {

		final Player player = event.getPlayer();
		final Location loc = event.getTo();
		final World world = loc.getWorld();
		final Chunk chunk = player.getLocation().getChunk();
		final int visibleDistance = server.getViewDistance() * 16;
		
		// Fix the visibility issue one tick later
		/*server.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				updateEntities(getPlayersWithin(player, visibleDistance));
				for(Player p : event.getTo().getWorld().getPlayers()) {
					player.hidePlayer(p);
					player.showPlayer(p);
				}
			}
		}, TELEPORT_FIX_DELAY);*/
	}
	

	/*public void updateEntities(List<Player> observers) {
		
		// Refresh every single player
		for (Player player : observers) {
			updateEntity(player, observers);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void updateEntity(Entity entity, List<Player> observers) {

		World world = entity.getWorld();
		WorldServer worldServer = ((CraftWorld) world).getHandle();

		EntityTracker tracker = worldServer.tracker;
		EntityTrackerEntry entry = (EntityTrackerEntry) tracker.trackedEntities
				.get(entity.getEntityId());

		List<EntityPlayer> nmsPlayers = getNmsPlayers(observers);

		// Force Minecraft to resend packets to the affected clients
		entry.trackedPlayers.removeAll(nmsPlayers);
		entry.scanPlayers(nmsPlayers);
	}

	private List<EntityPlayer> getNmsPlayers(List<Player> players) {
		List<EntityPlayer> nsmPlayers = new ArrayList<EntityPlayer>();

		for (Player bukkitPlayer : players) {
			CraftPlayer craftPlayer = (CraftPlayer) bukkitPlayer;
			nsmPlayers.add(craftPlayer.getHandle());
		}

		return nsmPlayers;
	}
	
	private List<Player> getPlayersWithin(Player player, int distance) {
		List<Player> res = new ArrayList<Player>();
		int d2 = distance * distance;

		for (Player p : server.getOnlinePlayers()) {
			if (p.getWorld() == player.getWorld()
					&& p.getLocation().distanceSquared(player.getLocation()) <= d2) {
				res.add(p);
			}
		}

		return res;
	}*/
}
