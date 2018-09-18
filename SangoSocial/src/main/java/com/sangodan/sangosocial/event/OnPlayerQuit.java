package com.sangodan.sangosocial.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.sangodan.sangoapi.wrapper.SangoPlayer;
import com.sangodan.sangosocial.party.Party;

public class OnPlayerQuit implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		SangoPlayer sPlayer = SangoPlayer.get(player);
		if(Party.getParty(sPlayer) != null) {
			Party party = Party.getParty(sPlayer);
			if(party.isPartyLeader(sPlayer)) {
				party.disband(player);
			} else {
				party.leave(player);
			}
		}
	}
}
