package io.github.podshot.handlers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackHandler {

	public static ItemStack getGunItemStack() {
		ItemStack gun = new ItemStack(Material.IRON_HOE);
		ItemMeta imGun = gun.getItemMeta();
		//imGun.setDisplayName(ChatColor.DARK_GRAY + "Standard Issue Rifle");
		imGun.setDisplayName("Standard Issue Rifle");
		gun.setItemMeta(imGun);
		return gun;
	}

	public static ItemStack getPistolitemStack() {
		// TODO Auto-generated method stub
		return null;
	}

}
