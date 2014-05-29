package io.github.podshot.api.interfaces;

import me.astramg.resources.BlockGenerator;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface Structure {
	
	@EventHandler
	public void onPlaceStructure(BlockPlaceEvent evt);
	
	@EventHandler
	public void onPickupStructure(PlayerInteractEvent evt);
	
	public BlockGenerator getBlocksArray();

}
