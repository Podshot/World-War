package io.github.podshot.WorldWar.events;

import java.util.ArrayList;
import java.util.List;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.PlayerAPI;
import io.github.podshot.WorldWar.api.SquadAPI;
import io.github.podshot.WorldWar.files.PlayerDataYAML;
import io.github.podshot.WorldWar.gui.ClassChooser;
import io.github.podshot.WorldWar.gui.TeamChooser;
import io.github.podshot.WorldWar.internals.Internals;
import io.github.podshot.WorldWar.players.PlayerSorter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerEvents implements Listener {

	private static WorldWar plugin = WorldWar.getInstance();

	@EventHandler
	public void onPlayerJoinEvent(final PlayerJoinEvent e) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			private PlayerJoinEvent evt = e;
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if (Internals.isWarDeclared()) {
					Player player = evt.getPlayer();
					String memberOfTeam = PlayerDataYAML.getPlayerTeam(player);
					plugin.logger.info("Cheking to see if username is already stored");
					if (memberOfTeam == null) {
						plugin.logger.info("Name is not present in the player file");
						player.openInventory(TeamChooser.getTeamChooserGui());
						evt.getPlayer().sendMessage("You are logged in");
						PlayerAPI.setAmmoAmount(player, "Rifle", 25);
						PlayerAPI.setAmmoAmount(player, "Rocket-Launcher", 1);
						PlayerDataYAML.setPlayerAmmoToFile(player);
					} else {
						if (memberOfTeam.equals("Blue")) {
							plugin.logger.info("Player's team is Blue");
							player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, "Blue"));
							PlayerSorter.addPlayer(player.getUniqueId(), "Blue");
						} else if (memberOfTeam.equals("Red")) {
							plugin.logger.info("Player's team is Red");
							player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, "Red"));
						}
						int rifleAmmo = PlayerDataYAML.getPlayerAmmoFromFile(player, "Rifle");
						int rocketAmmo = PlayerDataYAML.getPlayerAmmoFromFile(player, "Rocket-Launcher");
						int shotgunAmmo = PlayerDataYAML.getPlayerAmmoFromFile(player, "Shotgun");
						int pistolAmmo = PlayerDataYAML.getPlayerAmmoFromFile(player, "Pistol");

						player.setMetadata("WorldWar.Ammo.Rifle", new FixedMetadataValue(plugin, rifleAmmo));
						player.setMetadata("WorldWar.Ammo.Rocket", new FixedMetadataValue(plugin, rocketAmmo));
						player.setMetadata("WorldWar.Ammo.Shotgun", new FixedMetadataValue(plugin, shotgunAmmo));
						player.setMetadata("WorldWar.Ammo.Pistol", new FixedMetadataValue(plugin, pistolAmmo));

						if (player.hasPermission("worldwar.notifyUpdate") && plugin.getNotifyUpdate()) {

						}
						if (!(SquadAPI.getSquadForPlayer(player.getUniqueId()).equals("Not in Squad"))) {
							if (player.getInventory().contains(new ItemStack(Material.COMPASS))) {
								for (ItemStack item : player.getInventory().getContents()) {
									if (item.getType() == Material.COMPASS) {
										if (item.hasItemMeta()) {
											if (item.getItemMeta().hasDisplayName()) {
												String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
												if (name.equals("Squad Objective Locator")) {
													Location objLoc = SquadAPI.getSquadObjective(SquadAPI.getSquadForPlayer(player.getUniqueId()));
													ItemMeta im = item.getItemMeta();
													im.setLore(null);
													item.setItemMeta(im);
													List<String> lore = new ArrayList<String>();
													lore.add("X: " + objLoc.getBlockX());
													lore.add("Y: " + objLoc.getBlockY());
													lore.add("Z: " + objLoc.getBlockZ());
													ItemMeta nim = item.getItemMeta();
													nim.setLore(lore);
													item.setItemMeta(nim);
													player.updateInventory();
												}
											}
										}
									}
								}
							}

						}
					}
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
			if (evt.getTo().getBlockX() == evt.getFrom().getBlockX() && evt.getTo().getBlockY() == evt.getFrom().getBlockY() && evt.getTo().getBlockZ() == evt.getFrom().getBlockZ()) return;

			Player player = evt.getPlayer();
			Location location = player.getLocation();
			if (location.getY() > 256) {
				Location newLoc = new Location(location.getWorld(), location.getX(), 256, location.getZ(), location.getYaw(), location.getPitch());
				player.teleport(newLoc);
			}

		}
		return;
	}

	@SuppressWarnings("deprecation")
	public static void doReloadFix(Player player) {
		if (Internals.isWarDeclared()) {
			String memberOfTeam = PlayerDataYAML.getPlayerTeam(player);
			plugin.logger.info("Cheking to see if username is already stored");
			if (memberOfTeam == null) {
				plugin.logger.info("Name is not present in the player file");
				player.openInventory(TeamChooser.getTeamChooserGui());
				player.sendMessage("You are logged in");
				PlayerAPI.setAmmoAmount(player, "Rifle", 25);
				PlayerAPI.setAmmoAmount(player, "Rocket-Launcher", 1);
				PlayerDataYAML.setPlayerAmmoToFile(player);
			} else {
				if (memberOfTeam.equals("Blue")) {
					plugin.logger.info("Player's team is Blue");
					player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, "Blue"));
				} else if (memberOfTeam.equals("Red")) {
					plugin.logger.info("Player's team is Red");
					player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, "Red"));
				}
				int rifleAmmo = PlayerDataYAML.getPlayerAmmoFromFile(player, "Rifle");
				int rocketAmmo = PlayerDataYAML.getPlayerAmmoFromFile(player, "Rocket-Launcher");
				int shotgunAmmo = PlayerDataYAML.getPlayerAmmoFromFile(player, "Shotgun");
				int pistolAmmo = PlayerDataYAML.getPlayerAmmoFromFile(player, "Pistol");

				player.setMetadata("WorldWar.Ammo.Rifle", new FixedMetadataValue(plugin, rifleAmmo));
				player.setMetadata("WorldWar.Ammo.Rocket", new FixedMetadataValue(plugin, rocketAmmo));
				player.setMetadata("WorldWar.Ammo.Shotgun", new FixedMetadataValue(plugin, shotgunAmmo));
				player.setMetadata("WorldWar.Ammo.Pistol", new FixedMetadataValue(plugin, pistolAmmo));

				if (player.hasPermission("worldwar.notifyUpdate") && plugin.getNotifyUpdate()) {

				}
				if (!(SquadAPI.getSquadForPlayer(player.getUniqueId()).equals("Not in Squad"))) {
					if (player.getInventory().contains(new ItemStack(Material.COMPASS))) {
						for (ItemStack item : player.getInventory().getContents()) {
							if (item.getType() == Material.COMPASS) {
								if (item.hasItemMeta()) {
									if (item.getItemMeta().hasDisplayName()) {
										String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
										if (name.equals("Squad Objective Locator")) {
											Location objLoc = SquadAPI.getSquadObjective(SquadAPI.getSquadForPlayer(player.getUniqueId()));
											ItemMeta im = item.getItemMeta();
											im.setLore(null);
											item.setItemMeta(im);
											List<String> lore = new ArrayList<String>();
											lore.add("X: " + objLoc.getBlockX());
											lore.add("Y: " + objLoc.getBlockY());
											lore.add("Z: " + objLoc.getBlockZ());
											ItemMeta nim = item.getItemMeta();
											nim.setLore(lore);
											item.setItemMeta(nim);
											player.updateInventory();
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
