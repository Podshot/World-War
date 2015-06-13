package io.github.podshot.api.interfaces;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;

public interface IGrenade {
	
	/**
	 * Called whenever a player throw a grenade
	 * @param e The argument that holds all the data related to the event
	 */
	@EventHandler
	public void onThrowGrenade(PlayerInteractEvent e);
	
	/**
	 * Called when a Grenade lands on a Entity of Block
	 * @param e The argument that holds all the data related to the event
	 */
	@EventHandler
	public void onGrenadeLand(ItemProjectileHitEvent e);

}
