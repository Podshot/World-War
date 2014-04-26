package io.github.podshot.events;

import java.util.List;

import io.github.podshot.handlers.ItemStackHandler;
import io.github.podshot.internals.Internals;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import com.stirante.MoreProjectiles.Particles;
import com.stirante.MoreProjectiles.TypedRunnable;
import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.CustomProjectile;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class GunEvents implements Listener {

	@SuppressWarnings("unused")
	private ItemStack pistolItemStack = ItemStackHandler.getPistolItemStack();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {

		// if (Internals.warDeclared) {
		// return;
		// }

		if (e.getAction() == Action.LEFT_CLICK_AIR) {
			if (e.getItem().getType().equals(Material.MONSTER_EGG)) {
				String gunName = e.getItem().getItemMeta().getDisplayName().toString();
				e.getPlayer().sendMessage("Item Name: " + gunName);
				if (gunName.equals("Standard Issue Rifle")) {
					e.getItem().setDurability((short) 50);
					e.getPlayer().setLevel(25);
				}
				return;
			}
			if (e.getItem().getType() == Material.DIAMOND_HOE) {
				if (e.getItem().getItemMeta().getDisplayName().toString().equals("Rocket Launcher")) {
					e.getItem().setDurability((short) 0);
				}
				return;
			}
			return;
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem().getType().equals(Material.MONSTER_EGG)) {
				e.getPlayer().getItemInHand().setType(Material.MONSTER_EGG);
				e.getPlayer().getItemInHand().setDurability((short) 50);
			} else if (e.getItem().getType().equals(Material.RECORD_11)) {
				if (e.getItem().getItemMeta().getDisplayName().toString().equals("Remote")) {
					List<String> coords = e.getItem().getItemMeta().getLore();
					String x = coords.get(0).toString().split(": ")[1].toString();
					String y = coords.get(1).toString().split(": ")[1].toString();
					String z = coords.get(2).toString().split(": ")[1].toString();
					String world = coords.get(3).toString().split(": ")[1].toString();

					Location loc = new Location(Bukkit.getWorld(world), Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
					if (loc.getBlock().getType().equals(Material.SKULL)) {
						if (!(Bukkit.getWorld(world).getChunkAt(loc).isLoaded())) {
							Bukkit.getWorld(world).getChunkAt(loc).load(true);
						}
						Bukkit.getWorld(world).createExplosion(loc, 3.5F);
						e.getPlayer().getItemInHand().setType(Material.AIR);
						e.getPlayer().updateInventory();
					}
					return;
				}
				return;
			}
			return;
		}
		if (e.getAction() == Action.RIGHT_CLICK_AIR) {
			String gunType = e.getItem().getItemMeta().getDisplayName().toString();
			if (e.getItem().getType().equals(Material.MONSTER_EGG)) {
				if (gunType.equals("Standard Issue Rifle")) {
					if (e.getPlayer().getLevel() != 0) {
						ItemProjectile rBullet = new ItemProjectile("bullet-rifle", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 2.0F);
						rBullet.setIgnoreSomeBlocks(true);
						rBullet.boundingBox.shrink(2D, 2D, 2D);
						int n_lvl = e.getPlayer().getLevel() - 1;
						//Bukkit.getLogger().info("Old XP (i): " + o_lvl);
						//Bukkit.getLogger().info("New XP (i): " + n_lvl);
						e.getPlayer().setLevel(n_lvl);
						//e.getItem().setDurability((short) (e.getItem().getDurability() + 10));
						e.setCancelled(true);
					}
					return;
				}
				return;
			}
			if (e.getItem().getType().equals(Material.WOOD_HOE)) {
				if (gunType.equals("Pistol")) {
					ItemProjectile pBullet = new ItemProjectile("bullet-pistol", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 3.0F);
					pBullet.setIgnoreSomeBlocks(true);
					pBullet.boundingBox.shrink(2D, 2D, 2D);
					e.getItem().setDurability((short) (e.getItem().getDurability() - (short) 1));
					e.setCancelled(true);
				}
				return;
			}
			if (e.getItem().getType().equals(Material.DIAMOND_HOE)) {
				if (e.getItem().getDurability() == 1561) {
					if (gunType.equals("Rocket Launcher")) {
						CustomProjectile rocket = new ItemProjectile("rocket", e.getPlayer(), new ItemStack(Material.AIR), 2.0F);
						rocket.addTypedRunnable(new TypedRunnable<ItemProjectile>() {
							public void run(ItemProjectile o) {
								Particles.LARGE_SMOKE.display(o.getEntity().getLocation(), 0, 0, 0, 0, 5);
								Particles.FLAME.display(o.getEntity().getLocation(), 0, 0, 0, 0, 2);
							}
						});
						e.getItem().setDurability((short) 1561);
						e.setCancelled(true);
					}
					return;
				}
				return;
			}
			return;
		}
		return;
	}

	@EventHandler
	public void onHit(ItemProjectileHitEvent e) {
		String sTeam = null;
		String hTeam = null;
		LivingEntity hitE = e.getHitEntity();
		if (Internals.warDeclared) {
			if (e.getHitType() == CustomProjectileHitEvent.HitType.ENTITY) {
				// e.getHitEntity().damage(3.0D, e.getProjectile().getShooter());
				Player shooter = (Player) e.getProjectile().getShooter();
				for (MetadataValue val : shooter.getMetadata("WroldWar.Team")) {
					if (val.getOwningPlugin().getName().equals("WorldWar")) {
						sTeam = val.asString();
					}
				}
				if (e.getHitEntity().getType() == EntityType.PLAYER) {
					Player hitP = (Player) hitE;
					for (MetadataValue val : hitP.getMetadata("WorldWar.Team")) {
						if (val.getOwningPlugin().getName().equals("WorldWar")) {
							hTeam = val.asString();
						}
					}
					if (sTeam == "Blue") {
						if (hTeam == "Red") {
							if (e.getProjectile().getProjectileName() == "bullet-rifle") {
								hitP.damage(4.0D, shooter);
							} else if (e.getProjectile().getProjectileName() == "bullet-pistol") {
								hitP.damage(2.0D, shooter);
							}
						}
					} else if (sTeam == "Red") {
						if (hTeam == "Blue") {
							if (e.getProjectile().getProjectileName() == "bullet-rifle") {
								hitP.damage(2.0D, shooter);
							} else if (e.getProjectile().getProjectileName() == "bullet-pistol") {
								hitP.damage(2.0D, shooter);
							}
						}
					}
				} else {
					hitE.damage(2.0D, shooter);
					if (sTeam == hTeam) {
						Damageable d = (Damageable) hitE;
						d.setHealth(d.getHealth() + 2.0D);
					}
				}
			}
			if (e.getProjectile().getProjectileName().equals("rocket")) {
				e.getProjectile().getEntity().getWorld().createExplosion(e.getProjectile().getEntity().getLocation(), 0.0F);
			}
		}
		return;
	}
}
