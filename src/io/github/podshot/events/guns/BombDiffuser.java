package io.github.podshot.events.guns;

import io.github.podshot.api.interfaces.Gun;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BombDiffuser implements Listener, Gun {

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

}
