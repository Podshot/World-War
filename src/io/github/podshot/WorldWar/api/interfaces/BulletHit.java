package io.github.podshot.WorldWar.api.interfaces;

import org.bukkit.event.EventHandler;

import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;

@Deprecated
public interface BulletHit {
	
	/**
	 * Called whenever a ItemProjectile hits an Entity or Block
	 * @param e The argument that holds all the data related to the event
	 */
	@EventHandler
	public void onBulletHit(ItemProjectileHitEvent e);

}
