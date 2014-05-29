package io.github.podshot.api.interfaces;

import org.bukkit.event.EventHandler;

import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;

public interface BulletHit {
	
	@EventHandler
	public void onBulletHit(ItemProjectileHitEvent e);

}
