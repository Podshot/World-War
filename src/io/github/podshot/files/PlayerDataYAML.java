package io.github.podshot.files;

import io.github.podshot.WorldWar;
import io.github.podshot.api.PlayerAPI;
import io.github.podshot.api.Refactor;

import java.io.File;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;
@Refactor
public class PlayerDataYAML {
	
	private static WorldWar plugin = WorldWar.getInstance();

	public PlayerDataYAML() {
		File playerDataYaml = new File(plugin.getDataFolder() + plugin.fileSep + "PlayerData");
		if (!playerDataYaml.exists()) {
			ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
			ExtraConfigHandler.saveConfig(plugin.fileSep + "PlayerData");
		}
	}
	
	public static void setPlayerAmmoToFile(Player player) {
		FileConfiguration dataConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		
		dataConfig.set("Players." + player.getUniqueId() + ".Ammo.Rifle", PlayerAPI.getAmmoAmount(player, "Rifle"));
		dataConfig.set("Players." + player.getUniqueId() + ".Ammo.Rocket-Launcher", PlayerAPI.getAmmoAmount(player, "Rocket"));
		
		ExtraConfigHandler.saveConfig(plugin.fileSep + "PlayerData");
	}
	
	public static int getPlayerAmmoFromFile(Player player, String gun) {
		int ammo = 0;
		
		FileConfiguration dataConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		
		ammo = dataConfig.getInt("Players." + player.getUniqueId() + ".Ammo." + gun);
		return ammo;
	}
	
	public static void setPlayerToTeam(Player player, String team) {
		FileConfiguration cfg = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		cfg.set("Players." + player.getUniqueId() + ".Team", team);
		ExtraConfigHandler.saveConfig(plugin.fileSep + "PlayerData");
	}
	
	public static String getPlayerTeam(Player player) {
		FileConfiguration cfg = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		String team = cfg.getString("Players." + player.getUniqueId() + ".Team");
		return team;
	}
	
	public boolean isPlayerOnTeam(Player player) {
		FileConfiguration cfg = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		boolean contained = cfg.contains("Players." + player.getUniqueId() + ".Team");
		return contained;
	}
	
	public int getPlayerAmmoFromFile(UUID uuid, String gun) {
		int ammo = 0;
		
		FileConfiguration dataConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		
		ammo = dataConfig.getInt("Players." + uuid.toString() + ".Ammo." + gun);
		return ammo;
	}
	
	public static void setPlayerToTeam(UUID uuid, String team) {
		FileConfiguration cfg = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		cfg.set("Players." + uuid.toString() + ".Team", team);
		ExtraConfigHandler.saveConfig(plugin.fileSep + "PlayerData");
	}
	
	public String getPlayerTeam(UUID uuid) {
		FileConfiguration cfg = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		String team = cfg.getString("Players." + uuid.toString() + ".Team");
		return team;
	}
	
	public boolean isPlayerOnTeam(UUID uuid) {
		FileConfiguration cfg = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		boolean contained = cfg.contains("Players." + uuid.toString() + ".Team");
		return contained;
	}
	
}
