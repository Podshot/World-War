package io.github.podshot.api.interfaces;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public interface IVehicle extends Listener {
	
	@EventHandler
	void onPilotEnterVehicle(NPCRightClickEvent e);
	
	@EventHandler
	void onPlayerGetInSeat(PlayerInteractEntityEvent e);
	
	/**
	 * Gets the amount of Players the vehicle can hold
	 * @return
	 */
	int getSeatCount();
	
	/**
	 * Gets the Inventory of a Vehicle
	 * @return the Vehicle's inventory
	 */
	ItemStack[] getInventory();

	/**
	 * Gets the template NPC of the Vehicle (Before a Player has enterd it)
	 * @return
	 */
	NPC getNPCTemplate();
	
	/**
	 * Method that respawns the NPC 
	 * @param loc
	 */
	void respawnVehicle(Location loc);

}
