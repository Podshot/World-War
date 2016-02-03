package io.github.podshot.WorldWar.events.structures;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.interfaces.IStructure;
import io.github.podshot.WorldWar.handlers.WarHandler;
import me.astramg.resources.BlockGenerator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

@Deprecated
public class Wall implements IStructure {
	
	private WorldWar plugin = WorldWar.getInstance();

	@SuppressWarnings("unused")
	@EventHandler
	public void onPlaceStructure(final BlockPlaceEvent evt) {
		String warClass = null;
		if (WarHandler.isWarDeclared()) {
			if (evt.getBlockPlaced().getType() == Material.SPONGE) {
				Player placer = evt.getPlayer();
				for (MetadataValue val : placer.getMetadata("WorldWar.Class")) {
					if (val.getOwningPlugin().getName().equals("WorldWar")) {
						warClass = val.asString();
					}
				}
				//if (warClass.equals("Builder")) {
					BlockGenerator phase1 = getBlocksArray(Material.COBBLESTONE);
					phase1.generateWithTime(WorldWar.getInstance(), evt.getBlockPlaced().getLocation().add(-3, 0, 0), 200L, true);
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
						@Override
						public void run() {
							BlockGenerator phase2 = getBlocksArray(Material.SMOOTH_BRICK);
							phase2.generateWithTime(plugin, evt.getBlockPlaced().getLocation().add(-3, 0, 0), 150L, true);
						}
					}, 300L);
				//} else {
					//evt.setCancelled(true);
				//}
			}
		}
	}

	@EventHandler
	public void onPickupStructure(PlayerInteractEvent evt) {
		String warClass = null;
		if (WarHandler.isWarDeclared()) {
			if (evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (evt.getClickedBlock().getType() == Material.SPONGE) {
					for (MetadataValue val : evt.getPlayer().getMetadata("WorldWar.Class")) {
						if (val.getOwningPlugin().getName().equals("WorldWar")) {
							warClass = val.asString();
						}
					}
					if (warClass.equals("Builder")) {
						BlockGenerator destroy = this.getBlocksArray(Material.AIR);
						destroy.generate(evt.getClickedBlock().getLocation().add(-3, 0, 0), true);
					} else {
						evt.setCancelled(true);
					}
				}
			}
		}
	}

	public BlockGenerator getBlocksArray(Material mat) {
		BlockGenerator structure = new BlockGenerator(
				new BlockGenerator.BlockLayer(";GGGGGGG").setBlockType('G', mat),
				new BlockGenerator.BlockLayer(";GGGGGGG").setBlockType('G', mat),
				new BlockGenerator.BlockLayer(";GGGGGGG").setBlockType('G', mat),
				new BlockGenerator.BlockLayer(";GGGGGGG").setBlockType('G', mat));
		return structure;
	}
}
