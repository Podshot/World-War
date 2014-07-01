package io.github.podshot.api.interfaces;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public interface Vehicle {
	
	@EventHandler
	void onPilotEnterVehicle(NPCRightClickEvent e);
	
	@EventHandler
	void onPlayerGetInSeat(PlayerInteractEntityEvent e);
	
	int getSeatCount();
	
	/**
	 * Gets the Inventory of a Vehicle
	 * @return the Vehicle's inventory
	 */
	ItemStack[] getInventory();

	NPC getNPCTemplate();
	
	void respawnVehicle(Location loc);

}
