package io.github.podshot.WorldWar.api;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.squads.Squad;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

public class SquadAPI {
	
	private static WorldWar plugin = WorldWar.getInstance();
	private static FileConfiguration config;
	private static HashMap<String, Squad> squads = new HashMap<String, Squad>();
	private static HashMap<UUID, Squad> leaders = new HashMap<UUID, Squad>();
	
	public SquadAPI() {
		File squadYaml = new File(plugin.getDataFolder() + plugin.fileSep + "Squads");
		if (!squadYaml.exists()) {
			ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
			ExtraConfigHandler.saveConfig(plugin.fileSep + "Squads");
			config = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
		} else {
			config = ExtraConfigHandler.getConfig(plugin.fileSep + "Squads");
			List<String> allSquads = config.getStringList("Squads.AllSquads");
			for (String squadName : allSquads) {
				Squad parsedSquad = new Squad(squadName, config);
				squads.put(squadName, parsedSquad);
				leaders.put(parsedSquad.getSquadLeader(), parsedSquad);
			}
		}
	}
	
	public static void saveYAML() {
		for (String squadName : squads.keySet()) {
			Squad squad = squads.get(squadName);
			config.set("Squads." + squad.getSquadName() + ".Leader", squad.getSquadLeader());
			config.set("Squads." + squad.getSquadName() + ".Members", squad.getSquadMembers());
			config.set("Squads." + squad.getSquadName() + ".Alligance", squad.getAlligance());
		}
		ExtraConfigHandler.saveConfig(plugin.fileSep + "Squads");
	}
	
	public static void reloadYAML() {
		config = ExtraConfigHandler.reloadConfig(plugin.fileSep + "Squads");
	}
	
	public static boolean squadNameAvailable(String squadName) {
		return !squads.containsKey(squadName);
	}
	
	public static void addMember(String squadName, UUID uuid) {
		if (squads.get(squadName).getSquadMembers().size() < 10) {
			squads.get(squadName).addSquadMember(uuid);
		}
	}
	
	public static void removeMember(String squadName, UUID uuid) {
		squads.get(squadName).removeSquadMember(uuid);
	}
	
	public static void createNewSquad(String squadName, UUID leader, String alligance) {
		squads.put(squadName, new Squad(squadName, leader, alligance));
	}
	
	public static Set<String> getAllSquadNames() {
		return squads.keySet();
	}
	
	public static HashMap<String, Squad> getAllSquads() {
		return squads;
	}
	
	public static boolean isLeader(UUID player) {
		return leaders.containsKey(player);
	}
	
	public static Squad getSquadThePlayerLeads(UUID player) {
		return leaders.get(player);
	}
	
	public static boolean inSquad(UUID player) {
		for (String squadName : squads.keySet()) {
			Squad squad = squads.get(squadName);
			if (squad.getSquadMembers().contains(player)) {
				return true;
			}
		}
		return false;
	}
	
	public static Squad getSquadForPlayer(UUID player) {
		for (String squadName : squads.keySet()) {
			Squad squad = squads.get(squadName);
			if (squad.getSquadMembers().contains(player)) {
				return squad;
			}
		}
		return null;
	}

}
