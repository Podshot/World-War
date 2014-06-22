package io.github.podshot.squads;

import io.github.podshot.WorldWar;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

public class Squad {

	private WorldWar plugin = WorldWar.getInstance();
	private static WorldWar sPlugin = WorldWar.getInstance();

	public Squad(String squadName, UUID leader) {
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

	public void addMember(String squadName, UUID memberUUID) {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		List<String> existingMembers = squadConfig.getStringList("Squads." + squadName + ".Members");
		if (existingMembers.size() <= squadConfig.getInt("Squads.Global.MemberLimit")) {
			existingMembers.add(memberUUID.toString());
			List<String> squadMembers = squadConfig.getStringList("Squads.Global.PeopleInSquads");
			squadMembers.add(memberUUID.toString());
			squadConfig.set("Squads.Global.PeopleInSquads", squadMembers);
		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getName().equals(squadConfig.getString("Squads." + squadName + ".Leader"))) {
					p.sendMessage(ChatColor.RED + "Your squad is at the maximum member limit!");
				}
			}
		}
		squadConfig.set("Squads." + squadName + ".Members", existingMembers);
		ExtraConfigHandler.saveConfig(plugin.fileSep + "Squads");
	}
	
	public static void removeMember(String squadName, UUID memberUUID) {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(sPlugin.getDataFolder() + sPlugin.fileSep + "Squads");
		List<String> existingMembers = squadConfig.getStringList("Squads." + squadName + ".Members");
		existingMembers.remove(memberUUID.toString());
		squadConfig.set("Squads." + squadName + ".Members", existingMembers);
		List<String> squadMembers = squadConfig.getStringList("Squads.Global.PeopleInSquads");
		squadMembers.remove(memberUUID.toString());
		squadConfig.set("Squads.Global.PeopleInSquads", squadMembers);
		ExtraConfigHandler.saveConfig(sPlugin.fileSep + "Squads");		
	}
	
	public static String getSquadForPlayer(UUID uuid) {
		String ret = null;
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(sPlugin.fileSep + "Squads");
		List<String> members = squadConfig.getStringList("Squads.Global.PeopleInSquads");
		if (members.contains(uuid.toString())) {
			for (String squad : squadConfig.getStringList("Squads.Global.SquadList")) {
				if (squadConfig.getStringList("Squads." + squad + ".Members").contains(uuid.toString())) {
					ret = squad;
				}
			}
		} else {
			ret = "Not in Squad";
		}
		return ret;
	}
}
