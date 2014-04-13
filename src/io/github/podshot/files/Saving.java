package io.github.podshot.files;

import io.github.podshot.WorldWar;
import io.github.podshot.internals.Internals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class Saving {
	private static WorldWar plugin = WorldWar.getInstance();

	private static FileOutputStream outputTeam;

	private static FileOutputStream outputClass;

	public static void savePlayerTeamFile(Player[] players) {
		Properties playerTeamFile = new Properties();
		String val = null;
		for (Player p : players) {
			for (MetadataValue data : p.getMetadata("WorldWar.Team")) {
				if (data.getOwningPlugin().getName().equals("World War")) {
					val = data.asString();
				}
			}
			if (val != null) {
				playerTeamFile.setProperty(p.getName(), val);
			}
		}
		try {
			outputTeam = new FileOutputStream("Player-Team.values");
			playerTeamFile.store(outputTeam, null);
		} catch (IOException ioe) {
			plugin.logger.severe("Could not save Player Team File!");
			ioe.printStackTrace();
		} finally {
			if (outputTeam != null) {
				try {
					outputTeam.close();
				} catch (IOException ioe) {
					plugin.logger.warning("Could not close the Output Stream, a resource leak may occur!");
					ioe.printStackTrace();
				}
			}
		}

	}

	public static void loadTeamFile(String playersTeamPath) {
		Properties teamFile = new Properties();
		try {
			teamFile.load(new FileInputStream(playersTeamPath));
		} catch (IOException e) {
			plugin.logger.severe("Encountered a IOException while reading the Team file!");
			e.printStackTrace();
		}
		Internals.playersTeamFile = teamFile;
	}

	public static void savePlayerClassFile(Player[] players) {
		Properties playerClassFile = new Properties();
		// for (Player p : players) {
		// playerClassFile.setProperty(p.getName(), p.getMetadata(arg0));
		// }
		try {
			outputClass = new FileOutputStream("Player-Class.values");
			playerClassFile.store(outputClass, null);
		} catch (IOException ioe) {
			plugin.logger.severe("Could not save Player Class File!");
			ioe.printStackTrace();
		} finally {
			if (outputTeam != null) {
				try {
					outputClass.close();
				} catch (IOException ioe) {
					plugin.logger.warning("Could not close the Output Stream, a resource leak may occur!");
					ioe.printStackTrace();
				}
			}
		}
	}

	public static void loadClassFile(String playerClassPath) {
		Properties classFile = new Properties();
		try {
			classFile.load(new FileInputStream(playerClassPath));
		} catch (IOException e) {
			plugin.logger.severe("Encountered a IOException while reading the Class file!");
			e.printStackTrace();
		}
		Internals.playersClassFile = classFile;

	}
}
