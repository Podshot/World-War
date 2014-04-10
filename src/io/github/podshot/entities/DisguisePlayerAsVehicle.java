package io.github.podshot.entities;

import org.bukkit.entity.Player;

import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;
import pgDev.bukkit.DisguiseCraft.disguise.Disguise;
import pgDev.bukkit.DisguiseCraft.disguise.DisguiseType;
import io.github.podshot.WorldWar;

public class DisguisePlayerAsVehicle {
	private WorldWar plugin = WorldWar.getInstance();
	private DisguiseCraftAPI dcAPI = plugin.dcAPI;

	public void addPlayerAsBat(Player player) {
		String name = player.getName();
		if (!dcAPI.isDisguised(player)) {
			new Disguise(dcAPI.newEntityID(), name, DisguiseType.Bat);
		}
	}

}
