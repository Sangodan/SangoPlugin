package com.sangodan.sangosocial.friend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Friends {

	public static HashMap<UUID, HashSet<UUID>> friends = new HashMap<UUID, HashSet<UUID>>();

	// Acceptor, Requestor(s)
	public static HashMap<UUID, HashSet<UUID>> fRequests = new HashMap<UUID, HashSet<UUID>>();

	public static void setFriends(HashMap<UUID, HashSet<UUID>> map) {
		friends = map;
	}

	public static HashSet<UUID> getFriends(Player player) {
		HashSet<UUID> set = friends.get(player.getUniqueId());
		if (set == null) {
			set = new HashSet<UUID>();
			friends.put(player.getUniqueId(), set);
		}
		return set;
	}

	public static HashSet<UUID> getFriendRequests(Player player) {
		HashSet<UUID> set = fRequests.get(player.getUniqueId());
		if (set == null) {
			set = new HashSet<UUID>();
			fRequests.put(player.getUniqueId(), set);
		}
		return set;

	}

	public static void addToFriends(Player player, Player player2) {
		HashSet<UUID> f = getFriends(player);
		f.add(player2.getUniqueId());
	}

	public static void addToFriends(Player player, UUID player2) {
		HashSet<UUID> f = getFriends(player);
		f.add(player2);
	}

	public static void requestFriend(Player sender, Player receiver) {
		HashSet<UUID> f = getFriendRequests(receiver);
		HashSet<UUID> f2 = getFriendRequests(sender);
		f.add(sender.getUniqueId());
		f2.add(receiver.getUniqueId());
	}

	public static boolean canBeFriends(Player player1, Player player2) {
		if (player1.getUniqueId().equals(player2.getUniqueId())) {
			player1.sendMessage(ChatColor.DARK_RED + "You can't friend yourself!");
			return false;
		} else if (Friends.getFriends(player1).contains(player2.getUniqueId())) {
			player1.sendMessage(ChatColor.DARK_RED + "You are already friends with that person!");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Accepts the friend, if there is a request.
	 * 
	 * @param acceptor Player variable that is accepting the request
	 * @param accepted Player variable that is becoming the friend
	 * @return true if the request was accepted, false if it couldn't be accepted.
	 */
	public static boolean acceptFriend(Player acceptor, Player accepted) {
		HashSet<UUID> f = getFriendRequests(acceptor);
		if (f.contains(accepted.getUniqueId())) {
			f.remove(accepted.getUniqueId());
			addToFriends(acceptor, accepted);
			addToFriends(accepted, acceptor);
			return true;
		}
		return false;
	}

	public static String listFriends(Player player) {
		HashSet<UUID> f = getFriends(player);
		@SuppressWarnings("unchecked")
		HashSet<UUID> b = (HashSet<UUID>) f.clone();
		String e = ChatColor.GOLD + "Friends:";
		Iterator<UUID> it = b.iterator();
		while (it.hasNext()) {
			UUID n = it.next();
			Player p = Bukkit.getPlayer(n);
			if (Bukkit.getOnlinePlayers().contains(p)) {
				e += ChatColor.GREEN + "\nONLINE: " + "[" + p.getWorld().getName() + "] " + p.getDisplayName();
				it.remove();
			}
		}
		Iterator<UUID> it2 = b.iterator();
		while (it2.hasNext()) {
			UUID n = it2.next();
			OfflinePlayer p = Bukkit.getOfflinePlayer(n);
			e += ChatColor.DARK_RED + "\nOFFLINE: " + p.getName();
			it2.remove();
		}

		return e;
	}
}
