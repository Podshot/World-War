package io.github.podshot.WorldWar.handlers;

import io.github.podshot.WorldWar.files.StructureYAML;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class BlockHandler {

	public static class MortarHandler {
		private static List<Location> mortars = new ArrayList<Location>();
		private static List<Location> mortar_cooldown = new ArrayList<Location>();

		public static void addMortar(Location loc) {
			if (!(mortars.contains(loc))) {
				mortars.add(loc);
			}
		}

		public static void removeMortar(Location loc) {
			if (mortars.contains(loc)) {
				mortars.remove(loc);
			}
		}
		
		public static void addCooldown(Location loc) {
			if (!(mortars.contains(loc))) {
				addMortar(loc);
			}
			if (!(mortar_cooldown.contains(loc))) {
				mortar_cooldown.add(loc);
			}
		}
		
		public static void removeCooldown(Location loc) {
			if (mortar_cooldown.contains(loc)) {
				mortar_cooldown.add(loc);
			}
		}
		
		public static void save() {
			StructureYAML.saveMortar(mortars);
		}
	}
}
