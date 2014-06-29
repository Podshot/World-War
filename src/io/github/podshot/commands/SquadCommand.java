package io.github.podshot.commands;

import java.util.UUID;

import io.github.podshot.WorldWar;
import io.github.podshot.api.SquadAPI;
import io.github.podshot.gui.SquadInviteGUI;
import io.github.podshot.internals.Internals;
import io.github.podshot.squads.RemoveSquad;
import io.github.podshot.squads.Squad;

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
	private String commandUsage = "/squad <create|disband|invite|kick|leave> <Squad Name| |Player Name|Player Name|>";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		boolean ret = false;
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
					if (!(SquadAPI.getSquads().contains(squadName))) {
						new Squad(squadName, player.getUniqueId());
						player.setMetadata("WorldWar.Squad", new FixedMetadataValue(plugin, squadName));
						player.setMetadata("WorldWar.inSquad", new FixedMetadataValue(plugin, true));
					} else {
						player.sendMessage(ChatColor.RED + "There is already a squad with that name!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You must leave your current squad before creating one!");
				}
				ret = true;
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
				ret = true;
			} else if (args[0].equalsIgnoreCase("invite")) {
				String squadName = null;
				for (MetadataValue val : player.getMetadata("WorldWar.Squad")) {
					if (val.getOwningPlugin().getName().equals("WorldWar")) {
						squadName = val.asString();
					}
				}
				if (SquadAPI.isLeader(player.getUniqueId(), squadName)) {
					for (final Player p : Bukkit.getOnlinePlayers()) {
						if (p.getName().equalsIgnoreCase(args[1].toString())) {
							p.openInventory(SquadInviteGUI.getSquadInviteGUI(squadName));
							p.setGameMode(GameMode.CREATIVE);
							Internals.addPlayer(p.getName());
							Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
								@Override
								public void run() {
									p.setGameMode(GameMode.SURVIVAL);
									if (Internals.isPlayerInList(p.getName())) {
										p.closeInventory();
										Internals.removePlayer(p.getName());
									}
								}
							}, 1200L);
						}
					}
				}
				ret = true;
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
				if (SquadAPI.isLeader(player.getUniqueId(), squadName)) {
					if (SquadAPI.isInSquad(player.getUniqueId(), squadName)) {
						SquadAPI.removeMember(squadName, playerUUID);
					}
				}

			} else if (args[0].equalsIgnoreCase("leave")) {

			}
		}
		return ret;
	}

}
