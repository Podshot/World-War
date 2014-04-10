package io.github.podshot.entities;

import io.github.podshot.WorldWar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

public class DisguisePlayerAsVehicle {
	private static WorldWar plugin = WorldWar.getInstance();
	private static DisguiseCraftAPI dcAPI = plugin.dcAPI;

	public void addPlayerAsBat(Player player) {
		String name = player.getName();
		if (!dcAPI.isDisguised(player)) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "disguise Bat " + name);
		}
	}

	public void addPlayerAsBlaze(Player player) {
		String name = player.getName();
		if (!dcAPI.isDisguised(player)) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "disguise Blaze " + name);
		}
	}

	public void addPlayerAsDragon(Player player) {
		String name = player.getName();
		if (!dcAPI.isDisguised(player)) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "disguise EnderDragon " + name);
		}
	}

}
