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
import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;
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
		if (e.getAction() == Action.RIGHT_CLICK_AIR) {


			if (e.getItem().getItemMeta().getDisplayName() == "Standard Issue Rifle") {
				//Bukkit.getLogger().warning("Test Event Message");
				//Arrow a = e.getPlayer().launchProjectile(Arrow.class);
				ItemProjectile ip = new ItemProjectile("bullet-rifle", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 3.0F);
				// TODO: Add this => e.getItem().setDurability((short)1);
			} else if (e.getItem() == pistolItemStack ) {
				ItemProjectile ip = new ItemProjectile("bullet-pistol", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 3.0F);
			}
		}
	}

	@EventHandler
	public void onHit(ItemProjectileHitEvent e) {
		if (Internals.warDeclared) {
			if (e.getHitType() == CustomProjectileHitEvent.HitType.ENTITY) {
				e.getHitEntity().damage(3.0D, e.getProjectile().getShooter());
				Player shooter = (Player) e.getProjectile().getShooter();
				if (Internals.playersTeamFile.get(shooter.getName()) == "Blue") {
					if (e.getHitEntity().getType() == EntityType.PLAYER) {
						Player hitP = (Player) e.getHitEntity();
						if (Internals.playersTeamFile.containsKey(hitP.getName())) {
							if (Internals.playersTeamFile.get(hitP.getName()) == "Red") {
								if (e.getProjectile().getProjectileName() == "bullet-rifle") {
									hitP.damage(3.0D, shooter);
								} else if (e.getProjectile().getProjectileName() == "bullet-pistol") {
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
