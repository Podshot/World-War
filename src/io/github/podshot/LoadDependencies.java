package io.github.podshot;

import java.io.File;

public class LoadDependencies {

	public LoadDependencies(WorldWar plugin) {
		File jsonJarFile = new File(plugin.getDataFolder() + plugin.fileSep + "json-simple-1.1.1.jar");
		if (jsonJarFile.exists()) {
			return;
		}
		plugin.saveResource("json-simple-1.1.1.jar", false);
	}
}
