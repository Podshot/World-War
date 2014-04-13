package io.github.podshot.sides.storing;

import io.github.podshot.WorldWar;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class Maps {
	private static WorldWar plugin = WorldWar.getInstance();

	public Map<String, String> teamMap = new HashMap<>();

	public void addPlayerToTeam(Player p, String team) {
		// Player name, Team
		String name = p.getName();
		if (teamMap.containsKey(name)) {
			plugin.logger.info("Player: " + name + " is now on the " + team + " side!");
		} else {
			teamMap.put(name, team);
			p.sendMessage("You are now on the " + team + " side");
		}
	}

}
