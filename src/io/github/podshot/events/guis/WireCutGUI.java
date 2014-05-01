package io.github.podshot.events.guis;

import io.github.podshot.gui.WireGui;
import io.github.podshot.internals.Internals;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

public class WireCutGUI implements Listener {
	
	@EventHandler
	public void onWireCut(InventoryClickEvent e) {
		boolean chooseRightWire = false;
		String coord = null;
		if (Internals.isWarDeclared()) {
			Player player = (Player) e.getWhoClicked();
			ItemStack clicked = e.getCurrentItem();
			Inventory inv = e.getInventory();
			
			if (inv.getName().equals(WireGui.getWireGui().getName())) {
				if (clicked.getType() == Material.WOOL) {
					e.setCancelled(true);
					player.closeInventory();
					Random random = new Random();
					int ranWireNum = random.nextInt((3 - 1) + 1);
					short wireData = clicked.getDurability();
					switch (ranWireNum) {
					case 1:
						if (wireData == (short) 14) {
							chooseRightWire = true;
						}
						break;
					case 2:
						if (wireData == (short) 5) {
							chooseRightWire = true;
						}
						break;
					case 3:
						if (wireData == (short) 15) {
							chooseRightWire = true;
						}
						break;
					default:
						break;
					}
					for (MetadataValue value : player.getMetadata("WorldWar.bombToDiffuse")) {
						if (value.getOwningPlugin().getName().equals("WorldWar")) {
							coord = value.asString();
						}
					}
					String[] coords = coord.split(":");
					String world = coords[0].toString();
					String x = coords[1].toString();
					String y = coords[2].toString();
					String z = coords[3].toString();
					
					Location loc = new Location(Bukkit.getWorld(world), Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
					
					if (chooseRightWire) {
						loc.getBlock().setType(Material.AIR);
					} else {
						if (!(loc.getWorld().getChunkAt(loc).isLoaded())) {
							loc.getWorld().getChunkAt(loc).load(true);
						}
						loc.getWorld().createExplosion(loc, 1.75F);
					}
					return;					
				}
				return;
			}
			return;
		}
		return;
	}

}
