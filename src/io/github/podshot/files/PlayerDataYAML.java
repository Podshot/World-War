package io.github.podshot.files;

import io.github.podshot.WorldWar;
import io.github.podshot.api.PlayerAPI;
import io.github.podshot.api.Refactor;

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

	/**
	 * Constructor that creates a file named "PlayerData.yml" uses Jogy34's ExtraConfigHandler
	 */
	public PlayerDataYAML() {
		File playerDataYaml = new File(plugin.getDataFolder() + plugin.fileSep + "PlayerData");
		if (!playerDataYaml.exists()) {
			ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
			ExtraConfigHandler.saveConfig(plugin.fileSep + "PlayerData");
		}
	}
	
	/**
	 * Sets the player's ammo amount to the PlayerData file
	 * @param player The player to save the ammo for
	 */
	public static void setPlayerAmmoToFile(Player player) {
		FileConfiguration dataConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		
		dataConfig.set("Players." + player.getUniqueId().toString() + ".Ammo.Rifle", PlayerAPI.getAmmoAmount(player, "Rifle"));
		dataConfig.set("Players." + player.getUniqueId().toString() + ".Ammo.Rocket-Launcher", PlayerAPI.getAmmoAmount(player, "Rocket"));
		dataConfig.set("Players." + player.getUniqueId().toString() + ".Ammo.Shotgun", PlayerAPI.getAmmoAmount(player, "Shotgun"));
		
		ExtraConfigHandler.saveConfig(plugin.fileSep + "PlayerData");
	}
	
	/**
	 * Gets the ammo amount the player has for a certain gun
	 * @param player The player that we want the ammo amount for
	 * @param gun The name of the gun we want the ammo for
	 * @return The amount of ammo the player has for that gun
	 */
	public static int getPlayerAmmoFromFile(Player player, String gun) {
		int ammo = 0;
		
		FileConfiguration dataConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		
		ammo = dataConfig.getInt("Players." + player.getUniqueId().toString() + ".Ammo." + gun);
		return ammo;
	}
	
	/**
	 * Sets the player's team to the PlayerData file
	 * @param player The player that we want to set the team of
	 * @param team The name of the team
	 */
	public static void setPlayerToTeam(Player player, String team) {
		FileConfiguration cfg = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		cfg.set("Players." + player.getUniqueId().toString() + ".Team", team);
		ExtraConfigHandler.saveConfig(plugin.fileSep + "PlayerData");
	}
	
	/**
	 * Gets the team the player belongs to
	 * @param player 
	 * @return
	 */
	public static String getPlayerTeam(Player player) {
		FileConfiguration cfg = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		String team = cfg.getString("Players." + player.getUniqueId().toString() + ".Team");
		return team;
	}
	
	/**
	 * Checks if the player is on a team
	 * @param player The player to check
	 * @return true if the player is on a team, false otherwise
	 */
	public boolean isPlayerOnTeam(Player player) {
		FileConfiguration cfg = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		boolean contained = cfg.contains("Players." + player.getUniqueId().toString() + ".Team");
		return contained;
	}
	
	/**
	 * Saves a player's inventory string from {@link InventoryManager} to the PlayerData file
	 * @param player The player that the inventory belongs to
	 * @param inv The string that contains the serialized inventory
	 */
	public static void saveInventoryToFile(Player player, String inv) {
		FileConfiguration config = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		config.set("Players." + player.getUniqueId().toString() + ".Inventory", inv);
		ExtraConfigHandler.saveConfig(plugin.fileSep + "PlayerData");
	}
	
	/**
	 * Gets the serialized string of the specified player's inventory
	 * @param player The player to get the inventory for
	 * @return A string to be de-serialized by {@link InventoryManager}
	 */
	public static String getInventoryFromFile(Player player) {
		FileConfiguration config = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		String inventory = config.getString("Players." + player.getUniqueId().toString() + ".Inventory");
		return inventory;
	}
	
	/**
	 * Same method as @see {@link #getPlayerAmmoFromFile(Player, String)} just with UUID compability
	 * @param uuid
	 * @param gun
	 * @return
	 */
	public int getPlayerAmmoFromFile(UUID uuid, String gun) {
		int ammo = 0;
		
		FileConfiguration dataConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		
		ammo = dataConfig.getInt("Players." + uuid.toString() + ".Ammo." + gun);
		return ammo;
	}
	
	/**
	 * Same method as @see {@link #setPlayerToTeam(Player, String)} just with UUID compability
	 * @param uuid
	 * @param team
	 */
	public static void setPlayerToTeam(UUID uuid, String team) {
		FileConfiguration cfg = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		cfg.set("Players." + uuid.toString() + ".Team", team);
		ExtraConfigHandler.saveConfig(plugin.fileSep + "PlayerData");
	}
	
	/**
	 * Same method as @see {@link #getPlayerTeam(Player)} just with UUID compability
	 * @param uuid
	 * @return
	 */
	public String getPlayerTeam(UUID uuid) {
		FileConfiguration cfg = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		String team = cfg.getString("Players." + uuid.toString() + ".Team");
		return team;
	}
	
	/**
	 * Same method as @see {@link #isPlayerOnTeam(Player)} just with UUID compability
	 * @param uuid
	 * @return
	 */
	public boolean isPlayerOnTeam(UUID uuid) {
		FileConfiguration cfg = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		boolean contained = cfg.contains("Players." + uuid.toString() + ".Team");
		return contained;
	}
	
	/**
	 * Same method as @see {@link #saveInventoryToFile(Player, String)} just with UUID compability
	 * @param uuid
	 * @param inv
	 */
	public static void saveInventoryToFile(UUID uuid, String inv) {
		FileConfiguration config = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		config.set("Players." + uuid.toString() + ".Inventory", inv);
		ExtraConfigHandler.saveConfig(plugin.fileSep + "PlayerData");
	}
	
	/**
	 * Same method as @see {@link #getInventoryFromFile(Player)} just with UUID compability
	 * @param uuid
	 * @return
	 */
	public static String getInventoryFromFile(UUID uuid) {
		FileConfiguration config = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		String inventory = config.getString("Players." + uuid.toString() + ".Inventory");
		return inventory;
	}
	
}
