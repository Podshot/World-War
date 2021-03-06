package io.github.podshot.WorldWar.events.guns;

import io.github.podshot.WorldWar.handlers.WarHandler;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class KeepGun implements Listener {

	@EventHandler
	public void onUseOnGround(PlayerInteractEvent evt) {
		if (!(WarHandler.isWarDeclared())) {
			return;
		}

		if (!(evt.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		if (!(evt.getItem() != null)) {
			return;
		}

		if (evt.getItem().getType() == Material.MONSTER_EGG) {
			if (evt.getItem().hasItemMeta()) {
				evt.setCancelled(true);
				evt.getPlayer().getInventory().setItemInHand(evt.getItem());
			}
		}
		
		if (evt.getItem().getType() == Material.FIREBALL) {
			if (evt.getItem().hasItemMeta()) {
				if (evt.getItem().getItemMeta().hasDisplayName()) {
					if (evt.getItem().getItemMeta().getDisplayName().equals("Normal Grenade")) {
						evt.setCancelled(true);
						evt.getPlayer().getInventory().setItemInHand(evt.getItem());
					}
				}
			}
		}
	}
}
