package io.github.podshot.events;

import io.github.podshot.WorldWar;
import io.github.podshot.gui.ClassChooser;
import io.github.podshot.internals.Internals;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class PlayerEvents implements Listener {

	private Plugin plugin = WorldWar.getInstance();

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent evt) {
		if (Internals.warDeclared) {
			if (Internals.playersTeamFile.contains(evt.getPlayer().getName())) {
				if (Internals.playersTeamFile.getProperty(evt.getPlayer()
						.getName()) == "Blue") {
					Player player = evt.getPlayer();
					player.setMetadata("WorldWar.Team", new FixedMetadataValue(
							plugin, "Blue"));
				} else if (Internals.playersTeamFile.getProperty(evt
						.getPlayer().getName()) == "Red") {
					Player player = evt.getPlayer();
					player.setMetadata("WorldWar.Team", new FixedMetadataValue(
							plugin, "Red"));
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent evt) {
		if (Internals.warDeclared) {
			String team = null;
			Player player = evt.getPlayer();
			List<MetadataValue> values = player.getMetadata("WorldWar.Team");
			for (MetadataValue val : values) {
				if (val.getOwningPlugin().getName().equals("WorldWar")) {
					team = val.asString();
				}
			}
			if (team != null) {
				Internals.playersTeamFile.setProperty(player.getName(), team);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent evt) {
		Player player = evt.getEntity();
		player.getInventory().clear();
		player.updateInventory();
		evt.setDroppedExp(0);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent evt) {
		Player player = evt.getPlayer();
		player.openInventory(ClassChooser.getClassChooserGui());
	}
}
