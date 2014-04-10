package io.github.podshot.events;

import io.github.podshot.internals.Internals;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityEvents implements Listener {

	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent evt) {
		if (Internals.warDeclared) {
			Player player = evt.getPlayer();
			Entity ent = evt.getRightClicked();
		}
	}

}
