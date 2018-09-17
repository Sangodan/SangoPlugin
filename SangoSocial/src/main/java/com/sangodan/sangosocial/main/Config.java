package com.sangodan.sangosocial.main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.sangodan.sangosocial.friend.Friends;

public class Config {

	static SangoSocial plugin;
	static File dataFolder;

	static File friendsFile;
	static FileConfiguration friendsConfig;

	public static void onEnable(SangoSocial pl) {
		plugin = pl;
		dataFolder = pl.getDataFolder();
		friendsFile = new File(dataFolder, "friends.yml");
		friendsConfig = YamlConfiguration.loadConfiguration(friendsFile);

		createAllFiles();
		
		
		plugin.getLogger().info("Loading friends...");
		List<String> playerList = getFriendsConfig().getStringList("friends");
		HashMap<UUID, HashSet<UUID>> friends = new HashMap<UUID, HashSet<UUID>>();
		for (String player : playerList) {
			String[] words = player.split(":");
			UUID playerid = UUID.fromString(words[0]);
			String[] friendsidstr = words[1].split("/");
			HashSet<UUID> set = new HashSet<UUID>();
			for (String f : friendsidstr) {
				UUID id = UUID.fromString(f);
				set.add(id);
			}
			friends.put(playerid, set);
		}
		Friends.setFriends(friends);
		plugin.getLogger().info("Loaded friends.");
	}

	public static void onDisable() {
		// Friends
		plugin.getLogger().info("Saving friends...");
		HashMap<UUID, HashSet<UUID>> friends = Friends.friends;
		List<String> friendsList = getFriendsConfig().getStringList("friends");
		friendsList.clear();
		for (UUID uuid : friends.keySet()) {
			String id = uuid.toString() + ":";
			HashSet<UUID> playerFriendsList = friends.get(uuid);
			int count = 0;
			int length = playerFriendsList.size();
			for (UUID uuid2 : playerFriendsList) {
				count++;
				String friendID = uuid2.toString();
				if (count < length) {
					id += friendID + "/";
				} else {
					id += friendID;
				}
			}
			friendsList.add(id);
		}
		getFriendsConfig().set("friends", friendsList);
		plugin.getLogger().info("Saved friends.");
		try {
			getFriendsConfig().save(friendsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File getFriendsFile() {
		return friendsFile;
	}

	public static FileConfiguration getFriendsConfig() {
		return friendsConfig;
	}

	public static void createAllFiles() {
		if (!friendsFile.exists()) {
			plugin.getLogger().info("Could not find friends.yml, creating it for you...");
			plugin.saveResource("friends.yml", true);
			plugin.getLogger().info("Created friends.yml.");

		}
	}
}
