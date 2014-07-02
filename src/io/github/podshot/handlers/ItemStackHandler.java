package io.github.podshot.handlers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Deprecated
public class ItemStackHandler {

	public static ItemStack getGunItemStack() {
		ItemStack gun = new ItemStack(Material.MONSTER_EGG);
		gun.setDurability((short) 51);
		ItemMeta imGun = gun.getItemMeta();
		// imGun.setDisplayName(ChatColor.DARK_GRAY + "Standard Issue Rifle");
		imGun.setDisplayName("Standard Issue Rifle");
		gun.setItemMeta(imGun);
		return gun;
	}

	public static ItemStack getPistolItemStack() {
		ItemStack pistol = new ItemStack(Material.STONE_HOE);
		ItemMeta imPistol = pistol.getItemMeta();
		
		imPistol.setDisplayName("Pistol");
		pistol.setItemMeta(imPistol);
		return pistol;
	}

	public static ItemStack getLauncherStack() {
		ItemStack launcher = new ItemStack(Material.MONSTER_EGG);
		launcher.setDurability((short) 50);
		ItemMeta imLauncher = launcher.getItemMeta();
		
		imLauncher.setDisplayName("Rocket Launcher");
		launcher.setItemMeta(imLauncher);
		return launcher;
	}
	
	public static ItemStack getBombDiffuser() {
		ItemStack bombD = new ItemStack(Material.SHEARS);
		ItemMeta imBombD = bombD.getItemMeta();
		imBombD.setDisplayName("Bomb Diffuser");
		bombD.setItemMeta(imBombD);
		return bombD;
	}
}
