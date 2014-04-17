package io.github.podshot.events;

import io.github.podshot.WorldWar;
import io.github.podshot.internals.Internals;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class BlockEvents implements Listener {

	private static WorldWar plugin = WorldWar.getInstance();

	@Deprecated
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent evt) {
		//if (!Internals.warDeclared) {
		//return;
		//}

		Block placed = evt.getBlock();
		Player placer = evt.getPlayer();
		if (placed.getType() == Material.WOOD_BUTTON) {
			String team = Internals.playersTeamFile.getProperty(placer.getName());
			placed.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, team));
			Internals.explosiveLocations.add(new Location(evt.getBlock().getWorld(), evt.getBlock().getX(), evt.getBlock().getY(), evt.getBlock().getZ()));
		}
		return;
	}

	@EventHandler
	public  void onBlockBreak(BlockBreakEvent evt) {
		if (Internals.warDeclared) {
			if (evt.getBlock().getType() == Material.WOOL) {
				String teamFlag = null;
				String teamCapturer = null;
				Block wool = evt.getBlock();
				Player capturer = evt.getPlayer();
				for (MetadataValue val : wool.getMetadata("WorldWar.TeamFlag")) {
					if (val.getOwningPlugin().getName().equals("WorldWar")) {
						teamFlag = val.asString();
					}
				}
				for (MetadataValue data : capturer.getMetadata("WorldWar.Team")) {
					if (data.getOwningPlugin().getName().equals("WorldWar")) {
						teamCapturer = data.asString();
					}
				}

				if (teamFlag != null || teamCapturer != null) {
					if (teamFlag != teamCapturer) {
						plugin.getServer().broadcastMessage("Team: " + ChatColor.GOLD + teamCapturer + ChatColor.RESET + " has captured the " + ChatColor.GOLD + teamFlag + ChatColor.RESET + " Flag!");
						plugin.getServer().broadcastMessage("The flag was captured by " + ChatColor.GOLD + capturer.getName());
					}
				}
			}
		}
		return;
	}

}
