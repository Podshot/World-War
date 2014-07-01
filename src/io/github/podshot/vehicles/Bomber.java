package io.github.podshot.vehicles;

import io.github.podshot.WorldWar;
import io.github.podshot.api.Refactor;
import io.github.podshot.api.VehicleAPI;
import io.github.podshot.api.interfaces.Vehicle;
import io.github.podshot.internals.ConfigInternals;
import io.github.podshot.internals.Internals;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import org.bukkit.Bukkit;
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
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;
import pgDev.bukkit.DisguiseCraft.disguise.Disguise;
import pgDev.bukkit.DisguiseCraft.disguise.DisguiseType;

public class Bomber implements Listener, Vehicle {

	private WorldWar plugin = WorldWar.getInstance();
	private DisguiseCraftAPI dcAPI = plugin.getDCAPI();

	@EventHandler
	public void onPilotEnterVehicle(NPCRightClickEvent e) {
		if (!(Internals.isWarDeclared())) {
			return;
		}
		
		if (VehicleAPI.inVehicle(e.getClicker())) {
			return;
		}
		
		if (dcAPI.isDisguised(e.getClicker())) {
			return;
		}
		
		NPC npcClickedOn = e.getNPC();
		if (npcClickedOn.getName().equals(this.getNPCTemplate().getName())) {
			if (npcClickedOn.getEntity().hasMetadata("WorldWar.isVehicleNPC")) {
				Player player = e.getClicker();
				dcAPI.disguisePlayer(player, new Disguise(dcAPI.newEntityID(), DisguiseType.EnderDragon));
				player.setAllowFlight(true);
				VehicleAPI.setVehicle(player, "Bomber");
				final Location newSpawnLoc = npcClickedOn.getEntity().getLocation();
				npcClickedOn.destroy();
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					@Override
					public void run() {
						respawnVehicle(newSpawnLoc);
					}	
				}, 1800);
			}
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

	@Override
	public NPC getNPCTemplate() {
		NPCRegistry npcr = CitizensAPI.getNPCRegistry();
		NPC bomber = npcr.createNPC(EntityType.ENDER_DRAGON, "Bomber");
		return bomber;
	}

	@Override
	public void respawnVehicle(Location loc) {
		NPC bomber = this.getNPCTemplate();
		bomber.spawn(loc);
		bomber.getNavigator().setTarget(loc);
		bomber.getEntity().setMetadata("WorldWar.isVehicleNPC", new FixedMetadataValue(plugin, true));
	}
}
