package io.github.podshot.events.bullets;

import io.github.podshot.api.PlayerAPI;
import io.github.podshot.api.interfaces.BulletHit;
import io.github.podshot.internals.Internals;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;

@Deprecated
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
					shooterTeam = PlayerAPI.getTeam(shooter);
					hitTeam = PlayerAPI.getTeam(hitPlayer);
					if (!(hitTeam.equals(shooterTeam))) {
						if (e.getProjectile().getProjectileName().equals("bullet-rifle")) {
							hitPlayer.damage(2.0D, shooter);								
						}
					}
					e.getProjectile().getEntity().remove();
				} else {
					hitEntity.damage(2.0D, e.getProjectile().getShooter());
					e.getProjectile().getEntity().remove();
				}
			} else if (e.getHitType() == CustomProjectileHitEvent.HitType.BLOCK) {
				e.getProjectile().getEntity().remove();
			}
		}
		return;
	}
}
