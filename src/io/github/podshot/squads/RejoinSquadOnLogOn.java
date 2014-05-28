package io.github.podshot.squads;

import io.github.podshot.WorldWar;
import io.github.podshot.internals.Internals;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class RejoinSquadOnLogOn implements Listener {
	
	private WorldWar plugin = WorldWar.getInstance();
	
	@EventHandler
	public void addToSquad(final PlayerLoginEvent evt) {
		if (Internals.isWarDeclared()) {
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				private PlayerLoginEvent e = evt;
				String name = e.getPlayer().getName();
				@Override
				public void run() {
					String squad = Squad.getSquadForPlayer(name);	
					if (!(squad.equals("Not in Squad"))) {
						e.getPlayer().setMetadata("WorldWar.Squad", new FixedMetadataValue(plugin, squad));
						e.getPlayer().setMetadata("WorldWar.inSquad", new FixedMetadataValue(plugin, true));
					}
				}	
			}, 40L);	
		}
	}
}
