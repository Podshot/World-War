package io.github.podshot.WorldWar.files;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.internals.ConfigInternals;

import org.bukkit.configuration.file.FileConfiguration;

public class ReadConfig {
	
	private static WorldWar plugin = WorldWar.getInstance();

	public ReadConfig() {
		FileConfiguration config = plugin.getConfig();
		ConfigInternals.setAATOE(config.getBoolean("Allow-Anyone-To-Enter-Vehicles"));
		ConfigInternals.setHUOLAQW(config.getBoolean("Hyped-Up-On-Lemonade-And-Gummy-Worms"));
		ConfigInternals.setAnnounce(config.getBoolean("Announce-War-End-Cause-To-All-Players"));
		//ConfigInternals.setDPAHBOE(config.getBoolean("Drop-Player-At-Highest-Block-On-Exit"));
		ConfigInternals.setDevelopmentStageToListen(config.getString("Development-Stage-To-Listen-For"));
	}
	
	public static void addToTeam(String team) {
		FileConfiguration config = plugin.getConfig();
		int memberAmount = config.getInt("Teams."+team+".MemberAmount") + 1; 
		config.set("Teams."+team+".MemberAmount", memberAmount);
		plugin.saveConfig();
	}
	
	

}
