package io.github.podshot.entities;

import org.bukkit.entity.Player;

import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;
import pgDev.bukkit.DisguiseCraft.disguise.Disguise;
import pgDev.bukkit.DisguiseCraft.disguise.DisguiseType;
import io.github.podshot.WorldWar;

public class DisguisePlayerAsVehicle {
	private static WorldWar plugin = WorldWar.getInstance();
	private static DisguiseCraftAPI dcAPI = plugin.dcAPI;

	public void addPlayerAsBat(Player player) {
		String name = player.getName();
		if (!dcAPI.isDisguised(player)) {
			new Disguise(dcAPI.newEntityID(), name, DisguiseType.Bat);
		}
	}
	
	public void addPlayerAsBlaze(Player player) {
		String name = player.getName();
		if (!dcAPI.isDisguised(player)) {
			new Disguise(dcAPI.newEntityID(), name, DisguiseType.Blaze);
		}
	}

	public void addPlayerAsDragon(Player player) {
		String name = player.getName();
		if (!dcAPI.isDisguised(player)) {
			new Disguise(dcAPI.newEntityID(), name, DisguiseType.EnderDragon);
		}
	}

}
