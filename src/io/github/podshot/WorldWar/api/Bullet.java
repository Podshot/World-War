package io.github.podshot.WorldWar.api;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.stirante.MoreProjectiles.TypedRunnable;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class Bullet {

	public static ItemProjectile CreateRegularBullet(String bulletName, ItemStack item, Player p, float speed) {
		ItemProjectile bullet = new ItemProjectile(bulletName, p, item, speed);
		return bullet;
	}
	
	public static ItemProjectile CreateRegularBullet(String bulletName, ItemStack item, Player p, float speed, final List<Effect> particles) {
		ItemProjectile bullet = CreateRegularBullet(bulletName, item, p, speed);
		//bullet.boundingBox.shrink(2.0D, 2.0D, 2.0D);
		bullet.addTypedRunnable(new TypedRunnable<ItemProjectile>() {
			@Override
			public void run(ItemProjectile o) {
				for (Effect particle : particles) {
					o.getEntity().getWorld().spigot().playEffect(o.getEntity().getLocation(), particle);
				}
			}	
		});
		return bullet;
	}

}
