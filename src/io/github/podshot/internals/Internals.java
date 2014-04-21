package io.github.podshot.internals;

import io.github.podshot.files.SavePlayerData;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bukkit.Location;

@SuppressWarnings("deprecation")
public class Internals {

	public static Properties playersTeamFile = SavePlayerData.getTeamProperties();
	public static Properties playersClassFile;
	public static boolean warDeclared = false;
	public static List<Location> explosiveLocations = new ArrayList<Location>();

}
