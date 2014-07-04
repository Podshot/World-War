package io.github.podshot.events.guis;

import io.github.podshot.WorldWar;
import io.github.podshot.gui.ClassChooser;
import io.github.podshot.handlers.PlayerClasses;
import io.github.podshot.internals.Internals;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class ClassSelectorGUI implements Listener {
	
	private WorldWar plugin = WorldWar.getInstance();
	
	@EventHandler
	public void onSelectClass(InventoryClickEvent evt) {
		if (Internals.isWarDeclared()) {
			Player player = (Player) evt.getWhoClicked();
			ItemStack clicked = evt.getCurrentItem();
			Inventory inv = evt.getInventory();
			
			if (inv.getName().equals(ClassChooser.getClassChooserGui().getName())) {
				String name = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());
				evt.setCancelled(true);
				switch (name) {
				case "Soldier":
					player.setMetadata("WorldWar.Class", new FixedMetadataValue(plugin, "Soldier"));
					break;
				case "Spy":
					player.setMetadata("WorldWar.Class", new FixedMetadataValue(plugin, "Spy"));
					break;
				case "Engineer":
					player.setMetadata("WorldWar.Class", new FixedMetadataValue(plugin, "Engineer"));
					break;
				case "Builder":
					player.setMetadata("WorldWar.Class", new FixedMetadataValue(plugin, "Builder"));
					break;
				default:
					break;
				}
				player.sendMessage(ChatColor.BLUE + PlayerClasses.valueOf(name).toString());
			}
		}
	}

}
