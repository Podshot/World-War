package io.github.podshot.events;

import io.github.podshot.handlers.ItemStackHandler;
import io.github.podshot.internals.Internals;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.stirante.MoreProjectiles.TypedRunnable;
import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.CustomProjectile;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;
import com.stirante.MoreProjectiles.projectile.OrbProjectile;

@SuppressWarnings("unused")
public class GunEvents implements Listener {

	private ItemStack pistolItemStack = ItemStackHandler.getPistolitemStack();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {

		//if (Internals.warDeclared) {
		//return;
		//}
		if (e.getAction() != Action.RIGHT_CLICK_AIR) {
			return;
		}

		if (e.getItem().getItemMeta().getDisplayName() == "Standard Issue Rifle") {
			Bukkit.getLogger().warning("Test Event Message");
			Arrow a = e.getPlayer().launchProjectile(Arrow.class);
			ItemProjectile ip = new ItemProjectile("item", e.getPlayer(), new ItemStack(Material.APPLE), 1.0F);
			a.getWorld().playEffect(a.getLocation().getDirection().toLocation(a.getWorld()), Effect.SMOKE, 10);
			e.setCancelled(true);
		} else if (e.getItem() == pistolItemStack ) {
			//CustomProjectile p = new OrbProjectile("bulletpistol", e.getPlayer(), 1.0F);
			//p.getEntity().getWorld().playEffect(e.getPlayer.getLocation(), Effect.SMOKE, 10);
		}
	}

	public void onHit(CustomProjectileHitEvent e) {
		if (Internals.warDeclared) {
			if (e.getHitType() == CustomProjectileHitEvent.HitType.ENTITY) {
				e.getHitEntity().damage(3.0D, e.getProjectile().getShooter());
				Player shooter = (Player) e.getProjectile().getShooter();
				if (Internals.playersTeamFile.get(shooter.getName()) == "Blue") {
					if (e.getHitEntity().getType() == EntityType.PLAYER) {
						Player hitP = (Player) e.getHitEntity();
						if (Internals.playersTeamFile.containsKey(hitP.getName())) {
							if (Internals.playersTeamFile.get(hitP.getName()) == "Red") {
								if (e.getProjectile().getProjectileName() == "bulletrifle") {
									hitP.damage(3.0D, shooter);
								} else if (e.getProjectile().getProjectileName() == "bulletpistol") {
									hitP.damage(1.5D, shooter);
								}
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
