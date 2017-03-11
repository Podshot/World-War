package io.github.podshot.WorldWar.files;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.WorldWarTeam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

public class StructureYAML {

	private static WorldWar plugin = WorldWar.getInstance();
	private static FileConfiguration config;
	private static List<String> list = new ArrayList<String>();
	private static boolean loaded;
	
	public static void loadYAML() {
		File structures = new File(plugin.getDataFolder() + plugin.fileSep + "Structures");
		if (!structures.exists()) {
			ExtraConfigHandler.getConfig("Structures");
			ExtraConfigHandler.saveConfig("Structures");
			config = ExtraConfigHandler.getConfig("Structures");
			addConfigValues();
		}
		config = ExtraConfigHandler.getConfig("Structures");
		loaded = true;
	}
	
	public static boolean isLoaded() {
		return loaded;
	}
	
	public static void saveYAML() {
		ExtraConfigHandler.saveConfig("Structures");
	}
	
	public static void reloadYAML() {
		config = ExtraConfigHandler.reloadConfig("Structures");
	}

	private static void addConfigValues() {
		config.set("Structure.Flag.Red.X", 0);
		config.set("Structure.Flag.Red.Y", 100);
		config.set("Structure.Flag.Red.Z", 0);
		config.set("Structure.Flag.Red.World", null);

		config.set("Structure.Flag.Blue.X", 0);
		config.set("Structure.Flag.Blue.Y", 100);
		config.set("Structure.Flag.Blue.Z", 0);
		config.set("Structure.Flag.Blue.World", null);
		
		config.set("Structure.Vehicle.Spawn.0-100-0", "Blaze");
	}
	
	public static void setTeamSpawnpoint(Location loc, String team) {
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		World world = loc.getWorld();
		
		config.set("Structure.Spawnpoint." + team + ".X", x);
		config.set("Structure.Spawnpoint." + team + ".Y", y);
		config.set("Structure.Spawnpoint." + team + ".Z", z);
		config.set("Structure.Spawnpoint." + team + ".World", world.getName());
	}
	
	public static Location getTeamSpawnpoint(String team) {
		int x = config.getInt("Structure.Spawnpoint."+team+".X");
		int y = config.getInt("Structure.Spawnpoint."+team+".Y");
		int z = config.getInt("Structure.Spawnpoint."+team+".Z");
		String name = config.getString("Structure.Spawnpoint."+team+".World");
		World world = Bukkit.getWorld(name);
		Location loc = new Location(world, x, y, z);
		return loc;
	}

	public static Location getFlagPostition(WorldWarTeam team) {
		int x = config.getInt("Structure.Flag." + team.toString() + ".X");
		int y = config.getInt("Structure.Flag." + team.toString() + ".Y");
		int z = config.getInt("Structure.Flag." + team.toString() + ".Z");
		String name = config.getString("Structure.Flag." + team.toString() + ".World");
		if (name == null) {
			name = Bukkit.getServer().getWorlds().get(0).getName();
		}
		plugin.logger.info("World Name: " + name);
		World world = Bukkit.getServer().getWorld(name);
		Location loc = new Location(world, x, y, z);
		return loc;
	}
	
	public static void setFlagLocation(Location flag, String team) {
		
		int x = flag.getBlockX();
		int y = flag.getBlockY();
		int z = flag.getBlockZ();
		World w = flag.getWorld();
		
		config.set("Structure.Flag." + team + ".X", x);
		config.set("Structure.Flag." + team + ".Y", y);
		config.set("Structure.Flag." + team + ".Z", z);
		config.set("Structure.Flag." + team + ".World", w.getName());
		
	}
	
	
	public static void saveMortar(List<Location> mortars) {
		String strLoc;
		config.set("SpecialBlocks.Mortars", mortars);
	}
	
	//public static List<Location> loadMortars() {
		//List<Location> mortars = new ArrayList<Location>();
		//for (boolean mortar : )
		//return null;
	//}
}
