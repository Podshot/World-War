package io.github.podshot.events.bullets;

import io.github.podshot.api.interfaces.BulletHit;
import io.github.podshot.internals.Internals;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.MetadataValue;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;

public class RifleBullet implements BulletHit, Listener {

	@EventHandler
	public void onBulletHit(ItemProjectileHitEvent e) {
		String shooterTeam = null;
		String hitTeam = null;
		if (Internals.isWarDeclared()) {
			if (e.getHitType() == CustomProjectileHitEvent.HitType.ENTITY) {
				LivingEntity hitEntity = e.getHitEntity();
				if (hitEntity.getType() == EntityType.PLAYER) {
					Player hitPlayer = (Player) hitEntity;
					Player shooter = (Player) e.getProjectile().getShooter();
					for (MetadataValue value : shooter.getMetadata("WorldWar.Team")) {
						if (value.getOwningPlugin().getName().equals("WorldWar")) {
							shooterTeam = value.asString();
						}
					}
					for (MetadataValue value : hitPlayer.getMetadata("WorldWar.Team")) {
						if (value.getOwningPlugin().getName().equals("WorldWar.Team")) {
							hitTeam = value.asString();
						}
					}
					if (shooterTeam.equals("Blue")) {
						if (hitTeam.equals("Red")) {
							if (e.getProjectile().getProjectileName().equals("bullet-rifle")) {
								hitPlayer.damage(2.0D, shooter);								
							}
						}
					} else if (shooterTeam.equals("Red")) {
						if (hitTeam.equals("Blue")) {
							if (e.getProjectile().getProjectileName().equals("bullet-rifle")) {
								hitPlayer.damage(2.0D, shooter);
							}
						}
					}
				} else {
					hitEntity.damage(2.0D, e.getProjectile().getShooter());
				}
			}
		}
		return;
	}
}
