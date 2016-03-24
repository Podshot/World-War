package io.github.podshot.WorldWar.files;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.GunType;
import io.github.podshot.WorldWar.api.PlayerAPI;
import io.github.podshot.WorldWar.api.Refactor;
import io.github.podshot.WorldWar.api.WorldWarTeam;

import java.io.File;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

/**
 * A class used to store and get Player Data from a "PlayerData.yml" file
 */
@Refactor
public class PlayerDataYAML {
	
	private static WorldWar plugin = WorldWar.getInstance();
	private static FileConfiguration config;
	private static boolean loaded;

	/**
	 * Constructor that creates a file named "PlayerData.yml" uses Jogy34's ExtraConfigHandler
	 */	
	public static void loadYAML() {
		File playerDataYaml = new File(plugin.getDataFolder() + plugin.fileSep + "PlayerData");
		if (!playerDataYaml.exists()) {
			ExtraConfigHandler.getConfig("PlayerData");
			ExtraConfigHandler.saveConfig("PlayerData");
		}
		config = ExtraConfigHandler.getConfig("PlayerData");
		loaded = true;
	}
	
	public static boolean isLoaded() {
		return loaded;
	}
	
	public static void saveYAML() {
		ExtraConfigHandler.saveConfig("PlayerData");
	}
	
	public static void reloadYAML() {
		config = ExtraConfigHandler.reloadConfig("PlayerData");
	}
	
	/**
	 * Sets the player's ammo amount to the PlayerData file
	 * @param player The player to save the ammo for
	 */
	public static void setPlayerAmmoToFile(UUID player) {
		config.set("Players." + player.toString() + ".Ammo.Rifle", PlayerAPI.getAmmoAmount(player, GunType.RIFLE));
		config.set("Players." + player.toString() + ".Ammo.Rocket-Launcher", PlayerAPI.getAmmoAmount(player, GunType.ROCKET_LAUNCHER));
		config.set("Players." + player.toString() + ".Ammo.Shotgun", PlayerAPI.getAmmoAmount(player, GunType.SHOTGUN));
		config.set("Players." + player.toString() + ".Ammo.Pistol", PlayerAPI.getAmmoAmount(player, GunType.PISTOL));
	}
	
	/**
	 * Gets the ammo amount the player has for a certain gun
	 * @param player The player that we want the ammo amount for
	 * @param gun The name of the gun we want the ammo for
	 * @return The amount of ammo the player has for that gun
	 */
	public static int getPlayerAmmoFromFile(Player player, String gun) {
		int ammo = 0;
		
		ammo = config.getInt("Players." + player.getUniqueId().toString() + ".Ammo." + gun);
		return ammo;
	}
	
	/**
	 * Sets the player's team to the PlayerData file
	 * @param player The player that we want to set the team of
	 * @param team The name of the team
	 */	
	public static void setPlayerToTeam(Player player, WorldWarTeam team) {
		config.set("Players." + player.getUniqueId().toString() + ".Team", team.toString());
	}
	
	public static void setPlayerToTeam(UUID uuid, WorldWarTeam team) {
		config.set("Players." +uuid.toString() + ".Team", team.toString());
	}
	
	/**
	 * Gets the team the player belongs to
	 * @param player 
	 * @return
	 */
	@Refactor
	public static String getPlayerTeam(Player player) {
		String team = config.getString("Players." + player.getUniqueId().toString() + ".Team");
		return team;
	}
	
	/**
	 * Checks if the player is on a team
	 * @param player The player to check
	 * @return true if the player is on a team, false otherwise
	 */
	public boolean isPlayerOnTeam(Player player) {
		boolean contained = config.contains("Players." + player.getUniqueId().toString() + ".Team");
		return contained;
	}
	
	/**
	 * Saves a player's inventory string from {@link InventoryManager} to the PlayerData file
	 * @param player The player that the inventory belongs to
	 * @param inv The string that contains the serialized inventory
	 */
	public static void saveInventoryToFile(Player player, String inv) {
		config.set("Players." + player.getUniqueId().toString() + ".Inventory", inv);
	}
	
	/**
	 * Gets the serialized string of the specified player's inventory
	 * @param player The player to get the inventory for
	 * @return A string to be de-serialized by {@link InventoryManager}
	 */
	@Deprecated
	public static String getInventoryFromFile(Player player) {
		String inventory = config.getString("Players." + player.getUniqueId().toString() + ".Inventory");
		return inventory;
	}
	
	/**
	 * Same method as @see {@link #getPlayerAmmoFromFile(Player, String)} just with UUID compatibility
	 * @param uuid
	 * @param gun
	 * @return
	 */
	public int getPlayerAmmoFromFile(UUID uuid, String gun) {
		int ammo = 0;
		
		ammo = config.getInt("Players." + uuid.toString() + ".Ammo." + gun);
		return ammo;
	}
	
	/**
	 * Same method as @see {@link #setPlayerToTeam(Player, String)} just with UUID compatibility
	 * @param uuid
	 * @param team
	 */
	@Deprecated
	public static void setPlayerToTeam(UUID uuid, String team) {
		config.set("Players." + uuid.toString() + ".Team", team);
		ExtraConfigHandler.saveConfig(plugin.fileSep + "PlayerData");
	}
}
