package io.github.podshot.WorldWar.events;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.GunType;
import io.github.podshot.WorldWar.api.PlayerAPI;
import io.github.podshot.WorldWar.api.SquadAPI;
import io.github.podshot.WorldWar.api.WorldWarTeam;
import io.github.podshot.WorldWar.files.PlayerDataYAML;
import io.github.podshot.WorldWar.gui.TeamChooser;
import io.github.podshot.WorldWar.handlers.WarHandler;
import io.github.podshot.WorldWar.players.PlayerSorter;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class PlayerEvents implements Listener {

	private static WorldWar plugin = WorldWar.getInstance();

	@EventHandler
	public void onPlayerJoinEvent(final PlayerJoinEvent e) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			private PlayerJoinEvent evt = e;
			@Override
			public void run() {
				if (WarHandler.isWarDeclared()) {
					Player player = evt.getPlayer();
					String memberOfTeam = PlayerDataYAML.getPlayerTeam(player);
					plugin.logger.info("Checking to see if username is already stored");
					if (memberOfTeam == null) {
						plugin.logger.info("Name is not present in the player file");
						player.openInventory(TeamChooser.getTeamChooserGui());
						evt.getPlayer().sendMessage("You are logged in");
						PlayerAPI.setAmmoAmount(player, GunType.RIFLE, 25);
						PlayerAPI.setAmmoAmount(player, GunType.ROCKET_LAUNCHER, 1);
						PlayerDataYAML.setPlayerAmmoToFile(player.getUniqueId());
					} else {
						if (memberOfTeam.equals("Blue")) {
							plugin.logger.info("Player's team is Blue");
							PlayerAPI.setTeam(player, WorldWarTeam.BLUE);
							PlayerSorter.addPlayer(player.getUniqueId(), WorldWarTeam.BLUE);
						} else if (memberOfTeam.equals("Red")) {
							plugin.logger.info("Player's team is Red");
							PlayerAPI.setTeam(player, WorldWarTeam.RED);
						}

						if (SquadAPI.inSquad(player.getUniqueId()))
							plugin.logger.info(SquadAPI.getSquadForPlayer(player.getUniqueId()).getSquadName());
						//plugin.logger.info(SquadAPI.getSquadForPlayer(player.getUniqueId()).getSquadName() + ":" + SquadAPI.isLeader(player.getUniqueId()));
						/*
						if (!(SquadAPI_OLD.getSquadForPlayer(player.getUniqueId()).equals("Not in Squad"))) {
							if (player.getInventory().contains(new ItemStack(Material.COMPASS))) {
								for (ItemStack item : player.getInventory().getContents()) {
									if (item.getType() == Material.COMPASS) {
										if (item.hasItemMeta()) {
											if (item.getItemMeta().hasDisplayName()) {
												String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
												if (name.equals("Squad Objective Locator")) {
													Location objLoc = SquadAPI_OLD.getSquadObjective(SquadAPI_OLD.getSquadForPlayer(player.getUniqueId()));
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
						 */
					}
				}
			}
		}, 40L);
		return;
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent evt) {
		if (WarHandler.isWarDeclared()) {
			Player player = evt.getPlayer();
			WorldWarTeam team = PlayerAPI.getTeam(player);
			plugin.logger.info(team.toString());
			plugin.logger.info("Saving player");
			if (team != null) {
				plugin.logger.info("Team is not \"null\" saving data");
				PlayerDataYAML.setPlayerToTeam(player, team);
				PlayerDataYAML.setPlayerAmmoToFile(player.getUniqueId());
			}
		}
		return;
	}

	@EventHandler
	public void onDeEquipArmor(InventoryClickEvent evt) {
		if (!(WarHandler.isWarDeclared())) {
			return;
		}

		if (!(evt.getSlotType() == SlotType.ARMOR)) {
			return;
		}

		Player player = (Player) evt.getWhoClicked();
		if (evt.getSlot() == 39) {
			ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
			LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
			meta.spigot().setUnbreakable(true);
			meta.setColor(Color.BLUE);
			helmet.setItemMeta(meta);		
			player.getEquipment().setHelmet(helmet);
			evt.setCancelled(true);
		}
	}

	/*
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent evt) {
		if (WarHandler.isWarDeclared()) {
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
	 */
}
