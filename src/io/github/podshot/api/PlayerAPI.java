package io.github.podshot.api;

import io.github.podshot.WorldWar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class PlayerAPI {
	
	private static WorldWar plugin = WorldWar.getInstance();
	
	public static String getTeam(Player player) {
		String team = null;
		for (MetadataValue val : player.getMetadata("WorldWar.Team")) {
			if (val.getOwningPlugin().getName().equals("WorldWar")) {
				team = val.asString();
			}
		}
		return team;
	}
	
	public static String getTeam(String playerName) {
		String team = null;
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equals(playerName)) {
				for (MetadataValue val : p.getMetadata("WorldWar.Team")) {
					if (val.getOwningPlugin().getName().equals("WorldWar")) {
						team = val.asString();
					}
				}
			}
		}
		return team;	
	}
	
	public static void setAmmoAmount(Player player, String gun, int amount) {
		if (player.hasMetadata("WorldWar.Ammo." + gun)) {
			player.removeMetadata("WorldWar.Ammo." + gun, plugin);
		}
		player.setMetadata("WorldWar.Ammo." + gun, new FixedMetadataValue(plugin, amount));
	}
	
	public static int getAmmoAmount(Player player, String gun) {
		int toReturn = 0;
		for (MetadataValue val : player.getMetadata("WorldWar.Ammo." + gun)) {
			if (val.getOwningPlugin().getName().equals("WorldWar")) {
				toReturn = val.asInt();
			}
		}
		return toReturn;
	}

}
