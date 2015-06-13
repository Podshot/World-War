package io.github.podshot.WorldWar.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SquadInviteGUI {

	public static Inventory getSquadInviteGUI(String squadName) {
		String title = ChatColor.DARK_RED + "Invite to join Squad: " + squadName;
		Inventory gui = Bukkit.createInventory(null, 9, title);

		ItemStack accept = new ItemStack(Material.WOOL);
		accept.setDurability((short) 5);
		ItemMeta imAccept = accept.getItemMeta();
		imAccept.setDisplayName(ChatColor.GREEN + "Accept Squad Invite");
		accept.setItemMeta(imAccept);
		gui.setItem(0, accept);
		
		ItemStack reject = new ItemStack(Material.WOOL);
		reject.setDurability((short) 14);
		ItemMeta imReject = reject.getItemMeta();
		imReject.setDisplayName(ChatColor.RED + "Reject Squad Invite");
		reject.setItemMeta(imReject);
		gui.setItem(1, reject);

		return gui;	
	}

}
