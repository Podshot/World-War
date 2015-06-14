package io.github.podshot.WorldWar.squads;

import io.github.podshot.WorldWar.WorldWar;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

public class Squad_OLD {

	private WorldWar plugin = WorldWar.getInstance();

	public Squad_OLD(String squadName, UUID leader) {
		File squadFile = new File(plugin.getDataFolder() + plugin.fileSep + "Squads");
		if (!squadFile.exists()) {
			addValues();
		}
		this.addSquad(squadName, leader);
	}
	
	private void addValues() {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		squadConfig.set("Squads.Global.MemberLimit", 10);
		List<String> inSquads = Arrays.asList("", "");
		squadConfig.set("Squads.Global.PeopleInSquads", inSquads);
		List<String> squads = Arrays.asList("", "");
		squadConfig.set("Squads.Global.SquadList", squads);
		ExtraConfigHandler.saveConfig(plugin.fileSep + "Squads");
	}

	public void addSquad(String squadName, UUID uuid) {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		List<String> members = Arrays.asList(uuid.toString());
		squadConfig.set("Squads." + squadName + ".Members", members);
		squadConfig.set("Squads." + squadName + ".Leader", uuid.toString());
		List<String> squadList = squadConfig.getStringList("Squads.Global.SquadList");
		squadList.add(squadName);
		squadConfig.set("Squads.Global.SquadList", squadList);
		List<String> globalSquadMembers = squadConfig.getStringList("Squads.Global.PeopleInSquads");
		globalSquadMembers.add(uuid.toString());
		squadConfig.set("Squads.Global.PeopleInSquads", globalSquadMembers);
		ExtraConfigHandler.saveConfig(plugin.fileSep + "Squads");
	}
}
