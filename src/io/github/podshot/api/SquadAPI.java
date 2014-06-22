package io.github.podshot.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

import io.github.podshot.WorldWar;

/**
 * A class used to get info about squad related data
 */
public class SquadAPI {
	
	private static WorldWar plugin = WorldWar.getInstance();
	
	/**
	 * Gets the list of all squads known to WorldWar
	 * @return List<String> of all squad names 
	 */
	public static List<String> getSquads() {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		List<String> squads = squadConfig.getStringList("Squads.Global.SquadList");
		ExtraConfigHandler.saveConfig(plugin.fileSep + "Squads");
		return squads;
	}
	
	/**
	 * Checks if the specified player is the Leader of a specified squad
	 * @param uuid UUID of the player who might be the leader of the squad
	 * @param squadName The name of the squad that the player might be the leader of
	 * @return true if the player is the leader, false if otherwise
	 */
	public static boolean isLeader(UUID uuid, String squadName) {
		boolean ret = false;
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		UUID founder = UUID.fromString(squadConfig.getString("Squads." + squadName + ".Leader"));
		if (founder.equals(uuid)) {
			ret = true;
		}
		return ret;
	}
	
	/**
	 * Checks if the specified player is in the specified squad
	 * @param playerUUID UUID for the player that you want to check if they are in the specified squad
	 * @param squadName Name of the squad that may contain the Player
	 * @return true if they are in the specified squad, false otherwise
	 */
	public static boolean isInSquad(UUID playerUUID, String squadName) {
		boolean ret = false;
		List<UUID> squadUUIDs = new ArrayList<UUID>();
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		List<String> members = squadConfig.getStringList("Squads." + squadName + ".Members");
		for (String member : members) {
			UUID uuid = UUID.fromString(member);
			squadUUIDs.add(uuid);
		}
		if (squadUUIDs.contains(playerUUID)) {
			ret = true;
		}
		return ret;
	}

}
