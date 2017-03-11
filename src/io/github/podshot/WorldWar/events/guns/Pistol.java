package io.github.podshot.WorldWar.events.guns;

import io.github.podshot.WorldWar.api.Bullet;
import io.github.podshot.WorldWar.api.GunType;
import io.github.podshot.WorldWar.api.PlayerAPI;
import io.github.podshot.WorldWar.api.WorldWarTeam;
import io.github.podshot.WorldWar.api.interfaces.IGun;
import io.github.podshot.WorldWar.handlers.WarHandler;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class Pistol implements IGun {

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
		if (gunIS.getType() == Material.MONSTER_EGG && gunIS.getDurability() == 61) {
			if (e.getPlayer().getLevel() > 0) {
				String gun = gunIS.getItemMeta().getDisplayName();
				if (gun.equals("Pistol")) {
					ItemProjectile bullet = Bullet.createRegularBullet("bullet-pistol", new ItemStack(Material.STONE_BUTTON), e.getPlayer(), 2.0F);
					int lvl = e.getPlayer().getLevel() - 1;
					float progress = lvl / this.getMagSize();
					e.getPlayer().setExp(progress);
					e.getPlayer().setLevel(lvl);
					e.setCancelled(true);
					PlayerAPI.setAmmoAmount(e.getPlayer(), GunType.PISTOL, lvl);
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
		if (gunIS.getType() == Material.MONSTER_EGG && gunIS.getDurability() == 61) {
			if (gunIS.getItemMeta().getDisplayName().equals("Pistol")) {
				e.getPlayer().setLevel(this.getMagSize());
				float progress = this.getMagSize() / this.getMagSize();
				PlayerAPI.setAmmoAmount(e.getPlayer(), GunType.PISTOL, this.getMagSize());
				e.getPlayer().setExp(progress);
			}
		}
	}

	public int getMagSize() {
		return 5;
	}

	public ItemStack getGun() {
		ItemStack gun = new ItemStack(Material.MONSTER_EGG);
		gun.setDurability((short) 61);
		ItemMeta gunIM = gun.getItemMeta();
		gunIM.setDisplayName("Pistol");
		gun.setItemMeta(gunIM);
		return gun;
	}

	@EventHandler
	public void onBulletHit(ItemProjectileHitEvent e) {
		WorldWarTeam shooterTeam = null;
		WorldWarTeam hitTeam = null;
		if (WarHandler.isWarDeclared()) {
			if (e.getHitType() == CustomProjectileHitEvent.HitType.ENTITY) {
				LivingEntity hitEntity = e.getHitEntity();
				if (hitEntity.getType() == EntityType.PLAYER) {
					Player hitPlayer = (Player) hitEntity;
					Player shooter = (Player) e.getProjectile().getShooter();
					shooterTeam = PlayerAPI.getTeam(shooter);
					hitTeam = PlayerAPI.getTeam(hitPlayer);
					if (!(hitTeam == shooterTeam)) {
						if (e.getProjectile().getProjectileName().equals("bullet-pistol")) {
							hitPlayer.damage(this.getPlayerDamage(), shooter);
						}
					}
					e.getProjectile().getEntity().remove();
				} else {
					hitEntity.damage(this.getAnimalDamage(), e.getProjectile().getShooter());
					e.getProjectile().getEntity().remove();
				}
			} else if (e.getHitType() == CustomProjectileHitEvent.HitType.BLOCK) {
				e.getProjectile().getEntity().remove();
			}
		}
		
	}

	@Override
	public double getPlayerDamage() {
		return 1.0D;
	}

	@Override
	public double getAnimalDamage() {
		return this.getPlayerDamage();
	}
	
	

}
