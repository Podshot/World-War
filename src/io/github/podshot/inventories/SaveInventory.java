package io.github.podshot.inventories;

import io.github.podshot.WorldWar;
import io.github.podshot.api.Refactor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Refactor()
public class SaveInventory {
	
	private WorldWar plugin = WorldWar.getInstance();

	@SuppressWarnings("unused")
	private String itemFormatTemplate = "slot|material|durability|amount|displayName|lore|enchantments";

	@SuppressWarnings("unchecked")
	public SaveInventory(PlayerInventory playerInv, UUID uuid) {
		JSONObject jsonFile = new JSONObject();
		JSONArray items = new JSONArray();
		int size = playerInv.getSize();
		int currentSlot = 0;
		while (currentSlot <= size) {
			ItemStack item = playerInv.getItem(currentSlot);
			if (item == null) {
				items.add(currentSlot + "|empty");
			} else {
				String itemFormated = currentSlot + "|" + item.getType().toString() + "|" + item.getDurability() + "|" + item.getAmount() + "|";
				if (item.hasItemMeta()) {
					ItemMeta itemMeta = item.getItemMeta();
					if (itemMeta.hasDisplayName()) {
						itemFormated = itemFormated + itemMeta.getDisplayName().toString() + "|";
					} else {
						itemFormated = itemFormated + "none|";
					}

					if (itemMeta.hasLore()) {
						String loreFormated = null;
						for (String lineOfLore : itemMeta.getLore()) {
							loreFormated = loreFormated + lineOfLore + ":";
						}
						loreFormated = loreFormated.substring(0, loreFormated.length()-1);
						itemFormated = itemFormated + loreFormated;
					} else {
						itemFormated = itemFormated + "none";
					}
				} else {
					itemFormated = itemFormated + "none|none";
				}
				if (item.getEnchantments() != null) {
					String enchantFormat = "|";
					Map<Enchantment, Integer> enchantments = item.getEnchantments();
					for (Enchantment key : enchantments.keySet()) {
						Integer level = enchantments.get(key);
						enchantFormat = enchantFormat + key.getName() + "#" + level + ":";
					}
					enchantFormat = enchantFormat.substring(0, enchantFormat.length()-1);
					itemFormated = itemFormated + enchantFormat;
				}
				items.add(itemFormated);
			}
		}
		jsonFile.put("Items", items);
		
		try {
			FileWriter fileToWrite = new FileWriter(this.plugin.getDataFolder() + this.plugin.fileSep + "Player-Inventory-Backup" + this.plugin.fileSep + uuid.toString() + ".json");
			fileToWrite.write(jsonFile.toJSONString());
			fileToWrite.flush();
			fileToWrite.close();
		} catch (IOException e) {
			this.plugin.logger.warning("Could not save the Inventory of Player: \"" + Bukkit.getPlayer(uuid).getName() + "\" (" + uuid.toString() + ")");
			e.printStackTrace();
		}
		
	}
}
