package io.github.podshot.events.guns;

import io.github.podshot.api.interfaces.Gun;
import io.github.podshot.internals.Internals;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class SniperRifle implements Listener, Gun {

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
		if (gunIS.getType() == Material.MONSTER_EGG || gunIS.getDurability() == 60) {
			if (e.getPlayer().getLevel() >= 0) {
				String gun = gunIS.getItemMeta().getDisplayName().toString();
				if (gun.equals("Sniper Rifle")) {
					ItemProjectile bullet = new ItemProjectile("bullet-sniper", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 2.5F);
					bullet.setIgnoreSomeBlocks(true);
					bullet.boundingBox.shrink(2D, 2D, 2D);

					int lvl = e.getPlayer().getLevel() - 1;
					e.getPlayer().setLevel(lvl);
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onGunReload(PlayerInteractEvent e) {
		// TODO Auto-generated method stub

	}

}
