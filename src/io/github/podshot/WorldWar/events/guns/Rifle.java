package io.github.podshot.WorldWar.events.guns;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.Bullet;
import io.github.podshot.WorldWar.api.PlayerAPI;
import io.github.podshot.WorldWar.api.interfaces.IGun;
import io.github.podshot.WorldWar.handlers.PlayerHandler;
import io.github.podshot.WorldWar.internals.Internals;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import com.stirante.MoreProjectiles.projectile.OrbProjectile;

public class Rifle implements IGun {
	
	private WorldWar plugin = WorldWar.getInstance();

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
		
		if (PlayerHandler.RifleReloadHandler.isInList(e.getPlayer().getUniqueId())) {
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
					//OrbProjectile bullet = new OrbProjectile("bullet-rifle", e.getPlayer(), 2.0f);
					e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.FIREWORK_BLAST, 0.5f, 0.6f);
					int lvl = e.getPlayer().getLevel() - 1;
					float progress = (float) lvl/Rifle.getMagSize();
					this.plugin.logger.info("Progress: " + progress);
					e.getPlayer().setExp(progress);
					e.getPlayer().setLevel(lvl);
					e.setCancelled(true);
					PlayerAPI.setAmmoAmount(e.getPlayer(), "Rifle", lvl);
					final UUID uuid = e.getPlayer().getUniqueId();
					PlayerHandler.RifleReloadHandler.addToList(uuid);
					Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {

						@Override
						public void run() {
							PlayerHandler.RifleReloadHandler.removeFromList(uuid);
						}
						
					}, 20L);
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
				e.getPlayer().setLevel(Rifle.getMagSize());
				float progress = Rifle.getMagSize() / Rifle.getMagSize();
				PlayerAPI.setAmmoAmount(e.getPlayer(), "Rifle", Rifle.getMagSize());
				e.getPlayer().setExp(progress);
			}
		}
	}

	public static int getMagSize() {
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
		return;
		
	}

	@Override
	public double getPlayerDamage() {
		return 2.0D;
	}

	@Override
	public double getAnimalDamage() {
		return this.getPlayerDamage();
	}
}
