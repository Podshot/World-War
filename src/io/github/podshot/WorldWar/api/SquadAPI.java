package io.github.podshot.WorldWar.api;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.squads.Squad;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

public class SquadAPI {
	
	private static WorldWar plugin = WorldWar.getInstance();
	private static FileConfiguration config;
	private static HashMap<String, Squad> squads = new HashMap<String, Squad>();
	private static HashMap<UUID, Squad> leaders = new HashMap<UUID, Squad>();
	private static boolean loaded;
	
	public static void loadYAML() {
		plugin.logger.info(plugin.getDataFolder() + plugin.fileSep + "Squads.yml");
		File squadYaml = new File(plugin.getDataFolder() + plugin.fileSep + "Squads.yml");
		plugin.logger.info("Exists: "+squadYaml.exists());
		if (!squadYaml.exists()) {
			ExtraConfigHandler.getConfig("Squads");
			ExtraConfigHandler.saveConfig("Squads");
			config = ExtraConfigHandler.getConfig("Squads");
			config.createSection("Squads");
		} else {
			config = ExtraConfigHandler.getConfig("Squads");
			Set<String> allSquads = config.getConfigurationSection("Squads").getKeys(false);
			for (String squadName : allSquads) {
				Squad parsedSquad = new Squad(squadName, UUID.fromString(config.getString("Squads."+squadName+".Leader")), config.getStringList("Squads."+squadName+".Members"), config.getString("Squads."+squadName+".Alligance"));
				plugin.logger.warning("Contains: "+config.contains("Squads." + parsedSquad.getSquadName() + ".Objective"));
				if (config.contains("Squads." + parsedSquad.getSquadName() + ".Objective")) {
					World world = Bukkit.getWorld(UUID.fromString(config.getString("Squads." + parsedSquad.getSquadName() + ".Objective.World")));
					parsedSquad.addObjective(config.getVector("Squads." + parsedSquad.getSquadName() + ".Objective.Vector").toLocation(world));
				}
				squads.put(squadName, parsedSquad);
				leaders.put(parsedSquad.getSquadLeader(), parsedSquad);
			}
		}
		loaded = true;
	}
	
	public static boolean isLoaded() {
		return loaded;
	}
	
	public static void saveYAML() {
		plugin.logger.info(squads.toString());
		for (String squadName : squads.keySet()) {
			Squad squad = squads.get(squadName);
			config.set("Squads." + squad.getSquadName() + ".Leader", squad.getSquadLeader().toString());
			List<String> members = new ArrayList<String>();
			for (UUID member : squad.getSquadMembers()) {
				members.add(member.toString());
			}
			config.set("Squads." + squad.getSquadName() + ".Members", members);
			config.set("Squads." + squad.getSquadName() + ".Alligance", squad.getAlligance());
			if (squad.hasObjective()) {
				config.set("Squads." + squad.getSquadName() + ".Objective.Vector", squad.getObjectiveLocation().subtract(0, 25, 0).toVector());
				config.set("Squads." + squad.getSquadName() + ".Objective.World", squad.getObjectiveLocation().getWorld().getUID().toString());
			}
		}
		ExtraConfigHandler.saveConfig("Squads");
	}
	
	public static void reloadYAML() {
		config = ExtraConfigHandler.reloadConfig(plugin.fileSep + "Squads");
	}
	
	public static boolean squadNameAvailable(String squadName) {
		return !squads.containsKey(squadName);
	}
	
	public static Squad getSquadFromName(String squadName) {
		return squads.get(squadName);
	}
	
	public static void createNewSquad(String squadName, UUID leader, String alligance) {
		Squad squad = new Squad(squadName, leader, alligance);
		squads.put(squadName, squad);
		leaders.put(leader, squad);
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
			if (squad.getSquadMembers().contains(player) || squad.getSquadLeader().equals(player)) {
				return true;
			}
		}
		return false;
	}
	
	public static Squad getSquadForPlayer(UUID player) {
		for (String squadName : squads.keySet()) {
			Squad squad = squads.get(squadName);
			if (squad.getSquadMembers().contains(player) || squad.getSquadLeader().equals(player)) {
				return squad;
			}
		}
		return null;
	}
	
	public static void disbandSquad(String squadName) {
		Squad squad = SquadAPI.getSquadFromName(squadName);
		config.set("Squads." + squad.getSquadName(), null);
		squads.remove(squadName);
		squad.removeObjective();
		for (UUID uuid : squad.getSquadMembers()) {
			squad.removeSquadMember(uuid);
		}
	}
	
	public static void removeObjective(Squad squad) {
		config.set("Squads." + squad.getSquadName() + ".Objective", null);
	}

}
