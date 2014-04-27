package io.github.podshot;

import io.github.podshot.commands.TestCommand;
import io.github.podshot.commands.WorldWarCommand;
import io.github.podshot.events.BlockEvents;
import io.github.podshot.events.EntityEvents;
import io.github.podshot.events.GuiEvents;
import io.github.podshot.events.GunRegister;
import io.github.podshot.events.PlayerEvents;
import io.github.podshot.files.GameData;
import io.github.podshot.files.PlayerYAML;
import io.github.podshot.internals.Internals;
import io.github.podshot.structures.StructureYAML;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;
//import io.github.podshot.files.Saving;

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
		new GunRegister();
		//this.getServer().getPluginManager().registerEvents(new GunEvents(), this);
		this.getServer().getPluginManager().registerEvents(new EntityEvents(), this);
		this.getServer().getPluginManager().registerEvents(new GuiEvents(), this);
		this.getServer().getPluginManager().registerEvents(new BlockEvents(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
		if (!pluginFolderF.exists()) {
			pluginFolderF.mkdir();
			generate = true;
		}
		GameData.init();
		ExtraConfigHandler.initalize(this);
		StructureYAML.createFiles();
		PlayerYAML.createFile();

		if (generate) {
			this.saveDefaultConfig();
		}

		this.setupDC();
		if (Internals.isWarDeclared()) {
			this.setMetaData();
		}

	}

	private void setMetaData() {
		Location blueFlag = StructureYAML.getFlagPostition("Blue");
		Block blue = blueFlag.getBlock();
		blue.setMetadata("WorldWar.TeamFlag", new FixedMetadataValue(instance, "Blue"));

		Location redFlag = StructureYAML.getFlagPostition("Red");
		Block red = redFlag.getBlock();
		red.setMetadata("WorldWar.TeamFlag", new FixedMetadataValue(instance, "Red"));
	}

	@Override
	public void onDisable() {
		//		Saving.savePlayerTeamFile(Bukkit.getOnlinePlayers());
		//		Saving.savePlayerClassFile(Bukkit.getOnlinePlayers());
		for (Player p : Bukkit.getOnlinePlayers()) {
			String name = p.getName();
			for (MetadataValue data : p.getMetadata("WorldWar.Team")) {
				if (data.getOwningPlugin().getName().equals("WorldWar")) {
					PlayerYAML.setPlayerToTeam(name, data.asString());
				}
			}
		}
		GameData.saveProps();
	}

	public static WorldWar getInstance() {
		return instance;
	}

	private void setupDC() {
		dcAPI = DisguiseCraft.getAPI();
	}

}
