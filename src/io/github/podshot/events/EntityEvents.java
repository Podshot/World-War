package io.github.podshot.events;

import io.github.podshot.WorldWar;
import io.github.podshot.entities.DisguisePlayerAsVehicle;
import io.github.podshot.entities.VehicleCheck;
import io.github.podshot.internals.Internals;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class EntityEvents implements Listener {
	
	private WorldWar plugin = WorldWar.getInstance();

	@EventHandler
	public void onPlayerClickOnNPC(NPCRightClickEvent evt) {
		if (Internals.isWarDeclared()) {
			Player player = evt.getClicker().getPlayer();
			final NPC ent = evt.getNPC();
			final Location respawnLoc = ent.getStoredLocation();
			if (!(VehicleCheck.isVehicle(ent.getEntity()))) {
				return;
			}
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
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				@Override
				public void run() {
					ent.spawn(respawnLoc);
				}
			}, 4800L);
		}
		return;
	}
	
	public void onSpawnEvent(CreatureSpawnEvent evt) {
		if (!(Internals.isWarDeclared())) {
			return;
		}
		if (evt.getSpawnReason() == SpawnReason.SPAWNER_EGG) {
			evt.setCancelled(true);
		}
		return;
		
	}
}
