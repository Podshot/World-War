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
			String playersTeamPath = pluginFolder + fileSep + "players-teams.map";
			File playersTeamFile = new File(playersTeamPath);
			
			if (playersTeamFile.exists()) {
				Internals.playersTeamFile = MapSaving.load(playersTeamPath);
			} else {
				File newTeamFile = new File(pluginFolder + fileSep + "players-classes.map");
				newTeamFile.mkdir();
			}
			
			String playersClassPath = pluginFolder + fileSep + "players-classes.map";
			File playersClassFile = new File(playersClassPath);
			
			if (playersClassFile.exists()) {
				Internals.playersClassFile = MapSaving.load(playersClassPath);
			} else {
				File newClassFile = new File(pluginFolder + fileSep + "players-classes.map");
				newClassFile.mkdir();
			}
		}
		
	}
	
	@Override
	public void onDisable() {
		MapSaving.save(Internals.playersTeamFile, pluginFolder + fileSep + "players-teams.map");
		MapSaving.save(Internals.playersClassFile, pluginFolder + fileSep + "players-classes.map");
	}
	
	public static WorldWar getInstance() {
		return instance;
	}

}
