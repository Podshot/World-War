package io.github.podshot;

import io.github.podshot.commands.WorldWarCommand;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class WorldWar extends JavaPlugin {
	
	public Logger logger;
	private static WorldWar instance;

	@Override
	public void onEnable() {
		instance = this;
		logger = this.getLogger();
		this.getCommand("ww").setExecutor(new WorldWarCommand());
		
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static WorldWar getInstance() {
		return instance;
	}

}
