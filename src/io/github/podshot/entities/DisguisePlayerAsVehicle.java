package io.github.podshot.entities;

import io.github.podshot.WorldWar;

import org.bukkit.entity.Player;
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

	public static void addPlayerAsDragon(Player player) {
		if (!dcAPI.isDisguised(player)) {
			dcAPI.disguisePlayer(player, new Disguise(dcAPI.newEntityID(), DisguiseType.EnderDragon));
			player.setAllowFlight(true);
			player.setFlying(true);
			player.setFlySpeed(0.02F);
			player.setHealth(30.0D);
			player.getEyeLocation().getDirection();
			player.setMetadata("WorldWar.Vehicle.Type", new FixedMetadataValue(plugin, "Bomber"));
		}
	}
}
