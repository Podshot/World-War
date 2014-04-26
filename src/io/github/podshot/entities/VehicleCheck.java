package io.github.podshot.entities;

import org.bukkit.entity.Entity;

public class VehicleCheck {

	public boolean isVehicle(Entity entity) {
		boolean ret = false;
		if (entity.hasMetadata("WorldWar.isVehicle")) {
			ret = true;
		}
		return ret;
	}
}
