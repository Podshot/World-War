package io.github.podshot.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClassChooser {
	
	public Inventory getClassChooserGui() {
		Inventory classChooser = Bukkit.createInventory(null, 9, ChatColor.DARK_RED + "Choose a Fighting Class");
		
		ItemStack soldier = new ItemStack(Material.IRON_HELMET);
		ItemMeta imSoldier = soldier.getItemMeta();
		imSoldier.setDisplayName(ChatColor.GRAY + "Soldier");
		return classChooser;
	}

}
