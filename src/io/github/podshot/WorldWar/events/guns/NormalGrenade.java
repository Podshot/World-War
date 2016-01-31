package io.github.podshot.WorldWar.events.guns;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.interfaces.IGrenade;
import io.github.podshot.WorldWar.internals.Internals;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.stirante.MoreProjectiles.TypedRunnable;
import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class NormalGrenade implements Listener, IGrenade {
	
	private WorldWar plugin = WorldWar.getInstance();

	@Override
	@EventHandler
	public void onThrowGrenade(PlayerInteractEvent e) {
		if (!(Internals.isWarDeclared())) {
			return;
		}
		
		if (!(e.getAction() == Action.LEFT_CLICK_AIR)) {
			return;
		}
		
		if (e.getItem() == null) {
			return;
		}
		
		if (!(e.getItem().hasItemMeta())) {
			return;
		}
		
		ItemStack grenade = e.getItem();
		if (grenade.getType() == Material.FIREBALL) {
			if (grenade.getItemMeta().hasDisplayName()) {
				if (grenade.getItemMeta().getDisplayName().equals("Normal Grenade")) {
					ItemProjectile grenadeProjectile = new ItemProjectile("normal-grenade", e.getPlayer(), new ItemStack(Material.STONE), 0);
					//grenadeProjectile.boundingBox.shrink(2D, 2D, 2D);
					grenadeProjectile.addTypedRunnable(new TypedRunnable<ItemProjectile>() {
						@Override
						public void run(ItemProjectile o) {
							//Particles.LARGE_SMOKE.display(o.getEntity().getLocation(), 0, 0, 0, 0, 1);
							for (Entity nearby : o.getEntity().getNearbyEntities(7.0D, 7.0D, 7.0D)) {
								if (nearby.getType() == EntityType.PLAYER) {
									Player player = (Player) nearby;
									player.playSound(o.getEntity().getLocation(), Sound.FUSE, 2F, 0F);
								}
							}
						}
					});
				}
			}
		}

	}

	@Override
	@EventHandler
	public void onGrenadeLand(final ItemProjectileHitEvent e) {
		if (!(Internals.isWarDeclared())) {
			return;
		}
		
		if (e.getHitType() == CustomProjectileHitEvent.HitType.BLOCK) {
			if (e.getProjectile().getProjectileName().equals("normal-grenade")) {
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					@Override
					public void run() {
						e.getProjectile().getEntity().getWorld().createExplosion(e.getProjectile().getEntity().getLocation(), 5F);
						e.getProjectile().getEntity().remove();
					}				
				}, 140);
			}
		}
	}
	
	public static ItemStack getGrenade() {
		ItemStack grenade = new ItemStack(Material.FIREBALL);
		ItemMeta grenadeIM = grenade.getItemMeta();
		grenadeIM.setDisplayName("Normal Grenade");
		grenade.setItemMeta(grenadeIM);
		return grenade;
	}

}
