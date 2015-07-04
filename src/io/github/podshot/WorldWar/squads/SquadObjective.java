package io.github.podshot.WorldWar.squads;

import io.github.podshot.WorldWar.api.SquadAPI_OLD;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SquadObjective {
	
	//private Location location;
	private int[][] structure = { {0,-1,0}, {-1,-1,0}, {-1,-1,-1}, {0,-1,-1}, {1,-1,-1}, {1,-1,0}, {1,-1,0}, {1,-1,1}, {0,-1,1}, {-1,-1,1}};
	
	@SuppressWarnings("deprecation")
	public SquadObjective(Location loc, List<UUID> members, World world) {
		//this.location = loc;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (members.contains(player.getUniqueId())) {
				for (int i = 0; i < (structure.length); i++) {
					player.sendBlockChange(new Location(world, loc.getBlockX() + structure[i][0], loc.getBlockY() + structure[i][1], loc.getBlockZ() + structure[i][2]), Material.IRON_BLOCK, (byte) 0);
				}
				player.sendBlockChange(loc, Material.BEACON, (byte) 0);
			}
		}
	}
	
	@Deprecated
	public SquadObjective(String squadName, Location objLoc) {
		SquadAPI_OLD.setSquadObjective(squadName, objLoc);
		for (Player member : SquadAPI_OLD.getMembers(squadName)) {
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
