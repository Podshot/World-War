package io.github.podshot.events.guns;

import io.github.podshot.api.interfaces.IGun;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.stirante.MoreProjectiles.event.ItemProjectileHitEvent;

public class BombDiffuser implements Listener, IGun {

	@Override
	@EventHandler
	public void onFireGun(PlayerInteractEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	@EventHandler
	public void onGunReload(PlayerInteractEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getMagSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ItemStack getGun() {
		ItemStack bombD = new ItemStack(Material.MONSTER_EGG);
		bombD.setDurability((short) 91);
		ItemMeta imBombD = bombD.getItemMeta();
		imBombD.setDisplayName("Bomb Diffuser");
		bombD.setItemMeta(imBombD);
		return bombD;
	}

	@Override
	@EventHandler
	public void onBulletHit(ItemProjectileHitEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getPlayerDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAnimalDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

}
