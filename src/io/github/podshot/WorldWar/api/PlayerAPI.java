package io.github.podshot.WorldWar.api;

import io.github.podshot.WorldWar.WorldWar;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

/**
 * A class used to gather player related info related to WorldWar
 */
public class PlayerAPI {
	
	private static WorldWar plugin = WorldWar.getInstance();
	private static HashMap<UUID, Team> teamMap = new HashMap<UUID, Team>();
	
	/**
	 * Gets the Team the specified player is on from a player object
	 * @param player the player that you want to get the team from
	 * @return String The Team name that they belong to
	 */
	public static String getTeam(Player player) {
		String team = null;
		for (MetadataValue val : player.getMetadata("WorldWar.Team")) {
			if (val.getOwningPlugin().getName().equals("WorldWar")) {
				team = val.asString();
			}
		}
		return team;
	}
	
	/**
	 * Gets the Team the player is on from their UUID
	 * @param playerUUID UUID of the player you want to get the Team from
	 * @return String String The Team name that they belong to
	 */
	public static String getTeam(UUID playerUUID) {
		String team = null;
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getUniqueId().equals(playerUUID)) {
				for (MetadataValue val : p.getMetadata("WorldWar.Team")) {
					if (val.getOwningPlugin().getName().equals("WorldWar")) {
						team = val.asString();
					}
				}
			}
		}
		return team;	
	}
	
	public static void setTeam(Player player, Team team) {
		setTeam(player.getUniqueId(), team);
	}
	
	public static void setTeam(UUID uuid, Team team) {
		if (teamMap.containsKey(uuid)) {
			teamMap.remove(uuid);
		}
		teamMap.put(uuid, team);
	}
	
	/**
	 * Sets the ammo of specified gun to the specified player
	 * @param player The player that you want to set the ammo of
	 * @param gun The gun that the ammo amount belongs to
	 * @param amount The amount of ammo the player should have
	 */
	public static void setAmmoAmount(Player player, String gun, int amount) {
		if (player.hasMetadata("WorldWar.Ammo." + gun)) {
			player.removeMetadata("WorldWar.Ammo." + gun, plugin);
		}
		player.setMetadata("WorldWar.Ammo." + gun, new FixedMetadataValue(plugin, amount));
	}
	
	/**
	 * Gets the amount of ammo the player has for the specified gun
	 * @param player The player that you want to get the ammo amount for
	 * @param gun The gun that you want the ammo amount for
	 * @return int The amount of ammo the player has for that gun
	 */
	public static int getAmmoAmount(Player player, String gun) {
		int toReturn = 0;
		for (MetadataValue val : player.getMetadata("WorldWar.Ammo." + gun)) {
			if (val.getOwningPlugin().getName().equals("WorldWar")) {
				toReturn = val.asInt();
				player.sendMessage(ChatColor.RED + "Ammo: " + toReturn);
			}
		}
		return toReturn;
	}
}
