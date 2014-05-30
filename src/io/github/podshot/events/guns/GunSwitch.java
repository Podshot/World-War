package io.github.podshot.events.guns;

import io.github.podshot.api.PlayerAPI;
import io.github.podshot.internals.Internals;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GunSwitch implements Listener {

	@EventHandler
	public void onGunSwitch(PlayerItemHeldEvent evt) {
		if (Internals.isWarDeclared()) {
			Inventory inv = evt.getPlayer().getInventory();
			int slot = evt.getNewSlot();
			int oldSlot = evt.getPreviousSlot();
			if (inv.getItem(oldSlot) != null) {
				ItemStack oldis = inv.getItem(oldSlot);
				if (oldis.getType() == Material.MONSTER_EGG) {
					if (oldis.hasItemMeta()) {
						String oldGunName = oldis.getItemMeta().getDisplayName().toString();
						switch (oldGunName) {
						default:
							break;
						case "Standard Issue Rifle":
							PlayerAPI.setAmmoAmount(evt.getPlayer(), "Rifle", evt.getPlayer().getLevel());
							break;
						case "Rocket Launcher":
							PlayerAPI.setAmmoAmount(evt.getPlayer(), "Rocket", evt.getPlayer().getLevel());
							break;
						}
					}
				}
			}
			if (inv.getItem(slot) != null) {
				ItemStack newis = inv.getItem(slot);
				if (newis.getType() == Material.MONSTER_EGG) {
					if (newis.hasItemMeta()) {
						String gunName = newis.getItemMeta().getDisplayName().toString();
						switch (gunName) {
						default:
							break;
						case "Standard Issue Rifle":
							evt.getPlayer().setLevel(PlayerAPI.getAmmoAmount(evt.getPlayer(), "Rifle"));
							break;
						case "Rocket Launcher":
							evt.getPlayer().setLevel(PlayerAPI.getAmmoAmount(evt.getPlayer(), "Rocket"));
							break;
						}
					}
				}
			}
		}
	}
}
