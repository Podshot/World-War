package io.github.podshot.WorldWar.api;

import io.github.podshot.WorldWar.WorldWar;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class VehicleAPI {

	private static WorldWar plugin = WorldWar.getInstance();

	/**
	 * 
	 * @param clicker
	 * @return
	 */
	public static boolean inVehicle(Player clicker) {
		boolean in = false;
		if (clicker.hasMetadata("WorldWar.inVehicle")) {
			for (MetadataValue value : clicker.getMetadata("WorldWar.inVehicle")) {
				if (value.getOwningPlugin().getName().equals("WorldWar")) {
					in = value.asBoolean();
				}
			}
		}
		return in;
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public static String getVehicleType(Player player) {
		String type = null;
		if (player.hasMetadata("WorldWar.vehicle")) {
			for (MetadataValue value : player.getMetadata("WorldWar.vehicle")) {
				if (value.getOwningPlugin().getName().equals("WorldWar")) {
					type = value.asString();
				}
			}
		}
		return type;
	}

	/**
	 * 
	 * @param player
	 * @param vehicle
	 */
	public static void setVehicle(Player player, String vehicle) {
		player.setMetadata("WorldWar.vehicle", new FixedMetadataValue(plugin, vehicle));
		player.setMetadata("WorldWar.inVehicle", new FixedMetadataValue(plugin, true));
	}
	
	/**
	 * 
	 * @param player
	 */
	public static void removeVehicle(Player player) {
		player.removeMetadata("WorldWar.vehicle", plugin);
		player.removeMetadata("WorldWar.inVehicle", plugin);
	}

}
