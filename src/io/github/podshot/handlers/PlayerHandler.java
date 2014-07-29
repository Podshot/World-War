package io.github.podshot.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerHandler {
	
	public static class SquadGUIHandler {
		private static List<UUID> squadGUIOpen = new ArrayList<UUID>();
		
		public static void addPlayer(UUID uuid) {
			squadGUIOpen.add(uuid);
		}
		
		public static void removePlayer(UUID uuid) {
			squadGUIOpen.remove(uuid);
		}
		
		public static boolean isPlayerInList(UUID uuid) {
			boolean ret = false;
			if (squadGUIOpen.contains(uuid)) {
				ret = true;
			}
			
			return ret;
		}
	}
}
