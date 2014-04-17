package io.github.podshot.events;

import io.github.podshot.WorldWar;
import io.github.podshot.gui.ClassChooser;
import io.github.podshot.gui.TeamChooser;
import io.github.podshot.handlers.ClassHandler;

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

		if (inv.getName().equals(ClassChooser.getClassChooserGui().getName())) {
			if (clicked.getType() == Material.LEATHER_HELMET) {
				evt.setCancelled(true);
				player.closeInventory();
				player.setMetadata("WorldWar.Class", new FixedMetadataValue(plugin, "Soldier"));
				player.sendMessage("[" + player.getName() + "] Choose class: Soldier");
				ClassHandler.giveSoldierInventory(player);
			}
			if (clicked.getType() == Material.POTION) {
				evt.setCancelled(true);
				player.closeInventory();
				player.setMetadata("WorldWar.Class", new FixedMetadataValue(plugin, "Spy"));
				player.sendMessage("[" + player.getName() + "] Choose class: Spy");
				ClassHandler.giveSpyInventory(player);
			}
			if (clicked.getType() == Material.IRON_SPADE) {
				evt.setCancelled(true);
				player.closeInventory();
				player.setMetadata("WorldWar.Class", new FixedMetadataValue(plugin, "Engineer"));
				player.sendMessage("[" + player.getName() + "] Choose class: Engineer");
			}
			player.sendMessage("This is a debug message");
		}
		
		if (inv.getName().equals(TeamChooser.getTeamChooserGui().getName())) {
			if (clicked.getType() == Material.WOOL) {
				evt.setCancelled(true);
				player.closeInventory();
				if (clicked.getDurability() == (short) 11) {
					player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, "Blue"));
				} else if (clicked.getDurability() == (short) 14) {
					player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, "Red"));
				}
			}
		}
	}
}
