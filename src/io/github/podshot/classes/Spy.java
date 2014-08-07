package io.github.podshot.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.podshot.files.PlayerDataYAML;
import io.github.podshot.handlers.PlayerClasses;
import io.github.podshot.inventories.InventoryManager;

public class Spy {

	private static PlayerClasses type = PlayerClasses.Spy;
	private static List<UUID> spies = new ArrayList<UUID>();

	public Spy(Player player, boolean remove) {
		if (remove) {
			if (spies.contains(player.getUniqueId())) {
				spies.remove(player.getUniqueId());
			}
		} else {
			spies.add(player.getUniqueId());
			String savedInventory = InventoryManager.InventoryToString(player.getInventory());
			PlayerDataYAML.saveInventoryToFile(player, savedInventory);
			player.getInventory().clear();
			player.getInventory().setContents(this.getClassesInventory());
		}
	}

	private ItemStack[] getClassesInventory() {
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		ItemStack[] itemArray = items.toArray(new ItemStack[items.size()]);
		return itemArray;
	}

	public static PlayerClasses getPlayerClassesType() {
		return type;
	}

	public static List<UUID> getSpies() {
		return spies;
	}
}
