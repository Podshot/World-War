package io.github.podshot.files;

import io.github.podshot.WorldWar;
import io.github.podshot.api.PlayerAPI;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

public class PlayerDataYAML {
	
	private WorldWar plugin = WorldWar.getInstance();

	public PlayerDataYAML() {
		File playerDataYaml = new File(plugin.getDataFolder() + plugin.fileSep + "PlayerData");
		if (!playerDataYaml.exists()) {
			ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
			ExtraConfigHandler.saveConfig(plugin.fileSep + "PlayerData");
		}
	}
	
	public void setPlayerAmmoToFile(Player player) {
		FileConfiguration dataConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		
		dataConfig.set("Players." + player.getName() + ".Ammo.Rifle", PlayerAPI.getAmmoAmount(player, "Rifle"));
		dataConfig.set("Players." + player.getName() + ".Ammo.Rocket-Launcher", PlayerAPI.getAmmoAmount(player, "Rocket"));
		
		ExtraConfigHandler.saveConfig(plugin.fileSep + "PlayerData");
	}
	
	public int getPlayerAmmoFromFile(Player player, String gun) {
		int ammo = 0;
		
		FileConfiguration dataConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "PlayerData");
		
		ammo = dataConfig.getInt("Players." + player.getName() + ".Ammo." + gun);
		return ammo;
	}
}
