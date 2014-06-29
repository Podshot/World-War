package io.github.podshot.vehicles;

import io.github.podshot.WorldWar;
import io.github.podshot.api.Refactor;
import io.github.podshot.api.VehicleAPI;
import io.github.podshot.api.interfaces.Vehicle;
import io.github.podshot.internals.ConfigInternals;
import io.github.podshot.internals.Internals;
import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Bomber implements Listener, Vehicle {

	private WorldWar plugin = WorldWar.getInstance();

	@EventHandler
	public void onPilotEnterVehicle(NPCRightClickEvent e) {
		if (!(Internals.isWarDeclared())) {
			return;
		}
		
		if (VehicleAPI.inVehicle(e.getClicker())) {
			return;
		}

	}

	@EventHandler
	public void onPlayerGetInSeat(PlayerInteractEntityEvent e) {
		// TODO Auto-generated method stub

	}

	public int getSeatCount() {
		return 0;
	}

	public ItemStack[] getInventory() {
		return null;
	}

	@Refactor
	@EventHandler
	public void onDropRegularBombs(PlayerInteractEvent e) {
		if (!(Internals.isWarDeclared())) {
			return;
		}
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR)) {
			return;
		}
		if (e.getItem() == null) {
			return;
		}
		ItemStack used = e.getItem();
		if (used.getType() == Material.SULPHUR) {
			if (used.hasItemMeta()) {
				if (used.getItemMeta().hasDisplayName()) {
					if (used.getItemMeta().getDisplayName().equals("Drop Regular Bombs")) {
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
		}
		return;
	}

	@EventHandler
	public void onDropNapalmBombs(PlayerInteractEvent e) {
		if (!(Internals.isWarDeclared())) {
			return;
		}
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR)) {
			return;
		}
		if (e.getItem() == null) {
			return;
		}
		ItemStack used = e.getItem();
		if (used.getType() == Material.BLAZE_POWDER) {
			if (used.hasItemMeta()) {
				if (used.getItemMeta().hasDisplayName()) {
					if (used.getItemMeta().getDisplayName().equals("Drop Napalm Bombs")) {
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
		}
		return;
	}

	@EventHandler
	public void onPlayerLeaveBomber(PlayerInteractEvent e) {
		if (!(Internals.isWarDeclared())) {
			return;
		}
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR)) {
			return;
		}
		if (e.getItem() == null) {
			return;
		}
		ItemStack used = e.getItem();
		if (used.getType() == Material.REDSTONE_BLOCK) {
			if (used.hasItemMeta()) {
				if (used.getItemMeta().hasDisplayName()) {
					if (used.getItemMeta().getDisplayName().equals("Exit Bomber")) {
						if (ConfigInternals.getDPAHBOE()) {
							Location loc = e.getPlayer().getLocation();
							int landing = loc.getWorld().getHighestBlockYAt(loc);
							Player player = e.getPlayer();
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 4));
							int result = landing + 5;
							player.teleport(new Location(loc.getWorld(), loc.getX(), result, loc.getZ()));
						} else {
							Player player = e.getPlayer();
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000, 4));
							player.setFlying(false);
							this.plugin.getDCAPI().undisguisePlayer(player);
						}
					}
				}
			}
		}
	}
}
