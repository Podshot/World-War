package io.github.podshot.files;

import io.github.podshot.WorldWar;
import io.github.podshot.internals.ConfigInternals;

import org.bukkit.configuration.file.FileConfiguration;

public class ReadConfig {
	
	private WorldWar plugin = WorldWar.getInstance();

	public void readConfig() {
		FileConfiguration config = plugin.getConfig();
		ConfigInternals.setAATOE(config.getBoolean("Allow-Anyone-To-Enter-Vehicles"));
		ConfigInternals.setHUOLAQW(config.getBoolean("Hyped-Up-On-Lemonade-And-Gummy-Worms"));
		ConfigInternals.setAnnounce(config.getBoolean("Announce-War-End-Cause-To-All-Players"));
		//ConfigInternals.aatoe = config.getBoolean("Allow-Anyone-To-Enter-Vehicles");
		//ConfigInternals.huolagw = config.getBoolean("Hyped-Up-On-Lemonade-And-Gummy-Worms");
	}

}
