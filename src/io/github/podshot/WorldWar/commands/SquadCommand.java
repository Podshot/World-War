package io.github.podshot.WorldWar.commands;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.SquadAPI;
import io.github.podshot.WorldWar.gui.SquadInviteGUI;
import io.github.podshot.WorldWar.handlers.PlayerHandler;
import io.github.podshot.WorldWar.squads.Squad;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SquadCommand implements CommandExecutor {

	private WorldWar plugin = WorldWar.getInstance();

	@SuppressWarnings("unused")
	private String commandUsage = "/squad <create|disband|invite|kick|leave|objective> <Squad Name| |Player Name|Player Name| |mutipleValues>";

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
				if (SquadAPI.isLeader(player.getUniqueId())) {
					Squad squad = SquadAPI.getSquadThePlayerLeads(player.getUniqueId());
					if (args[1].equalsIgnoreCase("create")) {
						squad.addObjective(player.getLocation());
					} else if (args[1].equalsIgnoreCase("modify")) {
						// TODO: Remove the existing objective and give the Squad Leader another objective block
					} else if (args[1].equalsIgnoreCase("remove")) {
						// TODO: Remove the current objective
					}
				} else {
					// TODO: Tell the player that only Squad Leaders can modify objectives
				}
			}
		} else {
			sender.sendMessage("You must be a player to use the \\squad command!");
			return true;
		}
		return false;
	}
}
