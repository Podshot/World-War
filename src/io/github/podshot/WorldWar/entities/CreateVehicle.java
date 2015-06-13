package io.github.podshot.WorldWar.entities;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.files.StructureYAML;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;

public class CreateVehicle {
	
	private WorldWar plugin = WorldWar.getInstance();

	public CreateVehicle(Location spawnLoc, VehicleType type) {
		NPCRegistry npcr = CitizensAPI.getNPCRegistry();
		switch(type) {
		case BOMBER:
			NPC bomber = npcr.createNPC(EntityType.ENDER_DRAGON, "Bomber");
			bomber.spawn(spawnLoc);
			bomber.getNavigator().setTarget(spawnLoc);
			bomber.getEntity().setMetadata("WorldWar.isVehicle", new FixedMetadataValue(plugin, true));
			StructureYAML.setVehiclePad(spawnLoc, type);
			break;
		case TRUCK:
			NPC truck = npcr.createNPC(EntityType.COW, "Truck");
			truck.spawn(spawnLoc);
			truck.getNavigator().setTarget(spawnLoc);
			truck.getEntity().setMetadata("WorldWar.isVehicle", new FixedMetadataValue(plugin, true));
			break;
		default:
			break;
		}
	}

}
