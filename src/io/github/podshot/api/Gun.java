package io.github.podshot.api;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public interface Gun {
	
	@EventHandler
	public void onFireGun(PlayerInteractEvent e);
	
	@EventHandler
	public void onGunReload(PlayerInteractEvent e);

}
