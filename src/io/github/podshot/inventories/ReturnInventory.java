package io.github.podshot.inventories;

import io.github.podshot.WorldWar;
import io.github.podshot.api.Refactor;
import io.github.podshot.api.Refactor.Priority;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Refactor(priority = Priority.URGENT)
public class ReturnInventory {

	private WorldWar plugin = WorldWar.getInstance();

	@SuppressWarnings("unchecked")
	public ReturnInventory(Player player) {

		JSONObject json = this.readJSON(player);
		
		JSONArray items = (JSONArray) json.get("Items");
		Iterator<String> itemStrings = items.iterator();
		while (itemStrings.hasNext()) {
			String itemStr = itemStrings.next();
			if (itemStr.endsWith("empty")) {
				String[] splitString = itemStr.split("|");
				int slotNum = Integer.parseInt(splitString[0].toString());
				player.getInventory().setItem(slotNum, null);
			} else {
				
			}
		}
	}

	private JSONObject readJSON(Player player) {
		JSONParser parser = new JSONParser();
		JSONObject jobj = null;

		try {
			Object obj = parser.parse(new FileReader(this.plugin.getDataFolder() + this.plugin.fileSep + "Player-Inventory-Backup" + this.plugin.fileSep + player.getUniqueId().toString() + ".json"));
			if (obj != null) {
				jobj = (JSONObject) obj;
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return jobj;
	}

}
