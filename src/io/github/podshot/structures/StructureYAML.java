package io.github.podshot.structures;

import io.github.podshot.WorldWar;
import io.github.podshot.entities.VehicleType;

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

	private static FileConfiguration structureConfig;
	private static List<String> list = new ArrayList<String>();

	public static void createFiles() {
		File structures = new File(plugin.getDataFolder() + plugin.fileSep + "Structures");
		File flagsFile = new File(plugin.getDataFolder() + plugin.fileSep + "Structures" + plugin.fileSep + "Structures");
		if (!structures.exists()) {
			structures.mkdir();
			if (!flagsFile.exists()) {
				addConfigValues();
			}
		}
	}

	private static void addConfigValues() {
		structureConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Structures" + plugin.fileSep + "Structures");
		structureConfig.set("Structure.Flag.Red.X", 0);
		structureConfig.set("Structure.Flag.Red.Y", 100);
		structureConfig.set("Structure.Flag.Red.Z", 0);
		structureConfig.set("Structure.Flag.Red.World", null);

		structureConfig.set("Structure.Flag.Blue.X", 0);
		structureConfig.set("Structure.Flag.Blue.Y", 100);
		structureConfig.set("Structure.Flag.Blue.Z", 0);
		structureConfig.set("Structure.Flag.Blue.World", null);
		
		structureConfig.set("Structure.Vehicle.Spawn.0-100-0", "Blaze");
		

		ExtraConfigHandler.saveConfig(plugin.fileSep + "Structures" + plugin.fileSep + "Structures");
	}

	public static Location getFlagPostition(String team) {
		FileConfiguration config = ExtraConfigHandler.getConfig(plugin.fileSep + "Structures" + plugin.fileSep + "Structures");
		int x = config.getInt("Structure.Flag." + team + ".X");
		int y = config.getInt("Structure.Flag." + team + ".Y");
		int z = config.getInt("Structure.Flag." + team + ".Z");
		String name = config.getString("Structure.Flag." + team + ".World");
		plugin.logger.info("World Name: " + name);
		World world = Bukkit.getServer().getWorld(name);
		Location loc = new Location(world, x, y, z);
		return loc;
	}
	
	public static void setFlagLocation(Location flag, String team) {
		FileConfiguration flagConfig = ExtraConfigHandler.getConfig(plugin.fileSep + "Structures" + plugin.fileSep + "Structures");
		
		int x = flag.getBlockX();
		int y = flag.getBlockY();
		int z = flag.getBlockZ();
		World w = flag.getWorld();
		
		flagConfig.set("Structure.Flag." + team + ".X", x);
		flagConfig.set("Structure.Flag." + team + ".Y", y);
		flagConfig.set("Structure.Flag." + team + ".Z", z);
		flagConfig.set("Structure.Flag." + team + ".World", w.getName());
		
		ExtraConfigHandler.saveConfig(plugin.fileSep + "Structures" + plugin.fileSep + "Structures");
	}
	
	public static void setVehiclePad(Location loc, VehicleType type) {
		FileConfiguration config = ExtraConfigHandler.getConfig(plugin.fileSep + "Structures" + plugin.fileSep + "Structures");
		
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		World world = loc.getWorld();
		
		//String name = x + ":" + y + ":" + z;
		
		String format = x + ":" + y + ":" + z + ":" + world.getName() + ":" + type;
		list.add(format);
		
		config.set("Structures.Vehicles", list);
		/*
		config.set("Structure.Vehicle." + name + "X", x);
		config.set("Structure.Vehicle." + name + "Y", y);
		config.set("Structure.Vehicle." + name + "Z", z);
		config.set("Structure.Vehicle." + name + "World", world.getName());
		config.set("Structure.Vehicle." + name + "Type", type);
		*/
		
		ExtraConfigHandler.saveConfig(plugin.fileSep + "Structures" + plugin.fileSep + "Structures");
	}

}
