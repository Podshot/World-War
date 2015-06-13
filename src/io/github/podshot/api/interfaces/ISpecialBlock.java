package io.github.podshot.api.interfaces;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ISpecialBlock extends Listener {
	
	@EventHandler
	public void onClickBlock(PlayerInteractEvent evt);
	
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent evt);
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent evt);
	
	
}
