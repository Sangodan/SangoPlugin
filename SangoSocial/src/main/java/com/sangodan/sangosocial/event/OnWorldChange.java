package com.sangodan.sangosocial.event;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.sangodan.sangoapi.wrapper.SangoPlayer;
import com.sangodan.sangosocial.party.Party;


public class OnWorldChange implements Listener {

	@EventHandler
	public void onChange(PlayerChangedWorldEvent event) {
		SangoPlayer player = SangoPlayer.get(event.getPlayer());
		@SuppressWarnings("unused")
		World worldLeft = event.getFrom();
		World worldJoined = player.getPlayer().getWorld();
		if (worldJoined.getName().contains("Spleef_")) {
			if (Party.getParty(player) != null) {
				Party party = Party.getParty(player);
				if (party.isPartyLeader(player)) {
					List<UUID> players = party.getPartyMembers();
					for (UUID id : players) {
						Player p = Bukkit.getPlayer(id);
						SangoPlayer sp = SangoPlayer.get(p);
						sp.teleport(player.getPlayer().getWorld().getSpawnLocation(), true);
					}
				}
			}

		}
	}
}
