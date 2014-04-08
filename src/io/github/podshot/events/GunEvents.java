package io.github.podshot.events;

import io.github.podshot.handlers.ItemStackHandler;

import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.stirante.MoreProjectiles.projectile.OrbProjectile;

public class GunEvents implements Listener {
	
	private ItemStack gunItemStack = ItemStackHandler.getGunItemStack();

	@SuppressWarnings("unused")
	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR)) {
			return;
		}
		
		if (!(e.getItem() == gunItemStack )) {
			return;
		}
		
		Snowball s = e.getPlayer().launchProjectile(Snowball.class);
		
	}

}
