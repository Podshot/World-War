package io.github.podshot.WorldWar.events.structures;

import io.github.podshot.WorldWar.handlers.WarHandler;
import me.astramg.resources.BlockGenerator;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GuardTower {

	@EventHandler
	public void onPlaceStructure(BlockPlaceEvent evt) {
		if (!(WarHandler.isWarDeclared())) {
			return;
		}
		
		if (!(evt.getBlockPlaced().getType() == Material.SPONGE)) {
			return;
		}

	}

	@EventHandler
	public void onPickupStructure(PlayerInteractEvent evt) {
		// TODO Auto-generated method stub

	}

	public BlockGenerator getBlocksArray(Material mat) {
		// TODO Auto-generated method stub
		return null;
	}

}
