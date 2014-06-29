package io.github.podshot.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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
	
	/**
	 * Adds a member to a squad
	 * @param squadName The name of the squad that the player should be added to
	 * @param memberUUID The UUID of the player that should be added to the squad
	 */
	public static void addMember(String squadName, UUID memberUUID) {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		List<String> existingMembers = squadConfig.getStringList("Squads." + squadName + ".Members");
		if (existingMembers.size() <= squadConfig.getInt("Squads.Global.MemberLimit")) {
			existingMembers.add(memberUUID.toString());
			List<String> squadMembers = squadConfig.getStringList("Squads.Global.PeopleInSquads");
			squadMembers.add(memberUUID.toString());
			squadConfig.set("Squads.Global.PeopleInSquads", squadMembers);
		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getName().equals(squadConfig.getString("Squads." + squadName + ".Leader"))) {
					p.sendMessage(ChatColor.RED + "Your squad is at the maximum member limit!");
				}
			}
		}
		squadConfig.set("Squads." + squadName + ".Members", existingMembers);
		ExtraConfigHandler.saveConfig(plugin.fileSep + "Squads");
	}
	
	/**
	 * Removes a member from a squad
	 * @param squadName The name of the squad that the player should removed from
	 * @param memberUUID The UUID of the player that should be removed from the squad
	 */
	public static void removeMember(String squadName, UUID memberUUID) {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.getDataFolder() + plugin.fileSep + "Squads");
		List<String> existingMembers = squadConfig.getStringList("Squads." + squadName + ".Members");
		existingMembers.remove(memberUUID.toString());
		squadConfig.set("Squads." + squadName + ".Members", existingMembers);
		List<String> squadMembers = squadConfig.getStringList("Squads.Global.PeopleInSquads");
		squadMembers.remove(memberUUID.toString());
		squadConfig.set("Squads.Global.PeopleInSquads", squadMembers);
		ExtraConfigHandler.saveConfig(plugin.fileSep + "Squads");		
	}
	
	/**
	 * Gets the squad that the player belongs to
	 * @param uuid The UUID of the player that we want to get the squad for
	 * @return The squad's name, if they are not in a squad, this method will return "Not in Squad"
	 */
	public static String getSquadForPlayer(UUID uuid) {
		String ret = null;
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		List<String> members = squadConfig.getStringList("Squads.Global.PeopleInSquads");
		if (members.contains(uuid.toString())) {
			for (String squad : squadConfig.getStringList("Squads.Global.SquadList")) {
				if (squadConfig.getStringList("Squads." + squad + ".Members").contains(uuid.toString())) {
					ret = squad;
				}
			}
		} else {
			ret = "Not in Squad";
		}
		return ret;
	}
	
	/**
	 * Gets the leader for the specified squad
	 * @param squad The name of the squad that you want to get the leader of
	 * @return The UUID of the leader of that squad
	 */
	public static UUID getLeader(String squad) {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		UUID uuid = UUID.fromString(squadConfig.getString("Squads." + squad + ".Leader"));
		return uuid;
	}

}
