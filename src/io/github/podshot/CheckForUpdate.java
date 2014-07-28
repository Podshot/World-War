package io.github.podshot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class CheckForUpdate {


	private String version;
	private String developmentStage;
	private String releaseDate;
	private String targetedBukkitVersion;

	public CheckForUpdate() {
		Properties prop = new Properties();
		BufferedReader in = null;

		URL UPDATE_URL;
		try {

			UPDATE_URL = new URL("https://raw.githubusercontent.com/Podshot/World-War/master/update.properties");
			in = new BufferedReader(new InputStreamReader(UPDATE_URL.openStream()));

			prop.load(in);
			this.version = prop.getProperty("Version");
			this.developmentStage = prop.getProperty("Development-Stage");
			this.releaseDate = prop.getProperty("Release-Date");
			this.targetedBukkitVersion = prop.getProperty("Targeted-Bukkit-Version");
			

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
	
	public String getVersion() {
		return this.version;
	}
	
	public String getDevelopmentStage() {
		return this.developmentStage;
	}
	
	public String getReleaseDate() {
		return this.releaseDate;
	}
	
	public String getTargetedBukkitVersion() {
		return this.targetedBukkitVersion;
	}

}
