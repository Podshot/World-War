package io.github.podshot.structures;

import io.github.podshot.WorldWar;

import java.io.File;

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

		structureConfig.set("Structure.Flag.Blue.X", 0);
		structureConfig.set("Structure.Flag.Blue.Y", 100);
		structureConfig.set("Structure.Flag.Blue.Z", 0);
		
		

		ExtraConfigHandler.saveConfig(plugin.fileSep + "Structures" + plugin.fileSep + "Structures");
	}

	public static int getFlagPostition(String coord, String team) {
		int plane = 0;
		if (team.equals("Red")) {
			String parse = "Structure.Flag." + team + "." + coord;
			plane = structureConfig.getInt(parse);
		}
		return plane;
	}

}
