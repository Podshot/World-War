package io.github.podshot.WorldWar.events.guns;

import io.github.podshot.WorldWar.api.PlayerAPI;
import io.github.podshot.WorldWar.api.interfaces.IGun;
import io.github.podshot.WorldWar.handlers.WarHandler;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class SniperRifle implements IGun {

	@EventHandler
	public void onFireGun(PlayerInteractEvent e) {
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
		if (gunIS.getType() == Material.MONSTER_EGG || gunIS.getDurability() == 60) {
			if (e.getPlayer().getLevel() >= 0) {
				String gun = gunIS.getItemMeta().getDisplayName().toString();
				if (gun.equals("Sniper Rifle")) {
					ItemProjectile bullet = new ItemProjectile("bullet-sniper", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 2.5F);
					//bullet.setIgnoreSomeBlocks(true);
					//bullet.boundingBox.shrink(2D, 2D, 2D);

					int lvl = e.getPlayer().getLevel() - 1;
					e.getPlayer().setLevel(lvl);
					e.setCancelled(true);
				}
			}
		}
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
		if (gunIS.getType() == Material.MONSTER_EGG && gunIS.getDurability() == 52) {
			if (gunIS.getItemMeta().getDisplayName().equals("Sniper Rifle")) {
				e.getPlayer().setLevel(SniperRifle.getMagSize());
				float progress = SniperRifle.getMagSize() / SniperRifle.getMagSize();
				PlayerAPI.setAmmoAmount(e.getPlayer(), "Sniper Rifle", SniperRifle.getMagSize());
				e.getPlayer().setExp(progress);
			}
		}

	}

	public static int getMagSize() {
		return 5;
	}

	public static ItemStack getGun() {
		ItemStack sniperRifle = new ItemStack(Material.MONSTER_EGG);
		sniperRifle.setDurability((short) 60);
		ItemMeta sniperRifleIM = sniperRifle.getItemMeta();
		sniperRifleIM.setDisplayName("Sniper Rifle");
		sniperRifle.setItemMeta(sniperRifleIM);
		return sniperRifle;
	}

	@Override
	@EventHandler
	public void onBulletHit(ItemProjectileHitEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getPlayerDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAnimalDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

}
