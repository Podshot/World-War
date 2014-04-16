package io.github.podshot.structures;

import io.github.podshot.WorldWar;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

public class FlagHolder {

	private static WorldWar plugin = WorldWar.getInstance();

	private static FileConfiguration flagPlace;

	public static void createFiles() {
		File structures = new File(plugin.getDataFolder() + plugin.fileSep + "Structures");
		File flagsFile = new File(plugin.getDataFolder() + plugin.fileSep + "Structures" + plugin.fileSep + "Flag");
		if (!structures.exists()) {
			structures.mkdir();
			if (!flagsFile.exists()) {
				addConfigValues();
			}
		}
	}

	private static void addConfigValues() {
		flagPlace = ExtraConfigHandler.getConfig(plugin.fileSep + "Structures" + plugin.fileSep + "Flag");
		flagPlace.set("Flag.Red.X", 0);
		flagPlace.set("Flag.Red.Y", 100);
		flagPlace.set("Flag.Red.Z", 0);

		flagPlace.set("Flag.Blue.X", 0);
		flagPlace.set("Flag.Blue.Y", 100);
		flagPlace.set("Flag.Blue.Z", 0);

		ExtraConfigHandler.saveConfig(plugin.fileSep + "Structures" + plugin.fileSep + "Flag");
	}

	public static int getFlagPostition(String coord, String team) {
		int plane = 0;
		if (team.equals("Red")) {
			String parse = "Flag." + team + "." + coord;
			plane = flagPlace.getInt(parse);
		}
		return plane;
	}

}
