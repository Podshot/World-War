package io.github.podshot.WorldWar.api.interfaces;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;

public interface IGun extends Listener {
	
	/**
	 * Called whenever a Player right clicks
	 * @param e The argument that holds all the data related to the event
	 */
	@EventHandler
	public void onFireGun(PlayerInteractEvent e);
	
	/**
	 * Called whenever a player left clicks
	 * @param e
	 */
	@EventHandler
	public void onGunReload(PlayerInteractEvent e);
	
	/**
	 * Gets the amount of ammo the gun should have before not firing
	 * @return An Integer of how many bullets the player can shoot
	 */
	int getMagSize();
	
	double getPlayerDamage();
	
	double getAnimalDamage();
	
	@EventHandler
	public void onBulletHit(ItemProjectileHitEvent e);

}
