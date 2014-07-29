package io.github.podshot.events.guns;

import io.github.podshot.api.Bullet;
import io.github.podshot.api.PlayerAPI;
import io.github.podshot.api.interfaces.Gun;
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
import org.bukkit.inventory.meta.ItemMeta;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;

public class Rifle implements Listener, Gun {

	@EventHandler
	public void onFireGun(PlayerInteractEvent e) {

		if (!(Internals.isWarDeclared())) {
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
		if (gunIS.getType() == Material.MONSTER_EGG && gunIS.getDurability() == 51) {
			if (e.getPlayer().getLevel() > 0) {
				String gun = gunIS.getItemMeta().getDisplayName().toString();
				if (gun.equals("Standard Issue Rifle")) {
					/*
					ItemProjectile bullet = new ItemProjectile("bullet-rifle", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 2.0F);
					bullet.setIgnoreSomeBlocks(true);
					bullet.boundingBox.shrink(2D, 2D, 2D);
					*/
					new Bullet("bullet-rifle", new ItemStack(Material.STONE_BUTTON), e.getPlayer(), 2.0F);
					int lvl = e.getPlayer().getLevel() - 1;
					float progress = lvl / this.getMagSize();
					e.getPlayer().setExp(progress);
					e.getPlayer().setLevel(lvl);
					e.setCancelled(true);
					PlayerAPI.setAmmoAmount(e.getPlayer(), "Rifle", lvl);
				}
			}
		}
		return;
	}

	@EventHandler
	public void onGunReload(PlayerInteractEvent e) {

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

		ItemStack gunIS = e.getItem();
		if (gunIS.getType() == Material.MONSTER_EGG && gunIS.getDurability() == 51) {
			if (gunIS.getItemMeta().getDisplayName().equals("Standard Issue Rifle")) {
				e.getPlayer().setLevel(this.getMagSize());
				float progress = this.getMagSize() / this.getMagSize();
				PlayerAPI.setAmmoAmount(e.getPlayer(), "Rifle", this.getMagSize());
				e.getPlayer().setExp(progress);
			}
		}
	}

	@Override
	public int getMagSize() {
		return 25;
	}

	public static ItemStack getGun() {
		ItemStack gun = new ItemStack(Material.MONSTER_EGG);
		gun.setDurability((short) 51);
		ItemMeta gunIM = gun.getItemMeta();
		gunIM.setDisplayName("Standard Issue Rifle");
		gun.setItemMeta(gunIM);
		return gun;
	}

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
