package io.github.podshot.WorldWar.events.blocks;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.interfaces.ISpecialBlock;
import io.github.podshot.WorldWar.gui.ClassChooser;
import io.github.podshot.WorldWar.handlers.WarHandler;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class ClassChanger implements Listener, ISpecialBlock {
	
	private WorldWar plugin = WorldWar.getInstance();

	@EventHandler
	public void onClickBlock(PlayerInteractEvent evt) {
		boolean isClassChanger = false;
		if (WarHandler.isWarDeclared()) {
			if (evt.getAction() == Action.LEFT_CLICK_BLOCK) {
				Block clickedBlock = evt.getClickedBlock();
				if (clickedBlock.getType() == Material.COMMAND) {
					evt.getPlayer().closeInventory();
					for (MetadataValue val : clickedBlock.getMetadata("WorldWar.isClassChanger")) {
						if (val.getOwningPlugin().getName().equals("WorldWar")) {
							isClassChanger = val.asBoolean();
						}
					}
					if (isClassChanger) {
						evt.getPlayer().openInventory(ClassChooser.getClassChooserGui());
					}
				}
			}
		}
	}

	@Override
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent evt) {
		if (!(WarHandler.isWarDeclared())) {
			return;
		}
		
		if (evt.getBlock().getType() != Material.COMMAND) {
			return;
		}
		
		evt.getBlock().setMetadata("WorldWar.isClassChanger", new FixedMetadataValue(plugin, true));
	}

	@Override
	@EventHandler
	public void onBreakBlock(BlockBreakEvent evt) {
		return;
	}

}
