package io.github.podshot.events;

import io.github.podshot.WorldWar;
import io.github.podshot.api.PlayerAPI;
import io.github.podshot.files.TeamYAML;
import io.github.podshot.gui.ClassChooser;
import io.github.podshot.gui.TeamChooser;
import io.github.podshot.internals.Internals;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerEvents implements Listener {

	private WorldWar plugin = WorldWar.getInstance();

	@EventHandler
	public void onPlayerJoinEvent(final PlayerJoinEvent e) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			private PlayerJoinEvent evt = e;
			@Override
			public void run() {
				if (Internals.isWarDeclared()) {
					String memberOfTeam = TeamYAML.getPlayerTeam(evt.getPlayer().getName().toString());
					plugin.logger.info("Cheking to see if username is already stored");
					if (memberOfTeam.equals("Blue")) {
						plugin.logger.info("Player's team is Blue");
						Player player = evt.getPlayer();
						player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, "Blue"));
					} else if (memberOfTeam.equals("Red")) {
						plugin.logger.info("Player's team is Red");
						Player player = evt.getPlayer();
						player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, "Red"));
					} else  {
						plugin.logger.info("Name is not present in the player file");
						Player player = evt.getPlayer();
						player.openInventory(TeamChooser.getTeamChooserGui());
						evt.getPlayer().sendMessage("You are logged in");
					}
				}
			}
		}, 40L);
		return;
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent evt) {
		if (Internals.isWarDeclared()) {
			String team = null;
			Player player = evt.getPlayer();
			String name = player.getName().toString();
			team = PlayerAPI.getTeam(player);
			plugin.logger.info("Saving player");
			if (team != null) {
				plugin.logger.info("Team is not \"null\" saving data");
				TeamYAML.setPlayerToTeam(name, team);
			}
		}
		return;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent evt) {
		if (Internals.isWarDeclared()) {
			Player player = evt.getEntity();
			player.getInventory().clear();
			player.updateInventory();
			evt.setDroppedExp(0);
		}
		return;
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent evt) {
		if (Internals.isWarDeclared()) {
			Player player = evt.getPlayer();
			player.openInventory(ClassChooser.getClassChooserGui());
		}
		return;
	}

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent evt) {
		if (Internals.isWarDeclared()) {
			Player player = evt.getPlayer();
			Location location = player.getLocation();
			if (location.getY() > 256) {
				Location newLoc = new Location(location.getWorld(), location.getX(), 256, location.getZ(), location.getYaw(), location.getPitch());
				player.teleport(newLoc);
			}
		}
		return;
	}
}
