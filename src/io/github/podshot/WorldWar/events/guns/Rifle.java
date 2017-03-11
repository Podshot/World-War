package io.github.podshot.WorldWar.events.guns;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.Bullet;
import io.github.podshot.WorldWar.api.GunType;
import io.github.podshot.WorldWar.api.PlayerAPI;
import io.github.podshot.WorldWar.api.Sounds;
import io.github.podshot.WorldWar.api.WorldWarTeam;
import io.github.podshot.WorldWar.api.interfaces.IGun;
import io.github.podshot.WorldWar.handlers.PlayerHandler;
import io.github.podshot.WorldWar.handlers.WarHandler;

import java.util.UUID;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
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

public class Rifle implements IGun {
	
	private WorldWar plugin = WorldWar.getInstance();
	
	private static NBTTagCompound nbtTag = generateTag();

	@EventHandler
	public void onFireGun(PlayerInteractEvent e) {
		Player player = e.getPlayer();

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
		
		if (PlayerHandler.RifleReloadHandler.isInList(e.getPlayer().getUniqueId())) {
			return;
		}

		ItemStack gunIS = e.getItem();
		//plugin.logger.warning(gunIS.getType().toString());
		//plugin.logger.warning(gunIS.getData());
		//if (gunIS.getType() == Material.MONSTER_EGG && gunIS.getDurability() == 51) {
		if (gunIS.getType() == Material.MONSTER_EGG) {
			//plugin.logger.info("?");
			if (player.getLevel() > 0) {
				String gun = gunIS.getItemMeta().getDisplayName().toString();
				if (gun.equals("Standard Issue Rifle")) {
					//plugin.logger.info("Using rifle");
					/*
					ItemProjectile bullet = new ItemProjectile("bullet-rifle", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 2.0F);
					bullet.setIgnoreSomeBlocks(true);
					bullet.boundingBox.shrink(2D, 2D, 2D);
					*/
					ItemProjectile bullet = Bullet.createRegularBullet("bullet-rifle", new ItemStack(Material.STONE_BUTTON), player, 3F);
					//OrbProjectile bullet = new OrbProjectile("bullet-rifle", e.getPlayer(), 2.0f);
					//e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.FIREWORK_BLAST, 0.5f, 0.6f);
					Sounds.playRifleSound(player);
					//bullet.getBukkitEntity().setVelocity(bullet.getBukkitEntity().getVelocity().multiply(1.75));
					int lvl = player.getLevel() - 1;
					float progress = (float) lvl/Rifle.getMagSize();
					this.plugin.logger.info("Progress: " + progress);
					player.setExp(progress);
					player.setLevel(lvl);
					e.setCancelled(true);
					PlayerAPI.setAmmoAmount(player, GunType.RIFLE, lvl);
					final UUID uuid = player.getUniqueId();
					PlayerHandler.RifleReloadHandler.addToList(uuid);
					Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {

						@Override
						public void run() {
							PlayerHandler.RifleReloadHandler.removeFromList(uuid);
						}
						
					}, 20L);
				}
			} else {
				player.sendMessage(ChatColor.RED + "You don't have enough ammo to use this gun!");
			}
		}
		return;
	}

	@EventHandler
	public void onGunReload(PlayerInteractEvent e) {
		Player player = e.getPlayer();

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
		//if (gunIS.getType() == Material.MONSTER_EGG && gunIS.getDurability() == 51) {
		if (gunIS.getType() == Material.MONSTER_EGG) {
			if (gunIS.getItemMeta().getDisplayName().equals("Standard Issue Rifle")) {
				player.setLevel(Rifle.getMagSize());
				float progress = Rifle.getMagSize() / Rifle.getMagSize();
				PlayerAPI.setAmmoAmount(player, GunType.RIFLE, Rifle.getMagSize());
				player.setExp(progress);
			}
		}
	}

	public static int getMagSize() {
		return 25;
	}

	public static ItemStack getGun() {
		ItemStack gun = new ItemStack(Material.MONSTER_EGG);
		//gun.setDurability((short) 51);
		//ItemMeta gunIM = gun.getItemMeta();
		//gunIM.setDisplayName("Standard Issue Rifle");
		//gun.setItemMeta(gunIM);
		net.minecraft.server.v1_10_R1.ItemStack nmsGun = CraftItemStack.asNMSCopy(gun);
		nmsGun.setTag(nbtTag);
		gun = CraftItemStack.asBukkitCopy(nmsGun);
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
	
	public static NBTTagCompound generateTag() {
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagCompound entityTag = new NBTTagCompound();
		NBTTagCompound displayTag = new NBTTagCompound();
		
		entityTag.setString("id", "Skeleton");
		displayTag.setString("Name", "Standard Issue Rifle");
		
		tag.set("EntityTag", entityTag);
		tag.set("display", displayTag);
		
		return tag;
	}
}
