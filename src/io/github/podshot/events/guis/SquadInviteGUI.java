package io.github.podshot.events.guis;

import io.github.podshot.internals.Internals;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SquadInviteGUI implements Listener {
	
	@SuppressWarnings("unused")
	@EventHandler
	public void onSelectChoice(InventoryClickEvent evt) {
		if (Internals.isWarDeclared()) {
			Player player = (Player) evt.getWhoClicked();
			ItemStack itemClicked = evt.getCurrentItem();
			Inventory inv = evt.getInventory();
			String squadSplitPhase1 = ChatColor.stripColor(inv.getName());
			if (squadSplitPhase1.startsWith("Invite to join Squad: ")) {
				evt.setCancelled(true);
				player.closeInventory();
				String squadSplitPhase2 = squadSplitPhase1.replace("Invite to join Squad: ", "");
				player.sendMessage(squadSplitPhase2);
			}
			
		}
	}

}
