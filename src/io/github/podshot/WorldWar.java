package io.github.podshot;

import io.github.podshot.commands.TestCommand;
import io.github.podshot.commands.WorldWarCommand;
import io.github.podshot.events.EntityEvents;
import io.github.podshot.events.GuiEvents;
import io.github.podshot.events.GunEvents;
import io.github.podshot.files.GameData;
import io.github.podshot.files.Saving;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

public class WorldWar extends JavaPlugin {
	
	public Logger logger;
	public String fileSep;
	private String pluginFolder;
	private File pluginFolderF;
	public DisguiseCraftAPI dcAPI;
	private boolean generate;
	private static WorldWar instance;
	public boolean debug = true;

	@Override
	public void onEnable() {
		instance = this;
		logger = this.getLogger();
		fileSep = File.separator;
		pluginFolder = this.getDataFolder() + fileSep;
		pluginFolderF = new File(pluginFolder);
		this.getCommand("ww").setExecutor(new WorldWarCommand());
		if (debug) {
			this.getCommand("test").setExecutor(new TestCommand());
		}
		this.getServer().getPluginManager().registerEvents(new GunEvents(), this);
		this.getServer().getPluginManager().registerEvents(new EntityEvents(), this);
		this.getServer().getPluginManager().registerEvents(new GuiEvents(), this);
		if (!pluginFolderF.exists()) {
			pluginFolderF.mkdir();
			generate = true;
		}
		GameData.init();
		
		if (generate) {
			this.saveDefaultConfig();
		} else {
			String playersTeamPath = pluginFolder + fileSep + "players-teams.map";
			File playersTeamFile = new File(playersTeamPath);
			
			if (playersTeamFile.exists()) {
				Saving.loadTeamFile(playersTeamPath);
			} else {
				File newTeamFile = new File(pluginFolder + fileSep + "players-teams.map");
				try {
					newTeamFile.createNewFile();
				} catch (IOException e) {
					logger.severe("Could not create Player Team Map file!");
					e.printStackTrace();
				}
			}
			
			String playersClassPath = pluginFolder + fileSep + "players-classes.map";
			File playersClassFile = new File(playersClassPath);
			
			if (playersClassFile.exists()) {
				Saving.loadClassFile(playersClassPath);
			} else {
				File newClassFile = new File(pluginFolder + fileSep + "players-classes.map");
				try {
					newClassFile.createNewFile();
				} catch (IOException e) {
					logger.severe("Could not create Player Class Map file!");
					e.printStackTrace();
				}
			}
		}
		
		this.setupDC();
		
	}
	
	@Override
	public void onDisable() {
		Saving.savePlayerTeamFile(Bukkit.getOnlinePlayers());
		Saving.savePlayerClassFile(Bukkit.getOnlinePlayers());
		GameData.saveProps();
	}
	
	public static WorldWar getInstance() {
		return instance;
	}
	
	private void setupDC() {
		dcAPI = DisguiseCraft.getAPI();
	}

}
