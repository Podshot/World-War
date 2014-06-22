package io.github.podshot.events;

import io.github.podshot.WorldWar;
import io.github.podshot.api.PlayerAPI;
import io.github.podshot.files.PlayerDataYAML;
import io.github.podshot.gui.ClassChooser;
import io.github.podshot.gui.TeamChooser;
import io.github.podshot.internals.Internals;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
					Player player = evt.getPlayer();
					String memberOfTeam = PlayerDataYAML.getPlayerTeam(player);
					plugin.logger.info("Cheking to see if username is already stored");
					if (memberOfTeam.equals("Blue")) {
						plugin.logger.info("Player's team is Blue");
						player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, "Blue"));
					} else if (memberOfTeam.equals("Red")) {
						plugin.logger.info("Player's team is Red");
						player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, "Red"));
					} else  {
						plugin.logger.info("Name is not present in the player file");
						player.openInventory(TeamChooser.getTeamChooserGui());
						evt.getPlayer().sendMessage("You are logged in");
					}
					int rifleAmmo = PlayerDataYAML.getPlayerAmmoFromFile(player, "Rifle");
					int rocketAmmo = PlayerDataYAML.getPlayerAmmoFromFile(player, "Rocket-Launcher");
					player.setMetadata("WorldWar.Ammo.Rifle", new FixedMetadataValue(plugin, rifleAmmo));
					player.setMetadata("WorldWar.Ammo.Rocket", new FixedMetadataValue(plugin, rocketAmmo));
				}
			}
		}, 40L);
		return;
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent evt) {
		if (Internals.isWarDeclared()) {
			Player player = evt.getPlayer();
			String team = PlayerAPI.getTeam(player);
			plugin.logger.info("Saving player");
			if (team != null) {
				plugin.logger.info("Team is not \"null\" saving data");
				PlayerDataYAML.setPlayerToTeam(player, team);
				PlayerDataYAML.setPlayerAmmoToFile(player);
			}
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
