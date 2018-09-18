package com.sangodan.sangoplugin.event.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.sangodan.sangoapi.wrapper.SangoItemStack;
import com.sangodan.sangoapi.wrapper.SangoPlayer;
import com.sangodan.sangoplugin.games.GameUtils;
import com.sangodan.sangoplugin.utils.Settings;

public class OnInventoryChange implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		SangoPlayer player = SangoPlayer.get(p);
		ItemStack stack = event.getCurrentItem();
		World world = player.getWorld();
		Inventory inventory = event.getInventory();
		if (stack != null) {
			if (world.getName().equals("Hub")) {
				if (event.getInventory().getName().equals(ChatColor.DARK_GREEN + "Select Game")) {
					if (stack.getType() == Material.SNOW_BALL) {
						player.teleport(Bukkit.getWorld("Spleef").getSpawnLocation());
					}
					if (stack.getType() == Material.LADDER) {
						player.teleport(Bukkit.getWorld("Parkour").getSpawnLocation());
					}
					if (stack.getType() == Material.GRASS) {
						player.teleport(Bukkit.getWorld("Survival").getSpawnLocation());
					}
					if (stack.getType() == Material.IRON_SWORD) {
						player.teleport(Bukkit.getWorld("Pvp").getSpawnLocation());
					}
					event.setCancelled(true);
				}
				if (!player.hasPermission("SangoPlugin.inventorymove.hub")) {
					event.setResult(Result.DENY);
					event.setCancelled(true);
				}
			}
			if (world.getName().equals("Spleef")) {
				if (inventory.getName().equals(ChatColor.DARK_GREEN + "Spleef Maps")) {
					if (stack.getType() == Material.RED_SANDSTONE) {
						World tpWorld = GameUtils.getSpleef(0);
						player.teleport(tpWorld.getSpawnLocation());
					}
					if (stack.getType() == Material.PACKED_ICE) {
						World tpWorld = GameUtils.getSpleef(1);
						player.teleport(tpWorld.getSpawnLocation());
					}
					if (stack.getType() == Material.OBSIDIAN) {
						World tpWorld = GameUtils.getSpleef(2);
						player.teleport(tpWorld.getSpawnLocation());
					}
					if (stack.getType() == Material.CLAY) {
						World tpWorld = GameUtils.getSpleef(3);
						player.teleport(tpWorld.getSpawnLocation());
					}
					if (stack.getType() == Material.STONE) {
						World tpWorld = GameUtils.getSpleef(4);
						player.teleport(tpWorld.getSpawnLocation());
					}
					if (stack.getType() == Material.NETHER_STAR) {
						World tpWorld = GameUtils.getSpleef(-1);
						player.teleport(tpWorld.getSpawnLocation());
					}
					event.setCancelled(true);
				}
			}
			if (world.getName().equals(Settings.worldPvp)) {
				if (inventory.getName().equals(ChatColor.DARK_GREEN + "Kit Select")) {
					if (stack.getType() == Material.STICK) {
						ItemStack stick = new ItemStack(Material.STICK, 1);
						stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
						ItemMeta stickMeta = stick.getItemMeta();
						stickMeta.setDisplayName(ChatColor.GOLD + "Knocky Stick");
						stick.setItemMeta(stickMeta);
						ItemStack sword = new ItemStack(Material.WOOD_SWORD, 1);
						sword.addEnchantment(Enchantment.KNOCKBACK, 1);
						ItemMeta swordMeta = sword.getItemMeta();
						swordMeta.spigot().setUnbreakable(true);
						swordMeta.setDisplayName(ChatColor.GOLD + "Knocky Sword");
						sword.setItemMeta(swordMeta);
						ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
						ItemMeta chestMeta = chestplate.getItemMeta();
						chestMeta.spigot().setUnbreakable(true);
						chestplate.setItemMeta(chestMeta);
						player.getInventory().clear();
						player.getInventory().setItem(0, sword);
						player.getInventory().setItem(1, stick);
						player.getInventory().setArmorContents(new ItemStack[] { null, null, chestplate, null });
						player.updateInventory();
					}
					if (stack.getType() == Material.IRON_CHESTPLATE) {
						ItemStack sword = new ItemStack(Material.IRON_SWORD, 1);
						ItemMeta swordMeta = sword.getItemMeta();
						swordMeta.setDisplayName(ChatColor.GOLD + "Tank Sword");
						sword.setItemMeta(swordMeta);
						SangoItemStack helm = new SangoItemStack(new ItemStack(Material.IRON_HELMET, 1));
						helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
						helm.setUnbreakable(true);
						SangoItemStack chest = new SangoItemStack(Material.IRON_CHESTPLATE, 1);
						chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
						chest.setUnbreakable(true);
						SangoItemStack legs = new SangoItemStack(new ItemStack(Material.IRON_LEGGINGS, 1));
						legs.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
						legs.setUnbreakable(true);
						SangoItemStack boots = new SangoItemStack(Material.IRON_BOOTS, 1);
						boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
						boots.setUnbreakable(true);
						Potion pot = new Potion(PotionType.INSTANT_HEAL, 2);
						pot.setSplash(false);
						SangoItemStack potion = new SangoItemStack(pot.toItemStack(1));
						potion.setDisplayName(ChatColor.GOLD + "Health Pot");
						potion.setItemFlag(ItemFlag.HIDE_POTION_EFFECTS);

						player.getInventory().clear();
						player.getInventory().setItem(0, sword);
						player.getInventory().setItem(1, potion.getItem());
						player.getInventory().setArmorContents(
								new ItemStack[] { boots.getItem(), legs.getItem(), chest.getItem(), helm.getItem() });
						player.updateInventory();

					}
					if (stack.getType() == Material.IRON_SWORD) {
						SangoItemStack sword = new SangoItemStack(Material.IRON_SWORD, 1, Enchantment.DAMAGE_ALL, 1);
						sword.setDisplayName(ChatColor.GOLD + "Swordy sword");
						SangoItemStack chest = new SangoItemStack(Material.IRON_CHESTPLATE, 1);
						SangoItemStack legs = new SangoItemStack(Material.IRON_LEGGINGS, 1);
						SangoItemStack boots = new SangoItemStack(Material.IRON_BOOTS, 1);
						chest.setUnbreakable(true);
						legs.setUnbreakable(true);
						boots.setUnbreakable(true);
						Potion pot = new Potion(PotionType.STRENGTH, 1);
						pot.setSplash(false);
						SangoItemStack potion = new SangoItemStack(pot.toItemStack(1));
						potion.setDisplayName(ChatColor.GOLD + "Strong Pot");
						PotionMeta potionMeta = potion.getPotionMeta();
						potionMeta.addCustomEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(200, 0), true);
						potionMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
						potion.setItemMeta(potionMeta);

						player.getInventory().clear();
						player.getInventory().setItem(0, sword.getItem());
						player.getInventory().setItem(1, potion.getItem());
						player.getInventory().setArmorContents(
								new ItemStack[] { boots.getItem(), legs.getItem(), chest.getItem(), null });
						player.updateInventory();

					}
					if (stack.getType() == Material.BOW) {
						SangoItemStack sword = new SangoItemStack(Material.STONE_SWORD, 1);
						sword.setUnbreakable(true);
						sword.setDisplayName(ChatColor.GOLD + "Sniper Sword");
						SangoItemStack bow = new SangoItemStack(Material.BOW, 1, Enchantment.ARROW_DAMAGE, 1);
						bow.setUnbreakable(true);
						bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
						bow.setDisplayName(ChatColor.GOLD + "Epix Snypor");
						SangoItemStack helm = new SangoItemStack(Material.CHAINMAIL_HELMET, 1,
								Enchantment.PROTECTION_PROJECTILE, 2);
						SangoItemStack chest = new SangoItemStack(Material.CHAINMAIL_CHESTPLATE, 1,
								Enchantment.PROTECTION_PROJECTILE, 2);
						SangoItemStack legs = new SangoItemStack(Material.IRON_LEGGINGS, 1,
								Enchantment.PROTECTION_PROJECTILE, 2);
						SangoItemStack boots = new SangoItemStack(Material.IRON_BOOTS, 1,
								Enchantment.PROTECTION_PROJECTILE, 2);
						SangoItemStack arrows = new SangoItemStack(Material.ARROW, 1, ChatColor.GOLD + "Bullet");
						helm.setUnbreakable(true);
						chest.setUnbreakable(true);
						legs.setUnbreakable(true);
						boots.setUnbreakable(true);
						player.getInventory().clear();
						player.getInventory().setItem(0, sword.getItem());
						player.getInventory().setItem(1, bow.getItem());
						player.getInventory().setItem(2, arrows.getItem());
						player.setArmor(
								new ItemStack[] { boots.getItem(), legs.getItem(), chest.getItem(), helm.getItem() });
						player.updateInventory();
					}
					event.setResult(Result.DENY);
					event.setCancelled(true);
				}
			}
		}
	}
}
