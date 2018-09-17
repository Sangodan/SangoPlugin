package com.sangodan.sangoplugin.programbuttons.event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.sangodan.sangoplugin.main.SangoPlugin;
import com.sangodan.sangoplugin.utils.PublicVars;

public class ButtonBreak implements Listener {

	SangoPlugin pl;

	public ButtonBreak(SangoPlugin pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Material material = block.getType();
		if (material == Material.STONE_BUTTON) {
			if (PublicVars.hasCommand(block.getLocation())) {
				PublicVars.bCommands.remove(PublicVars.locify(block.getLocation()));
			}
		}
	}
}
