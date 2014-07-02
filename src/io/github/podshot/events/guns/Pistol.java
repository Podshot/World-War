package io.github.podshot.events.guns;

import io.github.podshot.api.interfaces.Gun;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Pistol implements Gun, Listener {

	@EventHandler
	public void onFireGun(PlayerInteractEvent e) {
		// TODO Auto-generated method stub
	}

	@EventHandler
	public void onGunReload(PlayerInteractEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getMagSize() {
		return 0;
	}

	public ItemStack getGun() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
