package io.github.podshot.events.guns;

import java.util.List;

import io.github.podshot.internals.Internals;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Remote implements Listener {

	@EventHandler
	public void onUseRemote(PlayerInteractEvent e) {

		if (!(Internals.isWarDeclared())) {
			return;
		}

		if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		if (e.getItem() == null) {
			return;
		}

		if (!(e.getItem().hasItemMeta())) {
			return;
		}

		if (e.getItem().getType().equals(Material.RECORD_11)) {
			if (e.getItem().getItemMeta().getDisplayName().toString().equals("Remote")) {
				List<String> coords = e.getItem().getItemMeta().getLore();
				String x = coords.get(0).toString().split(": ")[1].toString();
				String y = coords.get(1).toString().split(": ")[1].toString();
				String z = coords.get(2).toString().split(": ")[1].toString();
				String world = coords.get(3).toString().split(": ")[1].toString();
				e.getPlayer().getInventory().remove(e.getItem());

				Location loc = new Location(Bukkit.getWorld(world), Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
				if (loc.getBlock().getType().equals(Material.SKULL)) {
					if (!(Bukkit.getWorld(world).getChunkAt(loc).isLoaded())) {
						Bukkit.getWorld(world).getChunkAt(loc).load(true);
					}
					Bukkit.getWorld(world).createExplosion(loc, 3.5F);
				}
				return;
			}
			return;
		}
		return;
	}
}