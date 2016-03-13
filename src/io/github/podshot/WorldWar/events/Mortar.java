package io.github.podshot.WorldWar.events;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.interfaces.ISpecialBlock;
import io.github.podshot.WorldWar.handlers.BlockHandler;
import io.github.podshot.WorldWar.handlers.WarHandler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent.HitType;
import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class Mortar implements Listener, ISpecialBlock {

	private WorldWar plugin = WorldWar.getInstance();

	@EventHandler
	public void onClickBlock(final PlayerInteractEvent evt) {
		boolean isMortar = false;
		if (!(WarHandler.isWarDeclared())) {
			return;
		}

		if (!(evt.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		if (evt.getClickedBlock().getType() != Material.NETHER_FENCE) {
			return;
		}

		Block clickedBlock = evt.getClickedBlock();
		for (MetadataValue val : clickedBlock.getMetadata("WorldWar.isMortar")) {
			if (val.getOwningPlugin().getName().equals("WorldWar")) {
				isMortar = val.asBoolean();
			}
		}
		if (isMortar) {
			if (!(evt.getPlayer().isSneaking())) {
				final Location landing_loc = this.getLandingLocation(evt.getClickedBlock());
				plugin.getLogger().info("Firing Mortar....");
				evt.getPlayer().playSound(evt.getClickedBlock().getLocation(), Sound.FIREWORK_BLAST, 1, 1);
				for (Entity entity : evt.getPlayer().getNearbyEntities(10, 10, 10)) {
					if (entity.getType() == EntityType.PLAYER) {
						Player p = (Player) entity;
						p.playSound(evt.getClickedBlock().getLocation(), Sound.FIREWORK_BLAST, 1, 1);
					}
				}
				BlockHandler.MortarHandler.addCooldown(evt.getClickedBlock().getLocation());
				final ItemProjectile shot = new ItemProjectile("mortar-shot", evt.getClickedBlock().getLocation().add(0, 5, 0), new ItemStack(Material.STONE), evt.getPlayer(), 2f);
				//shot.boundingBox.shrink(1D, 1D, 1D);
				shot.getEntity().setVelocity(new Vector(0,5,0));
				Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {

					@Override
					public void run() {
						BlockHandler.MortarHandler.removeCooldown(evt.getClickedBlock().getLocation());
					}

				}, 60L);

				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

					@Override
					public void run() {
						shot.getEntity().teleport(landing_loc.add(new Vector(0, 50, 0)));
						shot.getEntity().setVelocity(new Vector(0,-10,0));
					}

				}, 180L);
			}
		}

	}

	private Location getLandingLocation(Block clickedBlock) {
		Location toReturn = null;
		for (MetadataValue val : clickedBlock.getMetadata("WorldWar.LandingLocation")) {
			if (val.getOwningPlugin().equals(plugin)) {
				String[] loc_data = val.asString().split(":");
				toReturn = new Location(Bukkit.getWorld(loc_data[0]), Double.parseDouble(loc_data[1]), Double.parseDouble(loc_data[2]), Double.parseDouble(loc_data[3]));
			}
		}
		return toReturn;
	}

	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent evt) {
		if (!(WarHandler.isWarDeclared())) {
			return;
		}

		if (evt.getBlock().getType() != Material.NETHER_FENCE) {
			return;
		}

		evt.getBlock().setMetadata("WorldWar.isMortar", new FixedMetadataValue(plugin, true));

		evt.getBlockPlaced().setMetadata("WorldWar.LandingLocation", new FixedMetadataValue(plugin,
				evt.getBlockPlaced().getWorld().getName()+":"+
						(evt.getBlockPlaced().getLocation().getBlockX()+25)+":"+
						evt.getBlockPlaced().getLocation().getBlockY()+":"+
						(evt.getBlockPlaced().getLocation().getBlockZ()+25)));
	}

	@EventHandler
	public void onBreakBlock(BlockBreakEvent evt) {
		// TODO Auto-generated method stub

	}

	@EventHandler
	public void onMortarLand(ItemProjectileHitEvent e) {
		if (!(WarHandler.isWarDeclared())) {
			return;
		}
		if (e.getProjectile().getProjectileName().equals("mortar-shot")) {
			if (e.getHitType() == HitType.BLOCK) {
				Location hit_loc = e.getHitBlock().getLocation();
				hit_loc.getWorld().createExplosion(hit_loc, 8f);
			} else if (e.getHitType() == HitType.ENTITY) {
				Location hit_loc = e.getHitEntity().getLocation();
				for (Entity ent : e.getHitEntity().getNearbyEntities(5, 5, 5)) {
					LivingEntity liv_ent = (LivingEntity) ent;
					liv_ent.damage(this.getMortarDamage(), e.getProjectile().getShooter());
				}
				hit_loc.getWorld().createExplosion(hit_loc, 1f);
			}
			e.getProjectile().getEntity().remove();
		}
	}

	private double getMortarDamage() {
		return 1.0;
	}

}
