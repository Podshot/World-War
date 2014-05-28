package io.github.podshot.internals;

import java.util.ArrayList;
import java.util.List;

public class SquadInternals {
	
	private static List<String> squads;
	
	public SquadInternals() {
		squads = new ArrayList<String>();
	}
	
	public static List<String> getSquads() {
		return squads;
	}
	
	public static void addSquad(String name) {
		if (!(squads.contains(name))) {
			squads.add(name);
		}
	}

}
