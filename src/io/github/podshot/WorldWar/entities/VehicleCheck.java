package io.github.podshot.WorldWar.entities;

import org.bukkit.entity.Entity;

public class VehicleCheck {

	public static boolean isVehicle(Entity entity) {
		boolean ret = false;
		if (entity.hasMetadata("WorldWar.isVehicle")) {
			ret = true;
		}
		return ret;
	}
}
