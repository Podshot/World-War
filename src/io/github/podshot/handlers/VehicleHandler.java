package io.github.podshot.handlers;

import io.github.podshot.internals.Internals;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;

public class VehicleHandler implements Listener {

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent evt) {
		if (!Internals.warDeclared) {
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
		int health = 20 - healthLeft;
		String vehicleType = null;
		String damagerTeam = Internals.playersTeamFile.getProperty(damager
				.getName());
		String damagedTeam = Internals.playersTeamFile.getProperty(player
				.getName());
		if (damagerTeam == damagedTeam) {
			evt.setCancelled(true);
		}

		for (MetadataValue val : player.getMetadata("WorldWar.Vehicle.Type")) {
			if (val.getOwningPlugin().getName().equals("WorldWar")) {
				vehicleType = val.asString();
			}
		}
		if (vehicleType != null) {
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
			}
		}

	}

}
