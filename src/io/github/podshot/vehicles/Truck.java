package io.github.podshot.vehicles;

import io.github.podshot.WorldWar;
import io.github.podshot.entities.DisguisePlayerAsVehicle;
import io.github.podshot.internals.Internals;
import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

public class Truck implements Listener {
	
	private static WorldWar plugin = WorldWar.getInstance();
	private static DisguiseCraftAPI dcAPI = plugin.getDCAPI();
	private int seats = 4;

	@EventHandler
	public void onPilotEnterVehicle(NPCRightClickEvent e) {
		if (Internals.isWarDeclared()) {
			if (e.getNPC().getName().equals("Truck")) {
				DisguisePlayerAsVehicle.addPlayerAsCow(e.getClicker());
				e.getClicker().setMetadata("WorldWar.Vehicle.Passangers", new FixedMetadataValue(plugin, 0));
			}
		}

	}

	@EventHandler
	public void onPlayerGetInSeat(PlayerInteractEntityEvent e) {
		boolean valid = false;
		int seatsTaken = 0;
		if (Internals.isWarDeclared()) {
			if (e.getRightClicked().getType() == EntityType.PLAYER && (!(dcAPI.isDisguised(e.getPlayer())))) {
				for (MetadataValue value : e.getRightClicked().getMetadata("WorldWar.Vehicle.Type")) {
					if (value.getOwningPlugin().getName().equals("WorldWar")) {
						if (value.asString().equals("Truck")) {
							valid = true;
						}
					}
				}
				for (MetadataValue value : e.getRightClicked().getMetadata("WorldWar.Vehicle.Passengers")) {
					if (value.getOwningPlugin().getName().equals("WorldWar")) {
						seatsTaken = value.asInt();
					}
				}
				if (valid && seatsTaken != seats) {
					e.getRightClicked().setPassenger(e.getPlayer());
					e.getRightClicked().setMetadata("WorldWar.Vehicles.Passengers", new FixedMetadataValue(plugin, seatsTaken + 1));
				}
			}
		}
	}

}
