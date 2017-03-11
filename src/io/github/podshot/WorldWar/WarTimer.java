package io.github.podshot.WorldWar;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class WarTimer implements Listener {
	
	private int totalTime;
	private int currentTime;
	private WorldWar plugin;
	
	public WarTimer(WorldWar plugin) {
		this.plugin = plugin;
		FileConfiguration config = plugin.getConfig();
		totalTime = config.getInt("Timer.TotalTime", 120);
		currentTime = config.getInt("Timer.LastTime", 0);
	}
	
	private Collection<Player> convertToGenericCollection(Collection<? extends Player> players) {
		return new ArrayList<Player>(players);
	}
}
