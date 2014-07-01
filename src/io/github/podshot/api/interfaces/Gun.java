package io.github.podshot.api.interfaces;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public interface Gun {
	
	@EventHandler
	public void onFireGun(PlayerInteractEvent e);
	
	@EventHandler
	public void onGunReload(PlayerInteractEvent e);
	
	int getMagSize();

}
