package io.github.podshot.WorldWar.events;

import io.github.podshot.WorldWar.internals.Internals;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class EntityEvents implements Listener {
	
	@EventHandler
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
