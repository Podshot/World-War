package io.github.podshot.events.bullets;

import io.github.podshot.api.interfaces.BulletHit;
import io.github.podshot.internals.Internals;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;

public class RocketBullet implements Listener, BulletHit {

	@EventHandler
	public void onBulletHit(ItemProjectileHitEvent e) {
		if (Internals.isWarDeclared()) {
			if (e.getHitType() == CustomProjectileHitEvent.HitType.BLOCK) {
				if (e.getProjectile().getProjectileName().equals("bullet-rocket")) {
					Block hitBlock = e.getHitBlock();
					Location hitLoc = hitBlock.getLocation();
					hitBlock.getWorld().createExplosion(hitLoc, 4.0F);
				}
			}
		}
	}

}
