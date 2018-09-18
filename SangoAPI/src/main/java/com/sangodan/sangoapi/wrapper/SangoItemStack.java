package com.sangodan.sangoapi.wrapper;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

public class SangoItemStack {

	private final ItemStack stack;
	
	public SangoItemStack(ItemStack stack) {
		this.stack = stack;
	}
	
	public SangoItemStack(Material material, int amount) {
		stack = new ItemStack(material, amount);
	}
	
	public SangoItemStack(Material material, int amount, Enchantment ench, int level) {
		ItemStack s = new ItemStack(material, amount);
		s.addEnchantment(ench, level);
		stack = s;
	}
	
	public SangoItemStack(Material material, int amount, String name) {
		ItemStack s = new ItemStack(material, amount);
		stack = s;
		setDisplayName(name);
	}
	
	public ItemStack getItem() {
		return this.stack;
	}
	
	public ItemMeta getItemMeta() {
		return stack.getItemMeta();
	}
	
	public void setItemMeta(ItemMeta itemMeta) {
		stack.setItemMeta(itemMeta);
	}
	
	public Material getType() {
		return stack.getType();
	}
	
	public void setItemFlag(ItemFlag flag) {
		ItemMeta meta = stack.getItemMeta();
		meta.addItemFlags(flag);
	}
	
	public PotionMeta getPotionMeta() {
		ItemMeta meta = stack.getItemMeta();
		PotionMeta pm = (PotionMeta) meta;
		return pm;
	}

	public void setDisplayName(String string) {
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(string);
		stack.setItemMeta(meta);
	}
	
	public void setUnbreakable(boolean isUnbreakable) {
		ItemMeta meta = stack.getItemMeta();
		meta.spigot().setUnbreakable(isUnbreakable);
		stack.setItemMeta(meta);
	}
	
	public void addEnchantment(Enchantment ench, int level) {
		stack.addEnchantment(ench, level);
	}
	
	public void addUnsafeEnchantment(Enchantment ench, int level) {
		stack.addUnsafeEnchantment(ench, level);
	}
}
