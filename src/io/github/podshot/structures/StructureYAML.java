package io.github.podshot.structures;

import io.github.podshot.WorldWar;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

public class StructureYAML {

	private static WorldWar plugin = WorldWar.getInstance();

	private static FileConfiguration structureConfig;

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
		String worldName = config.getString("Structure.Flag." + team + ".World");
		World world = Bukkit.getWorld(worldName);
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

}
