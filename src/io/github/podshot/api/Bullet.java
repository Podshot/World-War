package io.github.podshot.api;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.stirante.MoreProjectiles.Particles;
import com.stirante.MoreProjectiles.TypedRunnable;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class Bullet {

	public Bullet(String bulletName, ItemStack item, Player p, float speed) {
		ItemProjectile bullet = new ItemProjectile(bulletName, p, item, speed);
		bullet.boundingBox.shrink(2.0D, 2.0D, 2.0D);
		bullet.setIgnoreSomeBlocks(true);
	}
	
	public Bullet(String bulletName, ItemStack item, Player p, float speed, final List<Particles> particles) {
		ItemProjectile bullet = new ItemProjectile(bulletName, p, item, speed);
		bullet.boundingBox.shrink(2.0D, 2.0D, 2.0D);
		bullet.setIgnoreSomeBlocks(true);
		bullet.addTypedRunnable(new TypedRunnable<ItemProjectile>() {
			@Override
			public void run(ItemProjectile o) {
				for (Particles particle : particles) {
					particle.display(o.getEntity().getLocation(), 0, 0, 0, 0, 1);
				}
			}	
		});
	}

}
