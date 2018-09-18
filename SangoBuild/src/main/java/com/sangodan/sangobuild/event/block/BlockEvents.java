package com.sangodan.sangobuild.event.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.sangodan.sangoapi.classes.SangoPlayer;
import com.sangodan.sangoapi.classes.SangoWorld;
import com.sangodan.sangoapi.enums.Life;
import com.sangodan.sangoapi.enums.Minigame;

@SuppressWarnings({ "unused", "deprecation" })
public class BlockEvents implements Listener {

	private boolean metaPermCheck(final SangoPlayer user, final String action, final Block block) {
		if (block == null) {
			return false;
		}
		return metaPermCheck(user, action, block.getTypeId(), block.getData());
	}

	private boolean metaPermCheck(final SangoPlayer user, final String action, final Minigame minigame,
			final Block block) {
		return metaPermCheck(user, action, minigame, block.getTypeId(), block.getData());
	}

	private boolean metaPermCheck(final SangoPlayer user, final String action, final int blockId) {
		final String blockPerm = "SangoBuild." + action + "." + blockId;
		return user.hasPermission(blockPerm);
	}

	private boolean metaPermCheck(final SangoPlayer user, final String action, final int blockId, final short data) {
		final String blockPerm = "SangoBuild." + action + "." + blockId;
		final String dataPerm = blockPerm + ":" + data;

		if (user.isPermissionSet(dataPerm))
			return user.hasPermission(dataPerm);
		return user.hasPermission(blockPerm);
	}

	private boolean metaPermCheck(final SangoPlayer user, final String action, final Minigame minigame,
			final int blockId) {
		final String blockPerm = "SangoBuild." + action + "." + minigame.toString() + "." + blockId;
		return user.hasPermission(blockPerm);
	}

	private boolean metaPermCheck(final SangoPlayer user, final String action, final Minigame minigame,
			final int blockId, final short data) {
		final String blockPerm = "SangoBuild." + action + "." + minigame.toString() + "." + blockId;
		final String dataPerm = blockPerm + ":" + data;
		if (user.isPermissionSet(dataPerm))
			return user.hasPermission(dataPerm);
		return user.hasPermission(blockPerm);
	}

	 @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(final BlockBreakEvent event) {
		final Block block = event.getBlock();
		final int typeId = block.getTypeId();
		final Material type = block.getType();
		SangoPlayer player = SangoPlayer.get(event.getPlayer());
		SangoWorld world = SangoWorld.get(block.getWorld());
		if (world.isMinigame()) {
			if (world.getLife() != Life.STARTED || !metaPermCheck(player, "break", world.getMinigame(), block)) {
				event.setCancelled(true);
			}
			return;
		}
		if (!metaPermCheck(player, "break", block) && !player.canBuild()) {
			event.setCancelled(true);
		}
	}

	 @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		final Block block = event.getBlock();
		final int typeId = block.getTypeId();
		final Material type = block.getType();
		SangoPlayer player = SangoPlayer.get(event.getPlayer());
		SangoWorld world = SangoWorld.get(block.getWorld());
		if (world.isMinigame()) {
			if (world.getLife() != Life.STARTED || !metaPermCheck(player, "build", world.getMinigame(), block)) {
				event.setCancelled(true);
			}
			return;
		}
		if (!metaPermCheck(player, "build", block) && !player.canBuild()) {
			event.setCancelled(true);
			

		}

	}

	 @EventHandler(priority = EventPriority.LOW)
	public void onBlockClicked(PlayerInteractEvent event) {
		SangoPlayer player = SangoPlayer.get(event.getPlayer());
		ItemStack item = event.getItem();
		if (event.hasItem() && !metaPermCheck(player, "interact", item.getTypeId(), item.getDurability())) {
			event.setCancelled(true);
			return;
		}
		if (event.hasBlock() && !metaPermCheck(player, "interact", event.getClickedBlock())) {
			event.setCancelled(true);
		}
	}
}
