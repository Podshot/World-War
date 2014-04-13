package io.github.podshot.handlers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackHandler {

	public static ItemStack getGunItemStack() {
		ItemStack gun = new ItemStack(Material.IRON_HOE);
		ItemMeta imGun = gun.getItemMeta();
		// imGun.setDisplayName(ChatColor.DARK_GRAY + "Standard Issue Rifle");
		imGun.setDisplayName("Standard Issue Rifle");
		gun.setItemMeta(imGun);
		return gun;
	}

	public static ItemStack getPistolitemStack() {
		ItemStack pistol = new ItemStack(Material.STONE_HOE);
		ItemMeta imPistol = pistol.getItemMeta();
		
		imPistol.setDisplayName("Pistol");
		pistol.setItemMeta(imPistol);
		return pistol;
	}
	
	public static ItemStack getBombExplosiveItemStack() {
		ItemStack explosive = new ItemStack(Material.WOOD_BUTTON);
		ItemMeta imExplosive = explosive.getItemMeta();
		
		imExplosive.setDisplayName("Explosive Charge");
		explosive.setItemMeta(imExplosive);
		return explosive;
	}
}
