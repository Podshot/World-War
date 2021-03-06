package io.github.podshot.WorldWar.events.guns;

import io.github.podshot.WorldWar.api.GunType;
import io.github.podshot.WorldWar.api.PlayerAPI;
import io.github.podshot.WorldWar.handlers.WarHandler;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GunSwitch implements Listener {

	@EventHandler
	public void onGunSwitch(PlayerItemHeldEvent evt) {
		if (WarHandler.isWarDeclared()) {
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
							PlayerAPI.setAmmoAmount(evt.getPlayer(), GunType.RIFLE, evt.getPlayer().getLevel());
							break;
						case "Rocket Launcher":
							PlayerAPI.setAmmoAmount(evt.getPlayer(), GunType.ROCKET_LAUNCHER, evt.getPlayer().getLevel());
							break;
						case "Shotgun":
							PlayerAPI.setAmmoAmount(evt.getPlayer(), GunType.SHOTGUN, evt.getPlayer().getLevel());
							break;
						case "Pistol":
							PlayerAPI.setAmmoAmount(evt.getPlayer(), GunType.PISTOL, evt.getPlayer().getLevel());
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
							evt.getPlayer().setLevel(PlayerAPI.getAmmoAmount(evt.getPlayer(), GunType.RIFLE));
							float progress = PlayerAPI.getAmmoAmount(evt.getPlayer(), GunType.RIFLE) / Rifle.getMagSize();
							evt.getPlayer().setExp(0f);
							evt.getPlayer().setExp(progress);
							break;
						case "Rocket Launcher":
							evt.getPlayer().setLevel(PlayerAPI.getAmmoAmount(evt.getPlayer(), GunType.ROCKET_LAUNCHER));
							break;
						case "Shotgun":
							evt.getPlayer().setLevel(PlayerAPI.getAmmoAmount(evt.getPlayer(), GunType.SHOTGUN));
							break;
						case "Pistol":
							evt.getPlayer().setLevel(PlayerAPI.getAmmoAmount(evt.getPlayer(), GunType.PISTOL));
							break;
						}
					}
				}
			}
		}
	}
}
