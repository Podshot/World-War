package io.github.podshot.events.guns;

import io.github.podshot.api.PlayerAPI;
import io.github.podshot.api.interfaces.Gun;
import io.github.podshot.internals.Internals;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.stirante.MoreProjectiles.projectile.ItemProjectile;

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
					ItemProjectile bullet = new ItemProjectile("bullet-rifle", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 2.0F);
					bullet.setIgnoreSomeBlocks(true);
					bullet.boundingBox.shrink(2D, 2D, 2D);
					int lvl = e.getPlayer().getLevel() - 1;
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
		if (gunIS.getType().equals(Material.MONSTER_EGG) && gunIS.getDurability() == 51) {
			if (gunIS.getItemMeta().getDisplayName().toString().equals("Standard Issue Rifle")) {
				e.getPlayer().setLevel(25);
				PlayerAPI.setAmmoAmount(e.getPlayer(), "Rifle", 25);
			}
		}
	}
}
