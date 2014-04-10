package io.github.podshot.events;

import io.github.podshot.entities.DisguisePlayerAsVehicle;
import io.github.podshot.internals.Internals;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.metadata.MetadataValue;

public class EntityEvents implements Listener {
	private DisguisePlayerAsVehicle dpav;
	
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent evt) {
		if (Internals.warDeclared) {
			String vehicleType = null;
			Player player = evt.getPlayer();
			Entity ent = evt.getRightClicked();
			for (MetadataValue dat : ent.getMetadata("WorldWar.Vehicle")) {
				if (dat.getOwningPlugin().getName().equals("World War")) {
					vehicleType = dat.asString();
				}
			}

			if (vehicleType != null) {
				switch(vehicleType) {
				case "scout":
					dpav.addPlayerAsBat(player);
					evt.setCancelled(true);
					break;
				case "fighter":
					dpav.addPlayerAsBlaze(player);
					evt.setCancelled(true);
					break;
				case "bomber":
					dpav.addPlayerAsDragon(player);
					evt.setCancelled(true);
					break;
				default:
					break;
				}
			}
		}

	}
}
