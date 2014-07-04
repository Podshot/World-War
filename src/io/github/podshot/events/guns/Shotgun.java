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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class Shotgun implements Gun, Listener {

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
		
		ItemStack gunIS = e.getItem();
		if (gunIS.getType() == Material.MONSTER_EGG && gunIS.getDurability() == 52) {
			if (e.getPlayer().getLevel() > 0) {
				String gun = gunIS.getItemMeta().getDisplayName().toString();
				if (gun.equals("Shotgun")) {
					
					ItemProjectile bullet1 = new ItemProjectile("bullet-shotgun", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 2.0F);
					bullet1.setIgnoreSomeBlocks(true);
					bullet1.boundingBox.shrink(2D, 2D, 2D);
					bullet1.getEntity().getVelocity().add(new Vector(0.125f, 0f, 0.125f));
					
					ItemProjectile bullet2 = new ItemProjectile("bullet-shotgun", e.getPlayer(), new ItemStack(Material.STONE_BUTTON), 2.0F);
					bullet2.setIgnoreSomeBlocks(true);
					bullet2.boundingBox.shrink(2D, 2D, 2D);
					bullet2.getEntity().getVelocity().add(new Vector(0.125f, 0f, 0.125f));
					
					int lvl = e.getPlayer().getLevel() - 1;
					e.getPlayer().setLevel(lvl);
					e.setCancelled(true);
					PlayerAPI.setAmmoAmount(e.getPlayer(), "Shotgun", lvl);
				}
			}
		}

	}

	@Override
	@EventHandler
	public void onGunReload(PlayerInteractEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getMagSize() {
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

}
