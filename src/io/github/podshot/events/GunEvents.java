package io.github.podshot.events;

import io.github.podshot.handlers.ItemStackHandler;
import io.github.podshot.internals.Internals;

import org.bukkit.Effect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.CustomProjectile;
import com.stirante.MoreProjectiles.projectile.OrbProjectile;

public class GunEvents implements Listener {
	
	private ItemStack gunItemStack = ItemStackHandler.getGunItemStack();

	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR)) {
			return;
		}
		
		if (!(e.getItem() == gunItemStack )) {
			return;
		}
		
		CustomProjectile p = new OrbProjectile("bullet", e.getPlayer(), 1.0F);
		p.getEntity().getWorld().playEffect(e.getPlayer().getLocation(), Effect.SMOKE, 10);
	}
	
	public void onHit(CustomProjectileHitEvent e) {
		if (e.getHitType() == CustomProjectileHitEvent.HitType.ENTITY) {
			e.getHitEntity().damage(3.0D, e.getProjectile().getShooter());
			Player shooter = (Player) e.getProjectile().getShooter();
			if (Internals.playersTeamFile.containsKey(shooter.getName())) {
				if (Internals.playersTeamFile.get(shooter.getName()) == "Blue") {
					if (e.getHitEntity().getType() == EntityType.PLAYER) {
						Player hitP = (Player) e.getHitEntity();
						if (Internals.playersTeamFile.containsKey(hitP.getName())) {
							if (Internals.playersTeamFile.get(hitP.getName()) == "Red") {
								hitP.damage(3.0D, shooter);
							}
						}
					}
				} else if (Internals.playersTeamFile.get(shooter.getName()) == "Red") {
					if (e.getHitEntity().getType() == EntityType.PLAYER) {
						Player hitP = (Player) e.getHitEntity();
						if (Internals.playersTeamFile.containsKey(hitP.getName())) {
							if (Internals.playersTeamFile.get(hitP.getName()) == "Blue") {
								hitP.damage(3.0D, shooter);
							}
						}
					}
				}
					
			}
		}
	}

}
