package io.github.podshot.api;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

import io.github.podshot.WorldWar;

public class SquadAPI {
	
	private static WorldWar plugin = WorldWar.getInstance();
	
	public static List<String> getSquads() {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		List<String> squads = squadConfig.getStringList("Squads.Global.SquadList");
		ExtraConfigHandler.saveConfig(plugin.fileSep + "Squads");
		return squads;
	}
	
	public static boolean isFounder(String name, String squadName) {
		boolean ret = false;
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		String founder = squadConfig.getString("Squads." + squadName + ".Founder");
		if (founder.equals(name)) {
			ret = true;
		}
		return ret;
	}
	
	public static boolean isInSquad(String playerToKick, String squadName) {
		boolean ret = false;
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		List<String> members = squadConfig.getStringList("Squads." + squadName + ".Members");
		if (members.contains(playerToKick)) {
			ret = true;
		}
		return ret;
	}

}
