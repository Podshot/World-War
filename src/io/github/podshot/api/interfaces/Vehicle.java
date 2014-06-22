package io.github.podshot.api.interfaces;

import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public interface Vehicle {
	
	@EventHandler
	void onPilotEnterVehicle(NPCRightClickEvent e);
	
	@EventHandler
	void onPlayerGetInSeat(PlayerInteractEntityEvent e);
	
	int getSeatCount();
	
	ItemStack[] getInventory();

}
