package io.github.podshot.WorldWar.events.guis;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.PlayerAPI;
import io.github.podshot.WorldWar.api.WorldWarTeam;
import io.github.podshot.WorldWar.gui.TeamChooser;
import io.github.podshot.WorldWar.handlers.WarHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

public class TeamChooserGUI implements Listener {
	
	private static WorldWar plugin = WorldWar.getInstance();

	//@EventHandler
	public void closedInventory(InventoryCloseEvent evt) {
		if (!WarHandler.isWarDeclared()) {
			return;
		}
		
		Inventory inventory = evt.getInventory();
		if (!ChatColor.stripColor(inventory.getName()).equals("Select a Team")) {
			return;
		}
		Player player = (Player) evt.getPlayer();
		WorldWarTeam team = PlayerAPI.getTeam(player);
		if (team == WorldWarTeam.BLUE || team == WorldWarTeam.RED) {
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
		
		final Player player = (Player) evt.getWhoClicked();
		ItemStack clicked = evt.getCurrentItem();
		if (clicked.getDurability() == ((short) 14)) {
			PlayerAPI.setTeam(player, WorldWarTeam.RED);
		} else if (clicked.getDurability() == ((short) 11)){
			PlayerAPI.setTeam(player, WorldWarTeam.BLUE);
		}
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.runTask(plugin, new Runnable() {

			@Override
			public void run() {
				player.closeInventory();
			}
			
		});
	}
}
