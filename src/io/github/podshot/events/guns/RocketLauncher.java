package io.github.podshot.events.guns;

import io.github.podshot.api.interfaces.Gun;
import io.github.podshot.internals.Internals;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.stirante.MoreProjectiles.Particles;
import com.stirante.MoreProjectiles.TypedRunnable;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class RocketLauncher implements Gun, Listener {

	@EventHandler
	public void onFireGun(PlayerInteractEvent e) {
		if (!(Internals.isWarDeclared())) {
			return;
		}

		if (!(e.getAction() == Action.RIGHT_CLICK_AIR)) {
			return;
		}

		ItemStack gunIS = e.getItem();
		if (gunIS.getType() == Material.MONSTER_EGG && gunIS.getDurability() == 50) {
			if (e.getPlayer().getLevel() >= 0) {
				String gun = gunIS.getItemMeta().getDisplayName().toString();
				//if (gun.equals("Rocket Launcher")) {
					ItemProjectile bullet = new ItemProjectile("bullet-rocket", e.getPlayer(), new ItemStack(Material.STONE), 2.5F);
					bullet.setIgnoreSomeBlocks(true);
					bullet.boundingBox.shrink(2D, 2D, 2D);
					bullet.addTypedRunnable(new TypedRunnable<ItemProjectile>() {
						@Override
						public void run(ItemProjectile o) {
							Particles.FLAME.display(o.getEntity().getLocation(), 0, 0, 0, 0, 1);
							Particles.LARGE_SMOKE.display(o.getEntity().getLocation(), 0, 0, 0, 0, 1);
						}
					});
					int lvl = e.getPlayer().getLevel() - 1;
					e.getPlayer().setLevel(lvl);
					e.setCancelled(true);
				//}
			}
		}
		return;

	}

	@EventHandler
	public void onGunReload(PlayerInteractEvent e) {
		// TODO Auto-generated method stub

	}

}
