package io.github.podshot.events;

import io.github.podshot.handlers.ItemStackHandler;
import io.github.podshot.internals.Internals;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class GunEvents implements Listener {

	@SuppressWarnings("unused")
	private ItemStack pistolItemStack = ItemStackHandler.getPistolItemStack();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {

		// if (Internals.warDeclared) {
		// return;
		// }

		if (e.getAction() == Action.LEFT_CLICK_AIR) {
			if (e.getItem().getType() == Material.IRON_HOE) {
				String gunName = e.getItem().getItemMeta().getDisplayName().toString();
				e.getPlayer().sendMessage("Item Name: " + gunName);
				if (gunName.equals("Standard Issue Rifle")) {
					e.getItem().setDurability((short) 0);
				}
				e.setCancelled(true);
			}
		}
		if (e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (e.getItem().getType() == Material.IRON_HOE) {
				String gunType = e.getItem().getItemMeta().getDisplayName().toString();
				e.setCancelled(true);

				switch(gunType) {
				case "Standard Issue Rifle":
					if (e.getItem().getDurability() <= 249) {
						ItemProjectile rBullet = new ItemProjectile("bullet-rifle", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 2.0F);
						rBullet.setIgnoreSomeBlocks(true);
						e.getItem().setDurability((short) (e.getItem().getDurability() + 10));
					}
					break;
				case "Pistol":
					@SuppressWarnings("unused")
					ItemProjectile pBullet = new ItemProjectile("bullet-pistol", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 3.0F);
					e.getItem().setDurability((short) (e.getItem().getDurability() - (short) 1));
					break;
				}
			}
		}
	}

	@EventHandler
	public void onHit(ItemProjectileHitEvent e) {
		LivingEntity hitE = e.getHitEntity();
		if (Internals.warDeclared) {
			if (e.getHitType() == CustomProjectileHitEvent.HitType.ENTITY) {
				// e.getHitEntity().damage(3.0D, e.getProjectile().getShooter());
				Player shooter = (Player) e.getProjectile().getShooter();
				if (e.getHitEntity().getType() == EntityType.PLAYER) {
					if (Internals.playersTeamFile.get(shooter.getName()) == "Blue") {
						Player hitP = (Player) hitE;
						if (Internals.playersTeamFile.containsKey(hitP.getName())) {
							if (Internals.playersTeamFile.get(hitP.getName()) == "Red") {
								if (e.getProjectile().getProjectileName() == "bullet-rifle") {
									hitP.damage(4.0D, shooter);
								} else if (e.getProjectile().getProjectileName() == "bullet-pistol") {
									hitP.damage(2.0D, shooter);
								}
							}
						}
					} else if (Internals.playersTeamFile.get(shooter.getName()) == "Red") {
						Player hitP = (Player) hitE;
						if (Internals.playersTeamFile.containsKey(hitP.getName())) {
							if (Internals.playersTeamFile.get(hitP.getName()) == "Blue") {
								if (e.getProjectile().getProjectileName() == "bullet-rifle") {
									hitP.damage(2.0D, shooter);
								} else if (e.getProjectile().getProjectileName() == "bullet-pistol") {
									hitP.damage(2.0D, shooter);
								}
							}
						}
					}
				} else {
					hitE.damage(2.0D, shooter);
				}
			}
		} else {
			return;
		}
	}
}
