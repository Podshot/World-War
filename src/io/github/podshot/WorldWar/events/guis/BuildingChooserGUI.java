package io.github.podshot.WorldWar.events.guis;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.handlers.PlayerHandler;
import io.github.podshot.WorldWar.internals.Internals;
import io.github.podshot.WorldWar.structures.Wall;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BuildingChooserGUI implements Listener {

	@EventHandler
	public void onSelectChoice(InventoryClickEvent evt) {

		if (!(Internals.isWarDeclared())) {
			return;
		}

		Player player = (Player) evt.getWhoClicked();
		ItemStack itemClicked = evt.getCurrentItem();
		Inventory inv = evt.getInventory();
		String invName = ChatColor.stripColor(inv.getName());
		if (invName.equals("Choose a Building")) {
			evt.setCancelled(true);
			player.closeInventory();
			if (PlayerHandler.BuildingGUIHandler.isPlayerInList(player.getUniqueId())) {
				PlayerHandler.BuildingGUIHandler.removePlayer(player.getUniqueId());
			}
			switch (itemClicked.getType()) {
			case SMOOTH_BRICK:
				WorldWar.getInstance().logger.info("Clicked Smooth Brick");
				//if (PlayerHandler.BuildingHandler.hasLocationInMap(player.getUniqueId())) {
					WorldWar.getInstance().logger.info("Building Wall...");
					Location wallLoc = PlayerHandler.BuildingHandler.getLocation(player.getUniqueId());
					new Wall(wallLoc);
					WorldWar.getInstance().logger.info("Built Wall");
				//}
				break;
			default:
				break;
			}
		}
	}

	@EventHandler
	public void onCloseInventory(InventoryCloseEvent evt) {

		if (!(Internals.isWarDeclared())) {
			return;
		}

		Player player = (Player) evt.getPlayer();
		Inventory inv = evt.getInventory();
		String invName = ChatColor.stripColor(inv.getName());
		if (invName.equals("Choose a Building")) {
			if (PlayerHandler.BuildingGUIHandler.isPlayerInList(player.getUniqueId())) {
				PlayerHandler.BuildingGUIHandler.removePlayer(player.getUniqueId());
			}
			if (PlayerHandler.BuildingHandler.hasLocationInMap(player.getUniqueId())) {
				Location loc = PlayerHandler.BuildingHandler.getLocation(player.getUniqueId());
				loc.getBlock().setType(Material.AIR);
				PlayerHandler.BuildingHandler.removeLocation(player.getUniqueId());
			}
			player.setGameMode(GameMode.SURVIVAL);
		}
	}


}
