package com.sangodan.sangosocial.command;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sangodan.sangoapi.wrapper.SangoPlayer;
import com.sangodan.sangosocial.party.Party;

public class CommandParty implements CommandExecutor {

	public void sendNIPM(Player player) {
		player.sendMessage(ChatColor.RED + "You aren't in a party!");
	}

	public void sendNIPM(SangoPlayer player) {
		sendNIPM(player.getPlayer());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("party") || command.getAliases().contains(label.toLowerCase())) {
			if (!(sender instanceof Player)) {
				return false;
			}
			SangoPlayer player = SangoPlayer.get((Player) sender);

			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("create")) {
					if (Party.getParty(player) != null) {
						player.sendMessage(ChatColor.DARK_RED + "You must leave your current party first.");
						return true;
					}

					@SuppressWarnings("unused")
					Party party = new Party(player.getPlayer());
					player.sendMessage(ChatColor.GREEN + "Successfully created " + player.getDisplayName()
							+ ChatColor.GREEN + "'s party.");
					return true;

				}
				if (args[0].equalsIgnoreCase("join")) {
					UUID uuid = Party.getRequests().get(player.getUUID());
					if (uuid == null) {
						player.sendMessage(ChatColor.DARK_RED + "You aren't invited to any party.");
						return true;
					}
					SangoPlayer invitor = SangoPlayer.get(Bukkit.getPlayer(uuid));

					if (Party.getParty(player) != null) {
						player.sendMessage(ChatColor.DARK_RED + "You must first leave your current party.");
						return true;
					}
					Party party = Party.getParty(invitor);
					if (party == null) {
						player.sendMessage(ChatColor.RED + "The last party you were invited to no longer exists.");
						return true;
					}
					party.addPlayerToParty(player.getPlayer());
					player.sendMessage(
							ChatColor.GREEN + "You joined " + invitor.getDisplayName() + ChatColor.GREEN + "'s party!");
					party.sendPartyMessage(player.getDisplayName() + ChatColor.AQUA + " Joined the party!");
					return true;
				}
				if (args[0].equalsIgnoreCase("list")) {
					if (Party.getParty(player) != null) {
						Party party = Party.getParty(player);
						party.list(player.getPlayer());
						return true;
					}
					sendNIPM(player);
					return true;
				}
				if (args[0].equalsIgnoreCase("leave")) {
					if (Party.getParty(player) != null) {
						Party party = Party.getParty(player);
						boolean f = party.leave(player.getPlayer());
						if (f) {
							player.sendMessage(ChatColor.GREEN + "You left the party.");
							party.sendPartyMessage(player.getDisplayName() + ChatColor.DARK_AQUA + " left the party!");
						}
						return true;
					}
					sendNIPM(player);
					return true;
				}

				if (args[0].equalsIgnoreCase("disband")) {
					if (Party.getParty(player) != null) {
						Party party = Party.getParty(player);
						party.disband(player.getPlayer());
						return true;
					}
					sendNIPM(player);
					return true;
				}

				if (args[0].equalsIgnoreCase("warp")) {
					if (Party.getParty(player) != null) {
						Party party = Party.getParty(player);
						party.warp(player.getPlayer());
						return true;
					}
					sendNIPM(player);
					return true;
				}
				if (args[0].equalsIgnoreCase("help")) {
					String message = ChatColor.GOLD + "All /party commands: \n" + ChatColor.AQUA
							+ "/p create: Creates an empty party.\n" + ChatColor.AQUA
							+ "/p list: lists everybody in your party.\n/p leave: Leave your current party.\n/p disband: Destroys the party\n/p warp: "
							+ "warps everybody in your party to your lobby.\n/p promote [PLAYER]: Promotes that player to the party leader.\n/p invite [PLAYER]"
							+ ": Invites that player to join your party.";
					player.sendMessage(message);
					return true;
				}
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.getName().equals(args[0])) {
						Player targetPlayer = p;
						Party party = Party.getParty(player);
						if (party == null) {
							party = new Party(player.getPlayer());
						}
						boolean f = party.inviteToParty(targetPlayer);
						if (f) {
							player.sendMessage(ChatColor.DARK_GREEN + "You invited " + targetPlayer.getDisplayName()
									+ ChatColor.DARK_GREEN + " to the party.");
							return true;
						}
						player.sendMessage(ChatColor.DARK_RED + "That player is already in your party!");
						return true;
					}
				}
				return false;
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("invite")) {
					Player targetPlayer = Bukkit.getPlayer(args[1]);
					if (targetPlayer == null) {
						return false;
					}
					Party party = Party.getParty(player);
					if (party == null) {
						party = new Party(player.getPlayer());
					}
					boolean f = party.inviteToParty(targetPlayer);
					if (f) {
						player.sendMessage(ChatColor.DARK_GREEN + "You invited " + targetPlayer.getDisplayName()
								+ ChatColor.DARK_GREEN + " to the party.");
						return true;
					}
					player.sendMessage(ChatColor.DARK_RED + "That player is already in your party!");
					return true;
				}
				if (args[0].equalsIgnoreCase("promote")) {
					Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
					if (targetPlayer == null) {
						return false;
					}
					Party party = Party.getParty(player);
					if (party == null) {
						sendNIPM(player);
						return true;
					}
					party.promote(player.getPlayer(), targetPlayer);
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}

}
