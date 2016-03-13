package io.github.podshot.WorldWar.handlers;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.squads.Squad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

public class PlayerHandler {

	public static class SquadGUIHandler {
		private static HashMap<UUID, Squad> asking_squad = new HashMap<UUID, Squad>();
		
		public static void addPlayer(UUID uuid, Squad asking) {
			asking_squad.put(uuid, asking);
		}

		public static void removePlayer(UUID uuid) {
			asking_squad.remove(uuid);
		}

		public static boolean isPlayerInList(UUID uuid) {
			return asking_squad.containsKey(uuid);
		}
		
		public static Squad getAskingSquad(UUID uuid) {
			if (asking_squad.containsKey(uuid)) {
				return asking_squad.get(uuid);
			}
			return null;
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

	public static class BuildingGUIHandler {
		private static List<UUID> buildingGUIOpen = new ArrayList<UUID>();

		public static void addPlayer(UUID uuid) {
			buildingGUIOpen.add(uuid);
		}

		public static void removePlayer(UUID uuid) {
			buildingGUIOpen.remove(uuid);
		}

		public static boolean isPlayerInList(UUID uuid) {
			boolean toReturn = false;
			if (buildingGUIOpen.contains(uuid)) {
				toReturn = true;
			}
			return toReturn;
		}
	}
	
	public static class BuildingHandler {
		private static HashMap<UUID, Location> buildingMap = new HashMap<UUID, Location>();
		
		public static Location getLocation(UUID uuid) {
			WorldWar.getInstance().logger.info("Got Location from map: " + uuid.toString());
			return buildingMap.get(uuid);
		}
		
		public static void setLocation(UUID uuid, Location loc) {
			WorldWar.getInstance().logger.info("Put Location into map (" + uuid.toString() + "): " + loc.toString());
			buildingMap.put(uuid, loc);
		}
		
		public static void removeLocation(UUID uuid) {
			WorldWar.getInstance().logger.info("Removed Location into map: " + "(" + uuid.toString() + ")");
			buildingMap.remove(uuid);
		}
		
		public static boolean hasLocationInMap(UUID uuid) {
			WorldWar.getInstance().logger.info("Map: " + buildingMap.toString());
			boolean toReturn = false;
			if (buildingMap.containsKey(uuid)) {
				toReturn = true;
			}
			return toReturn;
		}
	}
	
	public static class ShotgunReloadHandler {
		private static List<UUID> shotgunReloading = new ArrayList<UUID>();

		public static boolean isInList(UUID uuid) {
			boolean toReturn = false;
			if (shotgunReloading.contains(uuid)) {
				toReturn = true;
			}
			return toReturn;
		}

		public static void addToList(UUID uuid) {
			shotgunReloading.add(uuid);
		}

		public static void removeFromList(UUID uuid) {
			shotgunReloading.remove(uuid);
		}
	}
	
	public static class TeamSwitchListHandler {
		private static List<UUID> blueWaitList = new ArrayList<UUID>();
		private static List<UUID> redWaitList = new ArrayList<UUID>();
		
		public static void addPlayerToBlueWaitList(UUID uuid) {
			blueWaitList.add(uuid);
		}
		
		public static void addPlayerToRedWaitList(UUID uuid) {
			redWaitList.add(uuid);
		}
		
		public static void removePlayerFromBlueWaitList(int position) {
			blueWaitList.remove(position);
		}
		
		public static void removePlayerFromRedWaitList(int position) {
			redWaitList.remove(position);
		}
		
		public static void removePlayerFromBlueWaitList(UUID uuid) {
			blueWaitList.remove(uuid);
		}
		
		public static void removePlayerFromRedWaitList(UUID uuid) {
			redWaitList.remove(uuid);
		}
		
		public static UUID getPlayerFromBlueWaitList(int position) {
			return blueWaitList.get(position);
		}
		
		public static UUID getPlayerFromRedWaitList(int posistion) {
			return redWaitList.get(posistion);
		}
	}
	
	public static class BlockMessagesHandler {
		private static List<UUID> doNotRecieve = new ArrayList<UUID>();
		
		public static void addPlayer(UUID uuid) {
			doNotRecieve.add(uuid);
		}
		
		public static void removePlayer(UUID uuid) {
			doNotRecieve.remove(uuid);
		}
		
		public static boolean isInList(UUID uuid) {
			boolean toReturn = false;
			if (doNotRecieve.contains(uuid)) {
				toReturn = true;
			}
			return toReturn;
		}
	}
}
