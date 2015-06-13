package io.github.podshot.WorldWar.files;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

import io.github.podshot.WorldWar.WorldWar;

@Deprecated
public class TeamYAML {
	
	private static WorldWar plugin = WorldWar.getInstance();
	
	public TeamYAML() {
		File teamsYaml = new File(plugin.getDataFolder() + plugin.fileSep + "Teams");
		if (!teamsYaml.exists()) {
			ExtraConfigHandler.getConfig(plugin.fileSep + "Teams");
			ExtraConfigHandler.saveConfig(plugin.fileSep + "Teams");
		}
	}
	
	public static void setPlayerToTeam(String name, String team) {
		FileConfiguration cfg = ExtraConfigHandler.getConfig(plugin.fileSep + "Teams");
		
		cfg.set("Player.Teams." + name.toString(), team);
		
		ExtraConfigHandler.saveConfig(plugin.fileSep + "Teams");
	}
	
	public static String getPlayerTeam(String name) {
		FileConfiguration config = ExtraConfigHandler.getConfig(plugin.fileSep + "Teams");
		
		String team = config.getString("Player.Teams." + name.toString());
		plugin.logger.info("Player: \"" + name + "\" is on " + team + " Team");
		return team;		
	}
	public static boolean isPlayerOnTeam(String name) {
		FileConfiguration config = ExtraConfigHandler.getConfig(plugin.fileSep + "Teams");
		
		boolean contained = config.contains("Player.Teams." + name.toString());
		return contained;
	}

}
