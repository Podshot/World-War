package io.github.podshot.events;

import java.util.List;

import io.github.podshot.WorldWar;
import io.github.podshot.internals.Internals;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class PlayerEvents implements Listener {

	private Plugin plugin = WorldWar.getInstance();

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent evt) {
		if (Internals.warDeclared) {
			if (Internals.playersTeamFile.contains(evt.getPlayer().getName())) {
				if (Internals.playersTeamFile.getProperty(evt.getPlayer().getName()) == "Blue") {
					Player player = (Player) evt.getPlayer();
					player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin ,"Blue"));
				} else if (Internals.playersTeamFile.getProperty(evt.getPlayer().getName()) == "Red") {
					Player player = (Player) evt.getPlayer();
					player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, "Red"));
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent evt) {
		if (Internals.warDeclared) {
			String team = null;
			Player player = (Player) evt.getPlayer();
			List<MetadataValue> values = player.getMetadata("WorldWar.Team");
			for (MetadataValue val : values) {
				if (val.getOwningPlugin().getName().equals("World War")) {
					team = val.asString();
				}
			}
			if (team != null) {
				Internals.playersTeamFile.setProperty(player.getName(), team);
			}
		}
	}
}
