package io.github.podshot.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

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
			boolean toReturn = false;
			if (squadGUIOpen.contains(uuid)) {
				toReturn = true;
			}
			return toReturn;
		}
	}

	public static class SquadWaypointHandler {
		private static Map<String, Location> waypoints = new HashMap<>();

		public static void addWaypoint(String squadName, Location point) {
			if (waypoints.containsKey(squadName)) {
				removeWaypoint(squadName);
			}
			waypoints.put(squadName, point);
		}

		public static void removeWaypoint(String squadName) {
			if (waypoints.containsKey(squadName)) {
				waypoints.remove(squadName);
			}
		}

		public static Location getWaypoint(String squadName) {
			Location point = null;
			if (waypoints.containsKey(squadName)) {
				point = waypoints.get(squadName);
			}
			return point;
		}
	}

	public static class RifleReloadHandler {
		private static List<UUID> rifleReloading = new ArrayList<UUID>();

		public static void addToList(UUID uuid) {
			rifleReloading.add(uuid);
		}

		public static void removeFromList(UUID uuid) {
			rifleReloading.remove(uuid);
		}

		public static boolean isInList(UUID uuid) {
			boolean toReturn = false;
			if (rifleReloading.contains(uuid)) {
				toReturn = true;
			}
			return toReturn;
		}
	}

	public static class RocketLauncherReloadHandler{
		private static List<UUID> rocketLauncherReloading = new ArrayList<UUID>();

		public static void addToList(UUID uuid) {
			rocketLauncherReloading.add(uuid);
		}

		public static void removeFromList(UUID uuid) {
			rocketLauncherReloading.remove(uuid);
		}

		public static boolean isInList(UUID uuid) {
			boolean toReturn = false;
			if (rocketLauncherReloading.contains(uuid)) {
				toReturn = true;
			}
			return toReturn;
		}
	}
	
	public static class SniperRifleReloadHandler {
		private static List<UUID> sniperRifleReloading = new ArrayList<UUID>();
		
		public static void addToList(UUID uuid) {
			sniperRifleReloading.add(uuid);
		}
		
		public static void removeFromList(UUID uuid) {
			sniperRifleReloading.remove(uuid);
		}
		
		public static boolean isInList(UUID uuid) {
			boolean toReturn = false;
			if (sniperRifleReloading.contains(uuid)) {
				toReturn = true;
			}
			return toReturn;
		}
	}

}
