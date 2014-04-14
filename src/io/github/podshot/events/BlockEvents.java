package io.github.podshot.events;

import io.github.podshot.WorldWar;
import io.github.podshot.internals.Internals;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockEvents implements Listener {
	
	private static WorldWar plugin = WorldWar.getInstance();

	@EventHandler
	public static void onBlockPlace(BlockPlaceEvent evt) {
		//if (!Internals.warDeclared) {
			//return;
		//}
		
		Block placed = evt.getBlock();
		Player placer = evt.getPlayer();
		if (placed.getType() == Material.WOOD_BUTTON) {
			String team = Internals.playersTeamFile.getProperty(placer.getName());
			placed.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, team));
			Internals.explosiveLocations.add(new Location(evt.getBlock().getWorld(), evt.getBlock().getX(), evt.getBlock().getY(), evt.getBlock().getZ()));
		} else {
			return;
		}
	}

}
