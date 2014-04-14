package io.github.podshot.handlers;

import io.github.podshot.WorldWar;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationHandler {
	
	private WorldWar plugin = WorldWar.getInstance();
	
	public void get(Location location, int radius) {
		Player[] players = plugin.getServer().getOnlinePlayers();
		int radiusSquared = radius*radius;
		
		for (int i = 0; i < players.length; i++) {
			if (players[i].getLocation().distanceSquared(location) <= radiusSquared) {
				players[i].sendMessage("Test Message");
			}
		}
	}

}
