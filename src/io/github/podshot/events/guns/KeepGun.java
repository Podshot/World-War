package io.github.podshot.events.guns;

import io.github.podshot.internals.Internals;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class KeepGun implements Listener {

	@EventHandler
	public void onUseOnGround(PlayerInteractEvent evt) {
		if (!(Internals.isWarDeclared())) {
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
	}
}
