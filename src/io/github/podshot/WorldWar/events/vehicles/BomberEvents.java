package io.github.podshot.WorldWar.events.vehicles;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.internals.ConfigInternals;
import io.github.podshot.WorldWar.internals.Internals;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BomberEvents implements Listener {
	
	private WorldWar plugin = WorldWar.getInstance();

	@EventHandler
	public void onDropRegularBombs(PlayerInteractEvent e) {
		if (Internals.isWarDeclared()) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR) {
				ItemStack used = e.getItem();
				if (used.getType() == Material.SULPHUR && used.getItemMeta().getDisplayName().equals("Drop Regular Bombs")) {
					Location loc = e.getPlayer().getLocation();
					TNTPrimed entity = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
					int highestBlock = loc.getWorld().getHighestBlockYAt(loc);
					int playerYLoc = (int) loc.getY();
					int remaining = playerYLoc - highestBlock;
					int result = remaining * 20;
					entity.setFuseTicks(result);
				}
			}
		}
		return;
	}

	@EventHandler
	public void onDropNapalmBombs(PlayerInteractEvent e) {
		if (Internals.isWarDeclared()) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR) {
				ItemStack used = e.getItem();
				if (used.getType() == Material.BLAZE_POWDER && used.getItemMeta().getDisplayName().equals("Drop Napalm Bombs")) {
					Location loc = e.getPlayer().getLocation();
					TNTPrimed entity = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
					entity.setIsIncendiary(true);
					int highestBlock = loc.getWorld().getHighestBlockYAt(loc);
					int playerYLoc = (int) loc.getY();
					int remaining = playerYLoc - highestBlock;
					int result = remaining * 20;
					entity.setFuseTicks(result);
				}
			}
		}
		return;
	}

	@EventHandler
	public void onPlayerLeaveBomber(PlayerInteractEvent e) {
		if (Internals.isWarDeclared()) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR) {
				if (ConfigInternals.getDPAHBOE()) {
					ItemStack used = e.getItem();
					if (used.getType() == Material.REDSTONE_BLOCK && used.getItemMeta().getDisplayName().equals("Exit Bomber")) {
						Location loc = e.getPlayer().getLocation();
						int landing = loc.getWorld().getHighestBlockYAt(loc);
						Player player = e.getPlayer();
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 4));
						int result = landing + 5;
						player.teleport(new Location(loc.getWorld(), loc.getX(), result, loc.getZ()));
					}
				} else {
					ItemStack used = e.getItem();
					if (used.getType() == Material.REDSTONE_BLOCK && used.getItemMeta().getDisplayName().equals("Exit Bomber")) {
						Player player = e.getPlayer();
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000, 4));
						player.setFlying(false);
					}
				}
			}
		}
		return;
	}
}