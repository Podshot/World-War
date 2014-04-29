package io.github.podshot.entities;

import io.github.podshot.WorldWar;
import io.github.podshot.vehicleInventories.Bomber;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;
import pgDev.bukkit.DisguiseCraft.disguise.Disguise;
import pgDev.bukkit.DisguiseCraft.disguise.DisguiseType;

public class DisguisePlayerAsVehicle {
	private static WorldWar plugin = WorldWar.getInstance();
	private static DisguiseCraftAPI dcAPI = plugin.dcAPI;

	public static void addPlayerAsBat(Player player) {
		if (!dcAPI.isDisguised(player)) {
			dcAPI.disguisePlayer(player, new Disguise(dcAPI.newEntityID(), DisguiseType.Bat));
		}
	}

	public static void addPlayerAsBlaze(Player player) {
		if (!dcAPI.isDisguised(player)) {
			dcAPI.disguisePlayer(player, new Disguise(dcAPI.newEntityID(), DisguiseType.Blaze));
		}
	}

	@SuppressWarnings("deprecation")
	public static void addPlayerAsDragon(Player player) {
		if (!dcAPI.isDisguised(player)) {
			dcAPI.disguisePlayer(player, new Disguise(dcAPI.newEntityID(), DisguiseType.EnderDragon));
			player.setAllowFlight(true);
			player.setFlying(true);
			player.setFlySpeed(0.02F);
			player.setHealth(20.0D);
			player.getEyeLocation();
			player.setMetadata("WorldWar.Vehicle.Type", new FixedMetadataValue(plugin, "Bomber"));
			player.getInventory().clear();
			player.updateInventory();
			int slotNum = 0;
			for (ItemStack item : Bomber.getBomber()) {
				player.getInventory().setItem(slotNum, item);
				slotNum = slotNum + 1;
			}

		}
	}
	
	public static void addPlayerAsCow(Player p) {
		if (!dcAPI.isDisguised(p)) {
			dcAPI.disguisePlayer(p, new Disguise(dcAPI.newEntityID(), DisguiseType.Cow));
			p.setMetadata("WorldWar.Vehicle.Type", new FixedMetadataValue(plugin, "Truck"));
		}
	}
}
