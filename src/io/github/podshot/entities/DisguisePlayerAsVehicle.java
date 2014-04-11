package io.github.podshot.entities;

import io.github.podshot.WorldWar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

public class DisguisePlayerAsVehicle {
	private static WorldWar plugin = WorldWar.getInstance();
	private static DisguiseCraftAPI dcAPI = plugin.dcAPI;

	public static void addPlayerAsBat(Player player) {
		String name = player.getName();
		if (!dcAPI.isDisguised(player)) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "disguise " + name + " Bat");
		}
	}

	public static void addPlayerAsBlaze(Player player) {
		String name = player.getName();
		if (!dcAPI.isDisguised(player)) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "disguise " + name + " Blaze");
		}
	}

	public static void addPlayerAsDragon(Player player) {
		String name = player.getName();
		if (!dcAPI.isDisguised(player)) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "disguise " + name + " EnderDragon");
			player.setAllowFlight(true);
			player.setFlying(true);
			player.setFlySpeed(0.02F);
		}
	}

}
