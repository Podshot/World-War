package io.github.podshot.api;

import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public interface Vehicle {
	
	@EventHandler
	public void onPilotEnterVehicle(NPCRightClickEvent e);
	
	@EventHandler
	public void onPlayerGetInSeat(PlayerInteractEntityEvent e);

}
