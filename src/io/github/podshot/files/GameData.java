package io.github.podshot.files;

import io.github.podshot.WorldWar;
import io.github.podshot.internals.Internals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class GameData {
	private static WorldWar plugin = WorldWar.getInstance();
	static Properties props;
	private static FileOutputStream output;

	public static void init() {
		props = new Properties();
		File propsF = new File(plugin.getDataFolder() + plugin.fileSep
				+ "GameData.props");
		if (!propsF.exists()) {
			try {
				propsF.createNewFile();
			} catch (IOException e) {
				plugin.logger.severe("Could not create new Game Data File!");
				e.printStackTrace();
			}
			writeNewProps();
		}
		try {
			props.load(new FileInputStream(plugin.getDataFolder()
					+ plugin.fileSep + "GameData.props"));
		} catch (IOException e) {
			plugin.logger.severe("Could not load Game Data File!");
			e.printStackTrace();
		}

		readProps();
	}

	private static void writeNewProps() {
		Properties pop = new Properties();
		pop.setProperty("War-Declared", "false");
		try {
			output = new FileOutputStream(plugin.getDataFolder()
					+ plugin.fileSep + "GameData.props");
			pop.store(output, null);
		} catch (IOException e) {
			plugin.logger.severe("Could not create a Game Data File!");
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException ioe) {
					plugin.logger
							.warning("Could not close the Output Stream, a resource leak may occur!");
					ioe.printStackTrace();
				}
			}
		}

	}

	public static void saveProps() {
		Properties saving = new Properties();
		if (Internals.warDeclared) {
			saving.setProperty("War-Declared", "false");
		} else {
			saving.setProperty("War-Declared", "true");
		}
		try {
			output = new FileOutputStream(plugin.getDataFolder()
					+ plugin.fileSep + "GameData.props");
			saving.store(output, null);
		} catch (IOException e) {
			plugin.logger.severe("Could not write to the Game Data File!");
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException ioe) {
					plugin.logger
							.warning("Could not close the Output Stream, a resource leak may occur!");
					ioe.printStackTrace();
				}
			}
		}
	}

	public static void readProps() {
		String war = props.getProperty("War-Declared");
		if (war.equalsIgnoreCase("true")) {
			Internals.warDeclared = true;
		} else {
			Internals.warDeclared = false;
		}
	}

}
