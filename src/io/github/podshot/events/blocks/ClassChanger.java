package io.github.podshot.events.blocks;

import io.github.podshot.api.interfaces.SpecialBlock;
import io.github.podshot.gui.ClassChooser;
import io.github.podshot.internals.Internals;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

public class ClassChanger implements Listener, SpecialBlock {

	@EventHandler
	public void onClickBlock(PlayerInteractEvent evt) {
		boolean isClassChanger = false;
		if (Internals.isWarDeclared()) {
			if (evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Block clickedBlock = evt.getClickedBlock();
				if (clickedBlock.getType() == Material.COMMAND) {
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

}
