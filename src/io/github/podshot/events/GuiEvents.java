package io.github.podshot.events;

import io.github.podshot.WorldWar;
import io.github.podshot.gui.ClassChooser;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class GuiEvents implements Listener {
	private WorldWar plugin = WorldWar.getInstance();
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent evt) {
		Player player = (Player) evt.getWhoClicked();
		ItemStack clicked = evt.getCurrentItem();
		Inventory inv = evt.getInventory();
		
		if(inv.getName().equals(ClassChooser.getClassChooserGui().getName())) {
			if(clicked == ClassChooser.getClassChooserGui().getItem(0) && clicked.getType() == Material.LEATHER_HELMET) {
				evt.setCancelled(true);
				player.closeInventory();
				player.setMetadata("WorldWar.Class", new FixedMetadataValue(plugin, "Soldier"));
			}
		}
	}
}
