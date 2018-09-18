package com.sangodan.sangoplugin.games;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sangodan.sangoapi.wrapper.SangoItemStack;
import com.sangodan.sangoplugin.utils.Settings;

import net.md_5.bungee.api.ChatColor;

public class OnEntityInteract implements Listener {

	@EventHandler
	public void onWitchClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		List<String> lobbies = Settings.lobbies;
		World world = player.getWorld();
		if (lobbies.contains(world.getName())) {
			if (entity.getType() == EntityType.VILLAGER) {
				player.playSound(player.getLocation(), Sound.FIZZ, 1, 1);
				if (world.getName().equals("Spleef")) {
					event.setCancelled(true);
					Inventory inv = Bukkit.createInventory(player, 27, ChatColor.DARK_GREEN + "Spleef Maps");
					ItemStack arena = new ItemStack(Material.RED_SANDSTONE, 1);
					ItemMeta arenaMeta = arena.getItemMeta();
					arenaMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Arena");
					arena.setItemMeta(arenaMeta);

					ItemStack ice = new ItemStack(Material.PACKED_ICE, 1);
					ItemMeta iceMeta = ice.getItemMeta();
					iceMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Ice");
					ice.setItemMeta(iceMeta);

					ItemStack volcano = new ItemStack(Material.OBSIDIAN, 1);
					ItemMeta volcanoMeta = volcano.getItemMeta();
					volcanoMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Volcano");
					volcano.setItemMeta(volcanoMeta);

					ItemStack tall = new ItemStack(Material.CLAY, 1);
					ItemMeta tallMeta = tall.getItemMeta();
					tallMeta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "Tall");
					tall.setItemMeta(tallMeta);
					SangoItemStack dungeon = new SangoItemStack(Material.STONE, 1);
					dungeon.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Dungeon");
					

					ItemStack random = new ItemStack(Material.NETHER_STAR, 1);
					ItemMeta randomMeta = random.getItemMeta();
					randomMeta.setDisplayName(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Random Map");
					random.setItemMeta(randomMeta);

					inv.setItem(0, arena);
					inv.setItem(2, ice);
					inv.setItem(4, volcano);
					inv.setItem(6, tall);
					inv.setItem(8, dungeon.getItem());
					inv.setItem(22, random);

					player.openInventory(inv);
				}

			}
		}
		if (world.getName().equals(Settings.worldPvp)) {
			if (entity.getType() == EntityType.VILLAGER) {
				player.playSound(player.getLocation(), Sound.FIZZ, 1, 1);
				event.setCancelled(true);
				Inventory inv = Bukkit.createInventory(player, 27, ChatColor.DARK_GREEN + "Kit Select");
				ItemStack knockback = new ItemStack(Material.STICK, 1);
				ItemMeta kbMeta = knockback.getItemMeta();
				kbMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Knockback Man");
				kbMeta.setLore(Arrays.asList(new String[] { ChatColor.DARK_PURPLE + "The Knockback Kit." }));
				knockback.setItemMeta(kbMeta);
				
				ItemStack tank = new ItemStack(Material.IRON_CHESTPLATE, 1);
				ItemMeta tankMeta = tank.getItemMeta();
				tankMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Tank");
				tankMeta.setLore(Arrays.asList(new String[] { ChatColor.DARK_PURPLE + "The Tank Kit."}));
				tank.setItemMeta(tankMeta);
				
				ItemStack swords = new ItemStack(Material.IRON_SWORD, 1);
				ItemMeta swordsMeta = swords.getItemMeta();
				swordsMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Swordsman");
				swordsMeta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "The Swords Kit"}));
				swords.setItemMeta(swordsMeta);
				
				ItemStack archer = new ItemStack(Material.BOW, 1);
				ItemMeta archerMeta = archer.getItemMeta();
				archerMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Archer");
				archerMeta.setLore(Arrays.asList(new String[] {ChatColor.DARK_PURPLE + "The Archer Kit"}));
				archer.setItemMeta(archerMeta);
				
				inv.setItem(0, knockback);
				inv.setItem(1, tank);
				inv.setItem(2, swords);
				inv.setItem(3, archer);
				
				player.openInventory(inv);
			}
		}
	}
}
