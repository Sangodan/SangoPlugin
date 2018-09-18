package com.sangodan.sangoplugin.main;

import org.bukkit.plugin.PluginManager;

import com.sangodan.sangoplugin.command.CommandFillInventory;
import com.sangodan.sangoplugin.command.CommandGetLife;
import com.sangodan.sangoplugin.command.CommandGetMinigame;
import com.sangodan.sangoplugin.command.CommandGetWorld;
import com.sangodan.sangoplugin.command.CommandHub;
import com.sangodan.sangoplugin.command.CommandLobby;
import com.sangodan.sangoplugin.command.CommandMoreChickens;
import com.sangodan.sangoplugin.command.CommandPvpAltitude;
import com.sangodan.sangoplugin.command.CommandSwitchCheckpointClay;
import com.sangodan.sangoplugin.command.CommandSwitchWorldIndependent;
import com.sangodan.sangoplugin.command.CommandTp;
import com.sangodan.sangoplugin.command.CommandTpSilent;
import com.sangodan.sangoplugin.event.block.BlockRightClick;
import com.sangodan.sangoplugin.event.block.OnChunkUnload;
import com.sangodan.sangoplugin.event.block.WildSignRightClick;
import com.sangodan.sangoplugin.event.player.EggThrow;
import com.sangodan.sangoplugin.event.player.OnCreatureSpawn;
import com.sangodan.sangoplugin.event.player.OnDamage;
import com.sangodan.sangoplugin.event.player.OnEntityAttackEntity;
import com.sangodan.sangoplugin.event.player.OnEntityExplode;
import com.sangodan.sangoplugin.event.player.OnInventoryChange;
import com.sangodan.sangoplugin.event.player.OnItemRightClick;
import com.sangodan.sangoplugin.event.player.OnJoin;
import com.sangodan.sangoplugin.event.player.OnLoseHunger;
import com.sangodan.sangoplugin.event.player.OnPlayerTeleport;
import com.sangodan.sangoplugin.event.player.OnRespawn;
import com.sangodan.sangoplugin.event.player.OnSurvivalLeave;
import com.sangodan.sangoplugin.event.world.OnPlayerChangeWorld;
import com.sangodan.sangoplugin.event.world.OnSpleefTick;
import com.sangodan.sangoplugin.event.world.OnWorldInit;
import com.sangodan.sangoplugin.event.world.OnWorldLoad;
import com.sangodan.sangoplugin.event.world.OnWorldUnload;
import com.sangodan.sangoplugin.games.ChatFilter;
import com.sangodan.sangoplugin.games.OnEntityInteract;
import com.sangodan.sangoplugin.games.OnProjectileLaunch;
import com.sangodan.sangoplugin.games.spleef.CreateSpleefArena;
import com.sangodan.sangoplugin.programbuttons.command.CommandEditor;
import com.sangodan.sangoplugin.programbuttons.command.CommandSetCommand;
import com.sangodan.sangoplugin.programbuttons.event.ButtonBreak;
import com.sangodan.sangoplugin.programbuttons.event.ButtonRightClick;

public class Registers {
	public static void registerCommands(SangoPlugin pluginMain) {
		pluginMain.getCommand("switchCheckpointClay").setExecutor(new CommandSwitchCheckpointClay());
		pluginMain.getCommand("switchCheckpointWorldIndependence").setExecutor(new CommandSwitchWorldIndependent());
		pluginMain.getCommand("fillInventory").setExecutor(new CommandFillInventory());
		pluginMain.getCommand("moreChickens").setExecutor(new CommandMoreChickens());
		pluginMain.getCommand("getWorld").setExecutor(new CommandGetWorld());
		pluginMain.getCommand("getlife").setExecutor(new CommandGetLife());

		pluginMain.getCommand("buttonedit").setExecutor(new CommandEditor());
		pluginMain.getCommand("setcommand").setExecutor(new CommandSetCommand(pluginMain));
		
		pluginMain.getCommand("lobby").setExecutor(new CommandLobby());
		pluginMain.getCommand("hub").setExecutor(new CommandHub());
		
		pluginMain.getCommand("tpspleef").setExecutor(new CreateSpleefArena());
		pluginMain.getCommand("tpsilent").setExecutor(new CommandTpSilent());
		pluginMain.getCommand("tp").setExecutor(new CommandTp());
		pluginMain.getCommand("getminigame").setExecutor(new CommandGetMinigame());
		pluginMain.getCommand("pvpaltitude").setExecutor(new CommandPvpAltitude());
	}

	public static void registerEvents(SangoPlugin pluginMain) {
		PluginManager pm = pluginMain.getServer().getPluginManager();
		// Block Events
		pm.registerEvents(new BlockRightClick(), pluginMain);
		pm.registerEvents(new WildSignRightClick(), pluginMain);
		pm.registerEvents(new EggThrow(), pluginMain);
		pm.registerEvents(new ButtonRightClick(), pluginMain);
		pm.registerEvents(new ButtonBreak(pluginMain), pluginMain);
		pm.registerEvents(new OnChunkUnload(), pluginMain);
		// Player Events
		pm.registerEvents(new OnPlayerTeleport(), pluginMain);
		pm.registerEvents(new OnSurvivalLeave(), pluginMain);
		pm.registerEvents(new OnDamage(), pluginMain);
		pm.registerEvents(new OnEntityAttackEntity(), pluginMain);
		pm.registerEvents(new OnPlayerChangeWorld(), pluginMain);
		pm.registerEvents(new OnJoin(), pluginMain);
		pm.registerEvents(new OnLoseHunger(), pluginMain);
		pm.registerEvents(new OnItemRightClick(), pluginMain);
		pm.registerEvents(new OnInventoryChange(), pluginMain);
		pm.registerEvents(new OnEntityInteract(), pluginMain);
		pm.registerEvents(new OnCreatureSpawn(), pluginMain);	
		pm.registerEvents(new OnProjectileLaunch(), pluginMain);
		pm.registerEvents(new OnRespawn(), pluginMain);
		pm.registerEvents(new OnEntityExplode(), pluginMain);
		// World Events
		pm.registerEvents(new OnWorldInit(), pluginMain);
		pm.registerEvents(new OnWorldLoad(), pluginMain);
		pm.registerEvents(new OnWorldUnload(), pluginMain);
		
		pm.registerEvents(new ChatFilter(), pluginMain);
		pm.registerEvents(new OnSpleefTick(), pluginMain);
	}

	public static void registerConfig(SangoPlugin pluginMain) {
		pluginMain.getConfig().options().copyDefaults(true);
		pluginMain.saveConfig();
	}

	public static void onEnable(SangoPlugin pluginMain) {
		registerEvents(pluginMain);
		registerCommands(pluginMain);
		registerConfig(pluginMain);
	}

	public static void onDisable(SangoPlugin pluginMain) {

	}
}
