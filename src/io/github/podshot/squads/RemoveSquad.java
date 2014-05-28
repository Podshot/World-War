package io.github.podshot.squads;

import io.github.podshot.WorldWar;

import org.bukkit.configuration.file.FileConfiguration;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

public class RemoveSquad {
	
	private WorldWar plugin = WorldWar.getInstance();

	public RemoveSquad(String squadName, String name) {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.getDataFolder() + plugin.fileSep + "Squads");
		String founder = squadConfig.getString("Squads." + squadName + ".Founder");
		if (founder.equals(name)) {
			
		}
	}

}
