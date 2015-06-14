package io.github.podshot.WorldWar.squads;

import java.util.List;
import java.util.UUID;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.SquadAPI_OLD;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

public class RemoveSquad {
	
	private WorldWar plugin = WorldWar.getInstance();

	public RemoveSquad(String squadName, String name) {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		String founder = squadConfig.getString("Squads." + squadName + ".Founder");
		if (founder.equals(name)) {
			for (String player : squadConfig.getStringList("Squads." + squadName + ".Members")) {
				SquadAPI_OLD.removeMember(squadName, UUID.fromString(player));
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.getName().equals(player)) {
						p.removeMetadata("WorldWar.Squad", plugin);
						p.removeMetadata("WorldWar.inSquad", plugin);
					}
				}
			}
			squadConfig.set("Squads." + squadName + ".Members", null);
			squadConfig.set("Squads." + squadName + ".Founder", null);
			squadConfig.set("Squads." + squadName, null);
			List<String> squadList = squadConfig.getStringList("Squads.Global.SquadList");
			squadList.remove(squadName);
			squadConfig.set("Squads.Global.SquadList", squadList);
			ExtraConfigHandler.saveConfig(plugin.fileSep + "Squads");
		}
	}
}
