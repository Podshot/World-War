package io.github.podshot.WorldWar.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamChooser {
	
	public static Inventory getTeamChooserGui() {
		Inventory gui = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Select a Team");
		
		ItemStack red = new ItemStack(Material.WOOL);
		red.setDurability((short) 14);
		ItemMeta imRed = red.getItemMeta();
		imRed.setDisplayName(ChatColor.RED + "Join Red Team");
		red.setItemMeta(imRed);
		gui.setItem(0, red);
		
		ItemStack blue = new ItemStack(Material.WOOL);
		blue.setDurability((short) 11);
		ItemMeta imBlue = blue.getItemMeta();
		imBlue.setDisplayName(ChatColor.BLUE + "Join Blue Team");
		blue.setItemMeta(imBlue);
		gui.setItem(1, blue);
		
		return gui;
	}

}
