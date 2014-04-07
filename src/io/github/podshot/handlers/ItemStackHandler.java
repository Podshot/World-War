package io.github.podshot.handlers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackHandler {

	public static ItemStack getGunItemStack() {
		ItemStack gun = new ItemStack(Material.IRON_HOE);
		ItemMeta imGun = gun.getItemMeta();
		imGun.setDisplayName(ChatColor.DARK_GRAY + "Standard Issue Rifle");
		gun.setItemMeta(imGun);
		return gun;
	}

}
