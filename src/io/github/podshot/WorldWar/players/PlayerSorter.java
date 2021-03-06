package io.github.podshot.WorldWar.players;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.WorldWarTeam;
import io.github.podshot.WorldWar.files.PlayerDataYAML;
import io.github.podshot.WorldWar.handlers.PlayerHandler;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerSorter {

	private static WorldWar plugin = WorldWar.getInstance();

	private static int blueAmount;
	private static int redAmount;

	public PlayerSorter() {
		blueAmount = plugin.getConfig().getInt("Teams.Blue.Amount");
		redAmount = plugin.getConfig().getInt("Teams.Red.Amount");
	}

	public static String getTeamWithMorePlayers() {
		FileConfiguration config = plugin.getConfig();
		int blueTeamAmount = config.getInt("Teams.Blue.Amount");
		int redTeamAmount = config.getInt("Teams.Red.Amount");
		if (blueTeamAmount == redTeamAmount) {
			return "EVEN";
		}
		if (blueTeamAmount > redTeamAmount) {
			return "BLUE";
		}
		if (blueTeamAmount < redTeamAmount) {
			return "RED";
		}
		return null;
	}

	public static void addPlayer(UUID uniqueId, WorldWarTeam team) {
		FileConfiguration config = plugin.getConfig();
		int playerTeam = config.getInt("Teams." + team.toString() + ".Amount");
		if (team == WorldWarTeam.BLUE) {
			blueAmount = playerTeam + 1;
			config.set("Teams.Blue.Amount", blueAmount);
		} else if (team == WorldWarTeam.RED) {
			redAmount = playerTeam + 1;
			config.set("Teams.Red.Amount", redAmount);
		}
		plugin.saveConfig();
	}

	public static void transerPlayer(Player player, WorldWarTeam team) {
		String teamWithMorePlayers = getTeamWithMorePlayers();
		switch (teamWithMorePlayers) {
		default:
			break;
		case "BLUE":
			break;
		case "RED":
			break;
		case "EVEN":
			Player player1 = null;
			Player player2 = null;
			UUID player1UUID = PlayerHandler.TeamSwitchListHandler.getPlayerFromBlueWaitList(0);
			UUID player2UUID = PlayerHandler.TeamSwitchListHandler.getPlayerFromRedWaitList(0);
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getUniqueId().equals(player1UUID)) {
					player1 = p;
				}
				if (p.getUniqueId().equals(player2UUID)) {
					player2 = p;
				}
			}
			if (player1 != null && player2 != null) {
				switchTeam(player1, player2);
			}
			break;
		}
	}

	/**
	 * 
	 * @param player1 Player that was on Blue Team (Switched to Red)
	 * @param player2 Player that was on Red Team (Switched to Blue)
	 */
	public static void switchTeam(Player player1, Player player2) {
		PlayerDataYAML.setPlayerToTeam(player1, WorldWarTeam.RED);
		PlayerDataYAML.setPlayerToTeam(player2, WorldWarTeam.BLUE);
		
		player1.sendMessage("You have been switched to the "+ChatColor.RED+"Red"+ChatColor.RESET+" team");
		player2.sendMessage("You have been switched to the "+ChatColor.BLUE+"Blue"+ChatColor.RESET+" team");
	}

}
