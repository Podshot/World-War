package io.github.podshot.WorldWar.squads;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.SquadAPI;
import io.github.podshot.WorldWar.internals.Internals;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
				Player player = e.getPlayer();
				@Override
				public void run() {
					String squad = SquadAPI.getSquadForPlayer(player.getUniqueId());	
					if (!(squad.equals("Not in Squad"))) {
						player.setMetadata("WorldWar.Squad", new FixedMetadataValue(plugin, squad));
						player.setMetadata("WorldWar.inSquad", new FixedMetadataValue(plugin, true));
					}
				}	
			}, 40L);	
		}
	}
}
