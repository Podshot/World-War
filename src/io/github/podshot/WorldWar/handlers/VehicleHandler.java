package io.github.podshot.WorldWar.handlers;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;

public class VehicleHandler implements Listener {

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent evt) {
		String damagerTeam = null;
		String damagedTeam = null;
		if (!(WarHandler.isWarDeclared())) {
			return;
		}

		if (!(evt.getEntity().getType() == EntityType.PLAYER)) {
			return;
		}

		if (!(evt.getDamager().getType() == EntityType.PLAYER)) {
			return;
		}

		Player player = (Player) evt.getEntity();
		Player damager = (Player) evt.getDamager();
		Integer healthLeft = (int) evt.getDamage();
		for (MetadataValue val : player.getMetadata("WorldWar.Team")) {
			if (val.getOwningPlugin().getName().equals("WorldWar")) {
				damagedTeam = val.asString();
			}
		}

		for (MetadataValue val : damager.getMetadata("WorldWar.Team")) {
			if (val.getOwningPlugin().getName().equals("WorldWar")) {
				damagerTeam = val.asString();
			}
		}
		int health = 20 - healthLeft;
		String vehicleType = null;
		if (damagerTeam.equals(damagedTeam)) {
			evt.setCancelled(true);
		}

		for (MetadataValue val : player.getMetadata("WorldWar.Vehicle.Type")) {
			if (val.getOwningPlugin().getName().equals("WorldWar")) {
				vehicleType = val.asString();
			}
		}
		switch (vehicleType) {
		case "Bomber":
			if (health == 2) {
				player.getVelocity().setY(1.0F);
			}
			break;
		case "Fighter":
			if (health == 3) {
				player.getVelocity().setY(1.0F);
			}
			break;
		default:
			break;
		}
	}

}
