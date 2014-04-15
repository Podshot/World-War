package io.github.podshot.events;

import io.github.podshot.WorldWar;
import io.github.podshot.files.SavePlayerData;
import io.github.podshot.gui.ClassChooser;
import io.github.podshot.gui.TeamChooser;
import io.github.podshot.internals.Internals;

import java.io.IOException;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
				if (Internals.playersTeamFile.getProperty(evt.getPlayer().getName()) == "Blue") {
					Player player = evt.getPlayer();
					player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, "Blue"));
				} else if (Internals.playersTeamFile.getProperty(evt.getPlayer().getName()) == "Red") {
					Player player = evt.getPlayer();
					player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, "Red"));
				}
			} else {
				Player player = evt.getPlayer();
				player.openInventory(TeamChooser.getTeamChooserGui());
			}
		}
		return;
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
			//if (team != null) {
			Internals.playersTeamFile.setProperty(player.getName(), team);
			//}
			if (team != null) {
				try {
					SavePlayerData.updateTeamFile(player.getName(), team);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent evt) {
		if (Internals.warDeclared) {
			Player player = evt.getEntity();
			player.getInventory().clear();
			player.updateInventory();
			evt.setDroppedExp(0);
		}
		return;
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent evt) {
		if (Internals.warDeclared) {
			Player player = evt.getPlayer();
			player.openInventory(ClassChooser.getClassChooserGui());
		}
		return;
	}

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent evt) {
		if (Internals.warDeclared) {

			Player player = evt.getPlayer();
			Location location = player.getLocation();
			if (location.getY() > 256) {
				Location newLoc = new Location(location.getWorld(), location.getX(), 256, location.getZ(), location.getYaw(), location.getPitch());
				player.teleport(newLoc);
			}

			for (Location loc : Internals.explosiveLocations) {
				if (loc == evt.getPlayer().getLocation()) {
					Block explosive = loc.getBlock();
					evt.getPlayer().getWorld().createExplosion(loc, 0.0F);
					String team = null;
					String bTeam = null;
					for (MetadataValue md : evt.getPlayer().getMetadata("WorldWar.Team")) {
						if (md.getOwningPlugin().getName().equals("WorldWar")) {
							team = md.asString();
						}
					}
					List<MetadataValue> vals = explosive.getMetadata("WorldWar.Team");
					for (MetadataValue val : vals) {
						if (val.getOwningPlugin().getName().equals("WorldWar")) {
							bTeam = val.asString();
						}
					}

					if (team != bTeam) {
						evt.getPlayer().getWorld().createExplosion(loc, 0.0F);
					}
				}
			}
		}
		return;
	}
}
