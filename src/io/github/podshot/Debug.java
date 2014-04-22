package io.github.podshot;

import io.github.podshot.handlers.ItemStackHandler;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Debug {

	public static void givePlayerRifle(Player p) {
		ItemStack gun = ItemStackHandler.getGunItemStack();
		p.getInventory().addItem(gun);
	}

	public static void givePlayerlauncher(Player sender) {
		ItemStack launcher = ItemStackHandler.getLauncherStack();
		sender.getInventory().addItem(launcher);
	}

}
