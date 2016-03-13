package io.github.podshot.WorldWar.api;

import io.github.podshot.WorldWar.files.PlayerDataYAML;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

/**
 * A class used to gather player related info related to WorldWar
 */
public class PlayerAPI {
	
	private static HashMap<UUID, WorldWarTeam> teamMap = new HashMap<UUID, WorldWarTeam>();
	private static HashMap<UUID, HashMap<GunType, Integer>> ammoMap = new HashMap<UUID, HashMap<GunType, Integer>>();
	
	/**
	 * Gets the Team the specified player is on from a player object
	 * @param player the player that you want to get the team from
	 * @return String The Team name that they belong to
	 */
	public static WorldWarTeam getTeam(Player player) {
		return getTeam(player.getUniqueId());
	}
	
	/**
	 * Gets the Team the player is on from their UUID
	 * @param playerUUID UUID of the player you want to get the Team from
	 * @return String String The Team name that they belong to
	 */
	public static WorldWarTeam getTeam(UUID playerUUID) {
		WorldWarTeam team = teamMap.get(playerUUID);
		return team;	
	}
	
	public static void setTeam(Player player, WorldWarTeam team) {
		setTeam(player.getUniqueId(), team);
	}
	
	public static void setTeam(UUID uuid, WorldWarTeam team) {
		teamMap.put(uuid, team);
		PlayerDataYAML.setPlayerToTeam(uuid, team);
	}
	
	/**
	 * Sets the ammo of specified gun to the specified player
	 * @param player The player that you want to set the ammo of
	 * @param gun The gun that the ammo amount belongs to
	 * @param amount The amount of ammo the player should have
	 */
	public static void setAmmoAmount(Player player, GunType gun, int amount) {
		setAmmoAmount(player.getUniqueId(), gun, amount);
	}
	
	public static void setAmmoAmount(UUID uuid, GunType gun, int amount) {
		if (!ammoMap.containsKey(uuid)) {
			ammoMap.put(uuid, new HashMap<GunType, Integer>());
		}
		HashMap<GunType, Integer> map = ammoMap.get(uuid);
		map.put(gun, amount);
		ammoMap.put(uuid, map);
	}
	
	/**
	 * Gets the amount of ammo the player has for the specified gun
	 * @param player The player that you want to get the ammo amount for
	 * @param gun The gun that you want the ammo amount for
	 * @return int The amount of ammo the player has for that gun
	 */	
	public static int getAmmoAmount(Player player, GunType gun) {
		return getAmmoAmount(player.getUniqueId(), gun);
	}
	
	public static int getAmmoAmount(UUID uuid, GunType gun) {
		if (ammoMap.containsKey(uuid)) {
			HashMap<GunType, Integer> map = ammoMap.get(uuid);
			if (map.containsKey(gun)) {
				return map.get(gun);
			} else {
				return 0;
			}
		}
		return 0;
	}
}
