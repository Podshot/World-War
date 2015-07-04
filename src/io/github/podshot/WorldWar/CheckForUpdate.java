package io.github.podshot.WorldWar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.bukkit.configuration.file.YamlConfiguration;

public class CheckForUpdate {

	public CheckForUpdate(WorldWar plugin) {
		plugin.getLogger().info("Checking for update");
		InputStream in = null;

		URL UPDATE_URL;
		try {
			UPDATE_URL = new URL("https://raw.githubusercontent.com/Podshot/World-War/master/plugin.yml");
			in = UPDATE_URL.openStream();
			
			if (in != null) {
				plugin.logger.info("stream was not null");
				YamlConfiguration definedConfig = YamlConfiguration.loadConfiguration(in);
				plugin.logger.warning("Ver: "+definedConfig.getString("version"));
			} else {
				plugin.logger.info("stream was null");
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
