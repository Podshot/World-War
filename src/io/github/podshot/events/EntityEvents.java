package io.github.podshot.events;

import io.github.podshot.entities.DisguisePlayerAsVehicle;
import io.github.podshot.internals.Internals;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class EntityEvents implements Listener {

	@EventHandler
	public void onPlayerClickOnNPC(NPCRightClickEvent evt) {
		if (Internals.warDeclared) {
			Player player = evt.getClicker().getPlayer();
			NPC ent = evt.getNPC();
			//DisguisePlayerAsVehicle.addPlayerAsDragon(player);
			//DisguisePlayerAsVehicle.addPlayerAsBlaze(player);
			evt.setCancelled(true);
			/*
			for (MetadataValue dat : ent.getMetadata("WorldWar.isVehicle")) {
				if (dat.getOwningPlugin().getName().equals("World War")) {
					vehicleType = dat.asString();
				}
			}
			 */
			String name = ent.getName().toString();

			switch (name) {
			case "Scout Plane":
				DisguisePlayerAsVehicle.addPlayerAsBat(player);
				break;
			case "Fighter Plane":
				DisguisePlayerAsVehicle.addPlayerAsBlaze(player);
				break;
			case "Bomber":
				DisguisePlayerAsVehicle.addPlayerAsDragon(player);
				break;
			default:
				break;
			}
			evt.setCancelled(true);
			ent.despawn();
		}
		return;
	}
	
	public void onSpawnEvent(CreatureSpawnEvent evt) {
		if (evt.getSpawnReason() == SpawnReason.SPAWNER_EGG) {
			evt.setCancelled(true);
			evt.getEntity().damage(20.0D);
		}
		if (evt.getSpawnReason() == SpawnReason.DISPENSE_EGG) {
			evt.setCancelled(true);
			evt.getEntity().damage(20.0D);
		}
		return;
		
	}
}
