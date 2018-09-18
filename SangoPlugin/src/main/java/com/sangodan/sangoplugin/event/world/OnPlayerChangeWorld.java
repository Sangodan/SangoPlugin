package com.sangodan.sangoplugin.event.world;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.sangodan.sangoapi.enums.Life;
import com.sangodan.sangoapi.wrapper.SangoPlayer;
import com.sangodan.sangoapi.wrapper.SangoWorld;
import com.sangodan.sangoplugin.main.SangoPlugin;
import com.sangodan.sangoplugin.utils.Settings;

import net.md_5.bungee.api.ChatColor;

public class OnPlayerChangeWorld implements Listener {

	@SuppressWarnings("unchecked")
	@EventHandler
	public void onPlayerLeave(PlayerChangedWorldEvent event) {
		SangoWorld worldLeft = SangoWorld.get(event.getFrom());
		Player player = event.getPlayer();
		SangoPlayer sPlayer = SangoPlayer.get(player);
		SangoWorld worldJoined = SangoWorld.get(player.getWorld());
		sPlayer.regen();
		sPlayer.blankScoreboard();
		sPlayer.clearAll();
		sPlayer.setSurvival();

		// Leaving World
		if (worldLeft.isMinigame()) {
			for (Player p : worldLeft.getPlayers()) {
				p.sendMessage(player.getDisplayName() + ChatColor.GOLD + " left.");
			}
		}
		// Joining World
		if (worldJoined.getName().equals("Hub")) {
			sPlayer.clearChat();
			ItemStack stack = new ItemStack(Material.DIAMOND, 1);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + "Game Select");
			stack.setItemMeta(meta);
			player.getInventory().setItem(0, stack);
		}
		if (worldJoined.getName().equals("Survival")) {
			File file = new File(SangoPlugin.instance().getDataFolder(), "/survivalinv/" + player.getName() + ".yml");
			if (file.exists()) {
				YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
				ItemStack[] content = ((List<ItemStack>) config.get("inventory.armor")).toArray(new ItemStack[0]);
				player.getInventory().setArmorContents(content);
				content = ((List<ItemStack>) config.get("inventory.content")).toArray(new ItemStack[0]);
				String[] srs = config.getString("world.location").split(",");
				Location loc = new Location(worldJoined.getWorld(), Double.parseDouble(srs[0]),
						Double.parseDouble(srs[1]), Double.parseDouble(srs[2]), Float.parseFloat(srs[3]),
						Float.parseFloat(srs[4]));
				player.getInventory().setContents(content);
				player.teleport(loc);
			}
		}
		if (worldJoined.getName().equals(Settings.worldPvp)) {
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective object = board.registerNewObjective("uplayerhealth", "health");
			object.setDisplayName(ChatColor.RED + "❤");
			object.setDisplaySlot(DisplaySlot.BELOW_NAME);
			Objective object2 = board.registerNewObjective("lplayerhealth", "health");
			object2.setDisplayName(ChatColor.RED + "❤");
			object2.setDisplaySlot(DisplaySlot.PLAYER_LIST);
			for (Player p : worldJoined.getPlayers()) {
				if (p.getHealth() > 0) {
					p.setHealth(p.getHealth() - 0.0001);
				}
			}
			player.setScoreboard(board);
		}

		// Join Messages
		if (worldLeft.isMinigame() && worldLeft.getLife() != Life.ENDED) {
			worldLeft.sendMessage(player.getDisplayName() + ChatColor.GOLD + " left the game.");
		}
		if (worldJoined.isMinigame()) {
			worldJoined.sendMessage(player.getDisplayName() + ChatColor.GOLD + " has joined! [" + ChatColor.GREEN
					+ worldJoined.getPlayers().size() + ChatColor.GOLD + "]");
		}

		sPlayer.clearChat();
		if (worldJoined.getName().contains("Spleef_")) {
			ItemStack stack = new ItemStack(Material.DIAMOND_SPADE);
			stack.addEnchantment(Enchantment.DIG_SPEED, 5);
			ItemMeta meta = stack.getItemMeta();
			meta.spigot().setUnbreakable(true);
			stack.setItemMeta(meta);
			player.getInventory().addItem(stack);
			player.updateInventory();
		}
	}
}
