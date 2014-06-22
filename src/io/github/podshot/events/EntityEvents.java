package io.github.podshot.events;

import io.github.podshot.internals.Internals;

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
