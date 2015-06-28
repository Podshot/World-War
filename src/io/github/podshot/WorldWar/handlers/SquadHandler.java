package io.github.podshot.WorldWar.handlers;

import java.util.HashMap;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.squads.Squad_OLD;

@Deprecated
public class SquadHandler {
	
	private static WorldWar plugin = WorldWar.getInstance();
	private static HashMap<String, Squad_OLD> squads = new HashMap<String, Squad_OLD>();
	
	public static boolean squadNameAvailable(String squadName) {
		return !squads.containsKey(squadName);
	}
	
	

}
