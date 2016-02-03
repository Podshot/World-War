package io.github.podshot.WorldWar.events.guns;

import io.github.podshot.WorldWar.api.Bullet;
import io.github.podshot.WorldWar.api.Sounds;
import io.github.podshot.WorldWar.api.interfaces.IGun;
import io.github.podshot.WorldWar.handlers.WarHandler;

import java.util.Arrays;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class RocketLauncher implements IGun {

	@EventHandler
	public void onFireGun(final PlayerInteractEvent e) {
		
		if (!(WarHandler.isWarDeclared())) {
			return;
		}

		if (!(e.getAction() == Action.RIGHT_CLICK_AIR)) {
			return;
		}

		if (e.getItem() == null) {
			return;
		}

		if (!(e.getItem().hasItemMeta())) {
			return;
		}

		ItemStack gunIS = e.getItem();
		if (gunIS.getType() == Material.MONSTER_EGG && gunIS.getDurability() == 50) {
			if (e.getPlayer().getLevel() > 0) {
				String gun = gunIS.getItemMeta().getDisplayName().toString();
				if (gun.equals("Rocket Launcher")) {
					/*
					ItemProjectile bullet = new ItemProjectile("bullet-rocket", e.getPlayer(), new ItemStack(Material.STONE), 2.5F);
					bullet.setIgnoreSomeBlocks(true);
					bullet.boundingBox.shrink(2D, 2D, 2D);
					bullet.addTypedRunnable(new TypedRunnable<ItemProjectile>() {
						@Override
						public void run(ItemProjectile o) {
							Particles.FLAME.display(o.getEntity().getLocation(), 0, 0, 0, 0, 1);
							Particles.LARGE_SMOKE.display(o.getEntity().getLocation(), 0, 0, 0, 0, 1);
						}
					});
					*/
					ItemProjectile bullet = Bullet.CreateRegularBullet("bullet-rocket", new ItemStack(Material.STONE), e.getPlayer(), 2.5F, Arrays.asList(Effect.FLAME, Effect.LARGE_SMOKE));
					//e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.FIREWORK_LAUNCH, 1, 0.1f);
					Sounds.PlayRocketSound(e.getPlayer());
					int lvl = e.getPlayer().getLevel() - 1;
					e.getPlayer().setLevel(lvl);
					e.setCancelled(true);
				}
			}
		}
		return;
	}

	@EventHandler
	public void onGunReload(PlayerInteractEvent e) {
		
		if (!(WarHandler.isWarDeclared())) {
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

		ItemStack gunIS = e.getItem();
		if (gunIS.getType().equals(Material.MONSTER_EGG) && gunIS.getDurability() == 50) {
			if (gunIS.getItemMeta().getDisplayName().toString().equals("Rocket Launcher")) {
				e.getPlayer().setLevel(RocketLauncher.getMagSize());
			}
		}
	}

	public static int getMagSize() {
		return 1;
	}

	public static ItemStack getGun() {
		ItemStack launcher = new ItemStack(Material.MONSTER_EGG);
		launcher.setDurability((short) 50);
		ItemMeta launcherIM = launcher.getItemMeta();
		launcherIM.setDisplayName("Rocket Launcher");
		launcher.setItemMeta(launcherIM);
		return launcher;
	}

	@EventHandler
	public void onBulletHit(ItemProjectileHitEvent e) {
		if (WarHandler.isWarDeclared()) {
			if (e.getHitType() == CustomProjectileHitEvent.HitType.BLOCK) {
				if (e.getProjectile().getProjectileName().equals("bullet-rocket")) {
					Block hitBlock = e.getHitBlock();
					Location hitLoc = hitBlock.getLocation();
					hitBlock.getWorld().createExplosion(hitLoc, 4.0F);
					e.getProjectile().getEntity().remove();
				}
			}
		}
	}

	@Override
	public double getPlayerDamage() {
		return 0;
	}

	@Override
	public double getAnimalDamage() {
		return 0;
	}
}
