package io.github.podshot.WorldWar.commands;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.SquadAPI;
import io.github.podshot.WorldWar.api.SquadAPI_OLD;
import io.github.podshot.WorldWar.events.blocks.SquadObjectiveBlock;
import io.github.podshot.WorldWar.gui.SquadInviteGUI;
import io.github.podshot.WorldWar.handlers.PlayerHandler;
import io.github.podshot.WorldWar.squads.RemoveSquad;
import io.github.podshot.WorldWar.squads.Squad;
import io.github.podshot.WorldWar.squads.SquadObjective;
import io.github.podshot.WorldWar.squads.Squad_OLD;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class SquadCommand implements CommandExecutor {

	private WorldWar plugin = WorldWar.getInstance();

	@SuppressWarnings("unused")
	private String commandUsage = "/squad <create|disband|invite|kick|leave|objective> <Squad Name| |Player Name|Player Name| |mutipleValues>";

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args[0].equalsIgnoreCase("create")) {
				if (!SquadAPI.inSquad(player.getUniqueId())) {
					String wantedSquadName = args[1];
					if (SquadAPI.squadNameAvailable(wantedSquadName)) {
						SquadAPI.createNewSquad(wantedSquadName, player.getUniqueId(), "Blue");
						player.sendMessage(ChatColor.GREEN + "Successfully created the " + wantedSquadName + " squad");
						return true;
					} else {
						// TODO: Tell executing player that the squad name must be currently in use
					}
				} else {
					// TODO: Tell executing player that they must not be in a squad
				}
			} else if (args[0].equalsIgnoreCase("disband")) {
				if (SquadAPI.isLeader(player.getUniqueId())) {
					Squad squad = SquadAPI.getSquadThePlayerLeads(player.getUniqueId());
					SquadAPI.disbandSquad(squad.getSquadName());
					player.sendMessage(ChatColor.RED + "Disbanded " + squad.getSquadName());
					return true;
				} else {
					// TODO: Tell executing player that they must be a squad leader
				}
			} else if (args[0].equalsIgnoreCase("invite")) {
				if (SquadAPI.isLeader(player.getUniqueId())) {
					if (!SquadAPI.inSquad(Bukkit.getPlayer(args[1]).getUniqueId())) {
						Squad squad = SquadAPI.getSquadThePlayerLeads(player.getUniqueId());
						for (final Player p : Bukkit.getOnlinePlayers()) {
							if (p.getName().equals(args[1])) {
								p.openInventory(SquadInviteGUI.getSquadInviteGUI(squad.getSquadName()));
								p.setGameMode(GameMode.CREATIVE);
								PlayerHandler.SquadGUIHandler.addPlayer(p.getUniqueId(), squad);
								Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
									@Override
									public void run() {
										p.setGameMode(GameMode.SURVIVAL);
										if (PlayerHandler.SquadGUIHandler.isPlayerInList(p.getUniqueId())) {
											p.closeInventory();
											PlayerHandler.SquadGUIHandler.removePlayer(p.getUniqueId());
										}
									}
								}, 1200L);
							}
						}
					} else {
						// TODO: Tell executing player that the requested player is already in a squad
					}
				} else {
					// TODO: Tell executing player that they must be a squad leader
				}
			} else if (args[0].equalsIgnoreCase("kick")) {
				if (SquadAPI.isLeader(player.getUniqueId())) {
					Squad squad = SquadAPI.getSquadThePlayerLeads(player.getUniqueId());
					if (squad.getSquadMembers().contains(Bukkit.getPlayer(args[1]).getUniqueId())) {
						// TODO: Kick the player from the squad
					}
				}
			} else if (args[0].equalsIgnoreCase("objective")) {
				List<UUID> uuids = new ArrayList<UUID>();
				uuids.add(player.getUniqueId());
				new SquadObjective(player.getLocation().subtract(0, 1, 0), uuids, player.getWorld());
			}
		}
		return false;
	}

	@Deprecated
	public boolean onCommand_OLD(CommandSender sender, Command cmd, String arg2, String[] args) {
		boolean toReturn = false;
		boolean onSquad = false;
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args[0].equalsIgnoreCase("create")) {
				String squadName = args[1].toString();
				for (MetadataValue val : player.getMetadata("WorldWar.inSquad")) {
					if (val.getOwningPlugin().getName().equals("WorldWar")) {
						onSquad = val.asBoolean();
					}
				}
				if (!(onSquad)) {
					if (!(SquadAPI.getAllSquadNames().contains(squadName))) {
						new Squad_OLD(squadName, player.getUniqueId());
						player.setMetadata("WorldWar.Squad", new FixedMetadataValue(plugin, squadName));
						player.setMetadata("WorldWar.inSquad", new FixedMetadataValue(plugin, true));
					} else {
						player.sendMessage(ChatColor.RED + "There is already a squad with that name!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You must leave your current squad before creating one!");
				}
				toReturn = true;
			} else if (args[0].equalsIgnoreCase("disband")) {
				boolean inSquad = false;
				String squadName = null;
				for (MetadataValue val : player.getMetadata("WorldWar.inSquad")) {
					if (val.getOwningPlugin().getName().equals("WorldWar")) {
						inSquad = val.asBoolean();
					}
				}
				for (MetadataValue val : player.getMetadata("WorldWar.Squad")) {
					if (val.getOwningPlugin().getName().equals("WorldWar")) {
						squadName = val.asString();
					}
				}
				if (inSquad) {
					new RemoveSquad(squadName, player.getName());
				} else {
					player.sendMessage(ChatColor.RED + "You cannot disband a squad if you are not in one!");
				}
				toReturn = true;
			} else if (args[0].equalsIgnoreCase("invite")) {
				String squadName = null;
				for (MetadataValue val : player.getMetadata("WorldWar.Squad")) {
					if (val.getOwningPlugin().getName().equals("WorldWar")) {
						squadName = val.asString();
					}
				}
				if (SquadAPI.isLeader(player.getUniqueId())) {
					for (final Player p : Bukkit.getOnlinePlayers()) {
						if (p.getName().equalsIgnoreCase(args[1].toString())) {
							p.openInventory(SquadInviteGUI.getSquadInviteGUI(squadName));
							p.setGameMode(GameMode.CREATIVE);
							//PlayerHandler.SquadGUIHandler.addPlayer(p.getUniqueId());
							Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
								@Override
								public void run() {
									p.setGameMode(GameMode.SURVIVAL);
									if (PlayerHandler.SquadGUIHandler.isPlayerInList(p.getUniqueId())) {
										p.closeInventory();
										PlayerHandler.SquadGUIHandler.removePlayer(p.getUniqueId());
									}
								}
							}, 1200L);
						}
					}
				}
				toReturn = true;
			} else if (args[0].equalsIgnoreCase("kick")) {
				Squad squad_toKick = null;
				Squad squad_kicker = null;
				String playerToKick = args[1].toString();
				UUID playerUUID = null;
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.getName().equalsIgnoreCase(playerToKick)) {
						playerUUID = p.getUniqueId();
					}
				}
				if (SquadAPI.inSquad(player.getUniqueId()) && SquadAPI.inSquad(playerUUID)) {
					squad_kicker = SquadAPI.getSquadForPlayer(player.getUniqueId());
					squad_toKick = SquadAPI.getSquadForPlayer(playerUUID);
					if (squad_kicker != squad_toKick) {
						player.sendMessage(ChatColor.RED + "You cannot kick someone from another Squad");
						return true;
					}
				}
				if (squad_kicker.getSquadLeader().equals(player.getUniqueId())) {
					squad_kicker.removeSquadMember(playerUUID);
					player.sendMessage(ChatColor.GREEN + "Successfully kicked \""+playerToKick+"\" from your Squad");
				}

			} else if (args[0].equalsIgnoreCase("leave")) {
				toReturn = false;
			} else if (args[0].equalsIgnoreCase("waypoint")) {
				if (args[1] == null) {					
					player.getInventory().addItem(SquadObjectiveBlock.getSpecialBlock());
					toReturn = true;
				} else {
					if (args[1].equalsIgnoreCase("remove")) {
						String squadName = null;
						for (MetadataValue val : player.getMetadata("WorldWar.Squad")) {
							if (val.getOwningPlugin().getName().equals("WorldWar")) {
								squadName = val.asString();
							}
						}
						if (SquadAPI_OLD.isLeader(player, squadName)) {
							SquadAPI_OLD.removeSquadObjecive(squadName);
						}
					}
				}
			}
		}
		return toReturn;
	}
}
