package io.github.podshot.files;

import org.bukkit.configuration.file.FileConfiguration;

import io.github.podshot.WorldWar;
import io.github.podshot.internals.ConfigInternals;

public class ReadConfig {
	private WorldWar plugin = WorldWar.getInstance();
	
	public void readConfig() {
		FileConfiguration config = plugin.getConfig();
		ConfigInternals.aatoe = config.getBoolean("Allow-Anyone-To-Enter-Vehicles");
		ConfigInternals.huolagw = config.getBoolean("Hyped-Up-On-Lemonade-And-Gummy-Worms");
	}

}
