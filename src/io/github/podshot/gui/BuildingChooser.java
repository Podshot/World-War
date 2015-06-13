package io.github.podshot.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BuildingChooser {
	
	public static Inventory getBuildingChooserGui() {
		Inventory buildingChooser = Bukkit.createInventory(null, 9, ChatColor.DARK_RED + "Choose a Building");
		
		ItemStack wall = new ItemStack(Material.SMOOTH_BRICK);
		ItemMeta wallIM = wall.getItemMeta();
		wallIM.setDisplayName(ChatColor.GRAY + "Build a Wall");
		wall.setItemMeta(wallIM);
		buildingChooser.setItem(0, wall);
		
		return buildingChooser;
	}

}
