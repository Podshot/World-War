package io.github.podshot.Vehicles;

import io.github.podshot.api.Vehicle;
import io.github.podshot.entities.DisguisePlayerAsVehicle;
import io.github.podshot.internals.Internals;
import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Bomber implements Listener, Vehicle {

	@EventHandler
	public void onPilotEnterVehicle(NPCRightClickEvent e) {
		if (Internals.isWarDeclared()) {
			if (e.getNPC().getName().equals("Bomber")) {
				DisguisePlayerAsVehicle.addPlayerAsDragon(e.getClicker());
			}
			return;
		}
		return;

	}

	@EventHandler
	public void onPlayerGetInSeat(PlayerInteractEntityEvent e) {
		if (e.getRightClicked().getType() == EntityType.PLAYER) {
			e.getPlayer().sendMessage(ChatColor.RED + "Only one person can be inside the Bomber");
			return;
		}
		return;
	}

}
