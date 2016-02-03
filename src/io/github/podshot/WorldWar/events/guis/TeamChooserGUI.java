package io.github.podshot.WorldWar.events.guis;

import io.github.podshot.WorldWar.api.PlayerAPI;
import io.github.podshot.WorldWar.gui.TeamChooser;
import io.github.podshot.WorldWar.handlers.WarHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class TeamChooserGUI implements Listener {

	@EventHandler
	public void closedInventory(InventoryCloseEvent evt) {
		if (!WarHandler.isWarDeclared()) {
			return;
		}
		
		Inventory inventory = evt.getInventory();
		if (!ChatColor.stripColor(inventory.getName()).equals("Select a Team")) {
			return;
		}
		Player player = (Player) evt.getPlayer();
		String team = PlayerAPI.getTeam(player);
		if (team.equals("Blue") || team.equals("Red")) {
			return;
		} else {
			evt.getPlayer().openInventory(TeamChooser.getTeamChooserGui());
		}
	}
	
	@EventHandler
	public void clickedInventory(InventoryClickEvent evt) {
		if (!WarHandler.isWarDeclared()) {
			return;
		}
		
		Inventory inventory = evt.getInventory();
		if (!ChatColor.stripColor(inventory.getName()).equals("Select a Team")) {
			return;
		}
	}
}
