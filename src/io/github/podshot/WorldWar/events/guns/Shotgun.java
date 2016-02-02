package io.github.podshot.WorldWar.events.guns;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.PlayerAPI;
import io.github.podshot.WorldWar.api.interfaces.IGun;
import io.github.podshot.WorldWar.handlers.PlayerHandler;
import io.github.podshot.WorldWar.internals.Internals;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class Shotgun implements IGun {
	
	private WorldWar plugin = WorldWar.getInstance();

	@Override
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
		
		if (PlayerHandler.ShotgunReloadHandler.isInList(e.getPlayer().getUniqueId())) {
			return;
		}

		ItemStack gunIS = e.getItem();
		if (gunIS.getType() == Material.MONSTER_EGG && gunIS.getDurability() == 52) {
			if (e.getPlayer().getLevel() > 0) {
				String gun = gunIS.getItemMeta().getDisplayName().toString();
				if (gun.equals("Shotgun")) {

					ItemProjectile bullet1 = new ItemProjectile("bullet-shotgun", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 2.0F);
					//bullet1.setIgnoreSomeBlocks(true);
					//bullet1.boundingBox.shrink(2D, 2D, 2D);
					Vector b1v = bullet1.getEntity().getVelocity();
					Vector b1vn = b1v.add(new Vector(0.25f, 0f, 0.25f));
					bullet1.getEntity().setVelocity(b1vn);

					ItemProjectile bullet2 = new ItemProjectile("bullet-shotgun", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 2.0F);
					//bullet2.setIgnoreSomeBlocks(true);
					//bullet2.boundingBox.shrink(2D, 2D, 2D);
					Vector b2v = bullet2.getEntity().getVelocity();
					Vector b2vn = b2v.add(new Vector(-0.25f, 0.25f, -0.25f));
					bullet2.getEntity().setVelocity(b2vn);
					
					ItemProjectile bullet3 = new ItemProjectile("bullet-shotgun", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 2.0F);
					//bullet3.setIgnoreSomeBlocks(true);
					//bullet3.boundingBox.shrink(2D, 2D, 2D);
					Vector b3v = bullet3.getEntity().getVelocity();
					Vector b3vn = b3v.add(new Vector(0.25f, -0.25f, -0.25f));
					bullet3.getEntity().setVelocity(b3vn);

					int lvl = e.getPlayer().getLevel() - 1;
					e.getPlayer().setLevel(lvl);
					float progress = (float) lvl/Shotgun.getMagSize();
					e.getPlayer().setExp(progress);
					e.setCancelled(true);
					PlayerAPI.setAmmoAmount(e.getPlayer(), "Shotgun", lvl);
					
					final UUID uuid = e.getPlayer().getUniqueId();
					PlayerHandler.ShotgunReloadHandler.addToList(uuid);
					Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {

						@Override
						public void run() {
							PlayerHandler.ShotgunReloadHandler.removeFromList(uuid);
						}
						
					}, 40L);
				}
			}
		}

	}

	@Override
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
		if (gunIS.getType() == Material.MONSTER_EGG && gunIS.getDurability() == 52) {
			if (gunIS.getItemMeta().getDisplayName().equals("Shotgun")) {
				e.getPlayer().setLevel(Shotgun.getMagSize());
				float progress = Shotgun.getMagSize() / Shotgun.getMagSize();
				PlayerAPI.setAmmoAmount(e.getPlayer(), "Shotgun", Shotgun.getMagSize());
				e.getPlayer().setExp(progress);
			}
		}

	}

	public static int getMagSize() {
		return 8;
	}

	public static ItemStack getGun() {
		ItemStack gun = new ItemStack(Material.MONSTER_EGG);
		gun.setDurability((short) 52);
		ItemMeta gunIM = gun.getItemMeta();
		gunIM.setDisplayName("Shotgun");
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
						if (e.getProjectile().getProjectileName().equals("bullet-shotgun")) {
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
		return 4.0D;
	}

	@Override
	public double getAnimalDamage() {
		return this.getPlayerDamage();
	}
}
