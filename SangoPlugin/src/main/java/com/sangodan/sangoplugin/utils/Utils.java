package com.sangodan.sangoplugin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import com.sangodan.sangoapi.event.WorldDeleteEvent;
import com.sangodan.sangoapi.wrapper.SangoWorld;

public class Utils {

	public static boolean isEmptyOrNull(String str) {
		if (str == null || str.equals("")) {
			return true;
		}
		return false;
	}

	private static void copyFileStructure(File source, File target) {
		try {
			ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
			if (!ignore.contains(source.getName())) {
				if (source.isDirectory()) {
					if (!target.exists())
						if (!target.mkdirs())
							throw new IOException("Couldn't create world directory!");
					String files[] = source.list();
					for (String file : files) {
						File srcFile = new File(source, file);
						File destFile = new File(target, file);
						copyFileStructure(srcFile, destFile);
					}
				} else {
					InputStream in = new FileInputStream(source);
					OutputStream out = new FileOutputStream(target);
					byte[] buffer = new byte[1024];
					int length;
					while ((length = in.read(buffer)) > 0)
						out.write(buffer, 0, length);
					in.close();
					out.close();
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void copyWorld(World origin, String newWorldName) {
		copyFileStructure(origin.getWorldFolder(), new File(Bukkit.getWorldContainer(), newWorldName));
		new WorldCreator(newWorldName).createWorld();
		Bukkit.getLogger().info("Copied world " + origin.getName() + " to world " + newWorldName + ".");
	}

	public static boolean deleteWorld(File path) {
		if (path.exists()) {
			File files[] = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteWorld(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}


	public static void deleteWorld(World world) {
		SangoWorld sWorld = SangoWorld.get(world);
		for (Player p : world.getPlayers()) {
			p.kickPlayer("Kicked you from your spleef game - Server Restart.");
		}
		WorldDeleteEvent delEvent = new WorldDeleteEvent(sWorld.getWorld());
		Bukkit.getPluginManager().callEvent(delEvent);
		boolean couldunload = Bukkit.unloadWorld(world, false);
		if(!couldunload) {
			Bukkit.getLogger().log(Level.WARNING, "Could not unload world " +world.getName());
		} else {
			deleteWorld(world.getWorldFolder());
		}

	}
}
