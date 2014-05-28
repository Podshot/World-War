package io.github.podshot.squads;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

import io.github.podshot.WorldWar;

public class Squad {

	private WorldWar plugin = WorldWar.getInstance();

	public Squad(String squadName, String founder) {
		File squadFile = new File(plugin.getDataFolder() + plugin.fileSep + "Squads");
		if (!squadFile.exists()) {
			addValues();
		}
		this.addSquad(squadName, founder);
	}

	private void addValues() {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.getDataFolder() + plugin.fileSep + "Squads");
		squadConfig.set("Squads.Global.MemberLimit", 10);
		ExtraConfigHandler.saveConfig(plugin.getDataFolder() + plugin.fileSep + "Squads");
	}

	public void addSquad(String squadName, String founder) {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.getDataFolder() + plugin.fileSep + "Squads");
		List<String> members = Arrays.asList(founder);
		squadConfig.set("Squads." + squadName + ".Members", members);
		squadConfig.set("Squads." + squadName + ".Founder", founder);
		ExtraConfigHandler.saveConfig(plugin.getDataFolder() + plugin.fileSep + "Squads");
	}

	public void addMember(String squadName, String member) {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.getDataFolder() + plugin.fileSep + "Squads");
		List<String> existingMembers = squadConfig.getStringList("Squads." + squadName + ".Members");
		if (existingMembers.size() <= squadConfig.getInt("Squads.Global.MemberLimit")) {
			existingMembers.add(member);
		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getName().equals(squadConfig.getString("Squads." + squadName + ".Founder"))) {
					p.sendMessage(ChatColor.RED + "Your squad is at the maximum member limit!");
				}
			}
		}
		squadConfig.set("Squads." + squadName + ".Members", existingMembers);
		ExtraConfigHandler.saveConfig(plugin.getDataFolder() + plugin.fileSep + "Squads");
	}
	
	public void removeMember(String squadName, String member) {
		FileConfiguration squadConfig = ExtraConfigHandler.getConfig(plugin.getDataFolder() + plugin.fileSep + "Squads");
		List<String> existingMembers = squadConfig.getStringList("Squads." + squadName + ".Members");
		existingMembers.remove(member);
		squadConfig.set("Squads." + squadName + ".Members", existingMembers);
		ExtraConfigHandler.saveConfig(plugin.getDataFolder() + plugin.fileSep + "Squads");		
	}

}
