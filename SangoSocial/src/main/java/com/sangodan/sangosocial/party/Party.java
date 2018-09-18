package com.sangodan.sangosocial.party;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.sangodan.sangoapi.wrapper.SangoPlayer;

public class Party {

	private static HashSet<Party> parties = new HashSet<Party>();
	private static HashMap<UUID, UUID> partyRequests = new HashMap<UUID, UUID>();

	private UUID partyLeader;
	private ArrayList<UUID> partyMembers = new ArrayList<UUID>();
	private ArrayList<UUID> allMembers = new ArrayList<UUID>();

	private Server server;

	public static Party getParty(SangoPlayer player) {
		Party p = null;
		for(Party party : parties) {
			if(party.getAllMembers().contains(player.getUUID())) {
				p = party;
			}
		}
		return p;
	}
	
	public Party(Player player) {
		partyLeader = player.getUniqueId();
		updatePlayers();
		server = Bukkit.getServer();
		parties.add(this);
	}

	public Party(SangoPlayer player) {
		this(player.getPlayer());
	}

	public boolean isPartyLeader(SangoPlayer player) {
		return this.partyLeader.equals(player.getUUID());
	}
	public static HashSet<Party> getParties() {
		return parties;
	}

	public void warp(Player p) {
		if(p.getUniqueId().equals(partyLeader)) {
			for(UUID id : partyMembers) {
				SangoPlayer player = SangoPlayer.get(Bukkit.getPlayer(id));
				player.teleport(p.getLocation(), true);
			}
		} else {
			p.sendMessage(ChatColor.RED + "Only the party leader can do that.");
		}
	}
	
	public static HashMap<UUID, UUID> getRequests() {
		return partyRequests;
	}

	public UUID getLeader() {
		updatePlayers();
		return this.partyLeader;
	}

	public Player getLeaderPlayer() {
		Player p = server.getPlayer(partyLeader);
		return p;
	}

	public ArrayList<UUID> getPartyMembers() {
		updatePlayers();
		return partyMembers;
	}

	public ArrayList<Player> getPartyMemberPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (UUID id : getPartyMembers()) {
			players.add(server.getPlayer(id));
		}
		return players;
	}

	public ArrayList<UUID> getAllMembers() {
		updatePlayers();
		return allMembers;
	}

	public ArrayList<Player> getAllMemberPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (UUID id : getAllMembers()) {
			players.add(server.getPlayer(id));
		}
		return players;
	}

	public boolean isLeader(Player p) {
		return p.getUniqueId().equals(partyLeader);
	}

	public void updatePlayers() {
		allMembers.clear();
		allMembers.addAll(partyMembers);
		allMembers.add(partyLeader);
	}

	public boolean inviteToParty(Player player) {

		if (allMembers.contains(player.getUniqueId())) {
			return false;
		}
		player.sendMessage(ChatColor.DARK_GREEN + "Would you like to join "
				+ server.getPlayer(partyLeader).getDisplayName() + ChatColor.DARK_GREEN + "'s party?");
		player.sendMessage(ChatColor.GREEN + "/p join " + ChatColor.DARK_GREEN + "to join the party.");
		partyRequests.put(player.getUniqueId(), partyLeader);
		updatePlayers();
		return true;

	}

	public boolean isPlayerInParty(Player player) {
		boolean playerInParty = allMembers.contains(player.getUniqueId());
		return playerInParty;
	}

	public void addPlayerToParty(Player player) {
		partyMembers.add(player.getUniqueId());
		updatePlayers();
	}

	public void promote(Player player, Player promotee) {
		if (player.getUniqueId().equals(partyLeader)) {
			if (partyMembers.contains(promotee.getUniqueId())) {
				partyMembers.add(partyLeader);
				partyMembers.remove(promotee.getUniqueId());
				partyLeader = promotee.getUniqueId();
				this.sendPartyMessage(
						server.getPlayer(partyLeader).getDisplayName() + ChatColor.GOLD + " is now the party leader!");
				updatePlayers();
			} else if (player.getUniqueId().equals(partyLeader)) {
				player.sendMessage(ChatColor.LIGHT_PURPLE + "You can't promote yourself!");
			} else {
				player.sendMessage(ChatColor.LIGHT_PURPLE + "That player is not in your party.");
			}
		} else {
			player.sendMessage(ChatColor.DARK_RED + "Only the leader can do that.");
		}
	}

	public boolean leave(Player player) {
		if (player.getUniqueId().equals(partyLeader)) {
			player.sendMessage(
					ChatColor.DARK_PURPLE + "You cannot leave as the leader: try /p disband to abolish the party.");
			return false;
		} else {
			partyMembers.remove(player.getUniqueId());
			updatePlayers();
			return true;
		}
	}

	public void disband(Player player) {
		if (player.getUniqueId().equals(partyLeader)) {
			Iterator<UUID> iterator8 = partyMembers.iterator();
			while (iterator8.hasNext()) {
				UUID s = iterator8.next();
				Player p = server.getPlayer(s);
				p.sendMessage(ChatColor.DARK_RED + "The party was disbanded!");
				iterator8.remove();
			}
			player.sendMessage(ChatColor.GREEN + "You disbanded the party.");
			partyLeader = null;
			updatePlayers();
			parties.remove(this);

		} else {
			player.sendMessage(ChatColor.DARK_RED + "You can only do this if you are the party leader.");
		}
	}

	public void sendPartyMessage(String string) {
		for (UUID s : allMembers) {
			Player p = server.getPlayer(s);
			p.sendMessage(ChatColor.GREEN + "[PARTY] " + string);
		}
	}

	public void teleport(Player sender, boolean ifInWorld) {
		if (isLeader(sender)) {
			for (Player p : getPartyMemberPlayers()) {
				if (sender.getWorld().getName().equals(p.getWorld().getName())) {
					continue;
				}
				SangoPlayer sp = SangoPlayer.get(p);
				sp.teleport(sender.getWorld().getSpawnLocation(), false);
				sp.sendMessage(ChatColor.GOLD + "The party leader teleported you to their world.");
			}
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "You can only do this if you are the party leader.");
		}
	}

	public void list(Player player) {
		player.sendMessage(
				ChatColor.AQUA + "Party Leader: " + ChatColor.WHITE + server.getPlayer(partyLeader).getDisplayName());
		String partymembers = "";
		for (UUID s : partyMembers) {
			if (partyMembers.size() == 1)
				partymembers += server.getPlayer(s).getDisplayName();
			else {
				if (!(partyMembers.indexOf(s) == (partyMembers.size() - 1)))
					partymembers += server.getPlayer(s).getDisplayName() + ChatColor.DARK_AQUA + ", ";
				else
					partymembers += server.getPlayer(s).getDisplayName();
			}
		}
		player.sendMessage(ChatColor.DARK_AQUA + "Party Members: " + partymembers);
	}
}
