package io.github.podshot.WorldWar.squads;

import io.github.podshot.WorldWar.api.SquadAPI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SquadObjective {
	
	@SuppressWarnings("deprecation")
	public SquadObjective(String squadName, Location objLoc) {
		SquadAPI.setSquadObjective(squadName, objLoc);
		for (Player member : SquadAPI.getMembers(squadName)) {
			member.setCompassTarget(objLoc);
			ItemStack gps = new ItemStack(Material.COMPASS);
			ItemMeta gpsIM = gps.getItemMeta();
			gpsIM.setDisplayName(ChatColor.GREEN + "Squad Objective Locator");
			List<String> lore = new ArrayList<String>();
			lore.add("X: " + objLoc.getBlockX());
			lore.add("Y: " + objLoc.getBlockY());
			lore.add("Z: " + objLoc.getBlockZ());
			gpsIM.setLore(lore);
			gps.setItemMeta(gpsIM);
			
			member.getInventory().addItem(gps);
			member.updateInventory();
		}
	}
}
