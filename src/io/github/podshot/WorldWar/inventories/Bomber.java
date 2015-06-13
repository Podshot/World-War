package io.github.podshot.WorldWar.inventories;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Deprecated
public class Bomber {
	
	public static ArrayList<ItemStack> getBomber() {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		
		ItemStack bombs = new ItemStack(Material.SULPHUR);
		ItemMeta imBombs = bombs.getItemMeta();
		imBombs.setDisplayName("Drop Regular Bombs");
		bombs.setItemMeta(imBombs);
		items.add(bombs);
		
		ItemStack napalm = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta imNapalm = napalm.getItemMeta();
		imNapalm.setDisplayName("Drop Napalm Bombs");
		napalm.setItemMeta(imNapalm);
		items.add(napalm);
		
		ItemStack exit = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta imExit = exit.getItemMeta();
		imExit.setDisplayName("Exit Bomber");
		exit.setItemMeta(imExit);
		items.add(exit);
		return items;
	}

}
