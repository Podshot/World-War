package io.github.podshot;

import io.github.podshot.commands.WorldWarCommand;
import io.github.podshot.events.GunEvents;
import io.github.podshot.files.MapSaving;
import io.github.podshot.internals.Internals;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class WorldWar extends JavaPlugin {
	
	public Logger logger;
	private String fileSep;
	private String pluginFolder;
	private File pluginFolderF;
	private static WorldWar instance;

	@Override
	public void onEnable() {
		instance = this;
		logger = this.getLogger();
		fileSep = File.separator;
		pluginFolder = this.getDataFolder() + fileSep;
		pluginFolderF = new File(pluginFolder);
		this.getCommand("ww").setExecutor(new WorldWarCommand());
		this.getServer().getPluginManager().registerEvents(new GunEvents(), this);
		
		if (!pluginFolderF.exists()) {
			pluginFolderF.mkdir();
			this.saveDefaultConfig();
		} else {
			String playersPath = pluginFolderF + fileSep + "players.map";
			File playersFile = new File(playersPath);
			
			if (playersFile.exists()) {
				Internals.playersTeamFile = MapSaving.load(playersPath);
			}
		}
		
	}
	
	@Override
	public void onDisable() {
		MapSaving.save(Internals.playersTeamFile, pluginFolderF + fileSep + "players.map");
	}
	
	public static WorldWar getInstance() {
		return instance;
	}

}
