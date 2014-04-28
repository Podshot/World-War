package io.github.podshot.api;

import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.event.EventHandler;

public interface Vehicle {
	
	public int seats = 1;
	
	@EventHandler
	public void onPlayerEnterVehicle(NPCRightClickEvent e);

}
