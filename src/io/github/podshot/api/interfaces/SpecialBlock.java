package io.github.podshot.api.interfaces;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public interface SpecialBlock {
	
	@EventHandler
	public void onClickBlock(PlayerInteractEvent evt);

}
