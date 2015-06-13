package io.github.podshot.WorldWar.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WireGui {

	public static Inventory getWireGui() {
		int row1 = 0;
		int row2 = 9;
		int row3 = 18;
		Inventory wireGui = Bukkit.createInventory(null, 27, "Cut a Wire");
		while (row1 <= 8) {
			ItemStack wire1 = new ItemStack(Material.WOOL);
			wire1.setDurability((short) 14);
			ItemMeta imWire1 = wire1.getItemMeta();
			imWire1.setDisplayName("Red Wire");
			wire1.setItemMeta(imWire1);
			wireGui.setItem(row1, wire1);
			row1 = row1 + 1;
		}
		while (row2 <= 17) {
			ItemStack wire2 = new ItemStack(Material.WOOL);
			wire2.setDurability((short) 5);
			ItemMeta imWire2 = wire2.getItemMeta();
			imWire2.setDisplayName("Green Wire");
			wire2.setItemMeta(imWire2);
			wireGui.setItem(row2, wire2);
			row2 = row2 + 1;
		}
		while (row3 <= 26) {
			ItemStack wire3 = new ItemStack(Material.WOOL);
			wire3.setDurability((short) 15);
			ItemMeta imWire3 = wire3.getItemMeta();
			imWire3.setDisplayName("Red Wire");
			wire3.setItemMeta(imWire3);
			wireGui.setItem(row3, wire3);
			row3 = row3 + 1;
		}
		return wireGui;
	}
}
