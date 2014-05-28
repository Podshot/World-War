package io.github.podshot.commands;

import io.github.podshot.WorldWar;
import io.github.podshot.squads.RemoveSquad;
import io.github.podshot.squads.Squad;

import org.bukkit.ChatColor;
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
					if (!(Squad.getSquads().contains(squadName))) {
						new Squad(squadName, player.getName());
						player.setMetadata("WorldWar.Squad", new FixedMetadataValue(plugin, squadName));
						player.setMetadata("WorldWar.inSquad", new FixedMetadataValue(plugin, true));
					} else {
						player.sendMessage(ChatColor.RED + "There is already a squad with that name!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You must leave your current squad before creating one!");
				}
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
			}
		}
		return ret;
	}

}
