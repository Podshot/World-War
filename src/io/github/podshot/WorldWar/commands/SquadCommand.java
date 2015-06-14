package io.github.podshot.WorldWar.commands;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.SquadAPI_OLD;
import io.github.podshot.WorldWar.events.blocks.SquadObjectiveBlock;
import io.github.podshot.WorldWar.gui.SquadInviteGUI;
import io.github.podshot.WorldWar.handlers.PlayerHandler;
import io.github.podshot.WorldWar.squads.RemoveSquad;
import io.github.podshot.WorldWar.squads.Squad_OLD;

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

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
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
					if (!(SquadAPI_OLD.getSquads().contains(squadName))) {
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
				if (SquadAPI_OLD.isLeader(player.getUniqueId(), squadName)) {
					for (final Player p : Bukkit.getOnlinePlayers()) {
						if (p.getName().equalsIgnoreCase(args[1].toString())) {
							p.openInventory(SquadInviteGUI.getSquadInviteGUI(squadName));
							p.setGameMode(GameMode.CREATIVE);
							PlayerHandler.SquadGUIHandler.addPlayer(p.getUniqueId());
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
				String squadName = null;
				String playerToKick = args[1].toString();
				UUID playerUUID = null;
				for (MetadataValue val : player.getMetadata("WorldWar.Squad")) {
					if (val.getOwningPlugin().getName().equals("WorldWar")) {
						squadName = val.asString();
					}
				}
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.getName().equalsIgnoreCase(playerToKick)) {
						playerUUID = p.getUniqueId();
					}
				}
				if (SquadAPI_OLD.isLeader(player.getUniqueId(), squadName)) {
					if (SquadAPI_OLD.isInSquad(player.getUniqueId(), squadName)) {
						SquadAPI_OLD.removeMember(squadName, playerUUID);
					}
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
