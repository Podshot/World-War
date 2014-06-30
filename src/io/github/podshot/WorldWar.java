package io.github.podshot;

import io.github.podshot.commands.SquadCommand;
import io.github.podshot.commands.TestCommand;
import io.github.podshot.commands.WorldWarCommand;
import io.github.podshot.commands.tabcompleters.*;
import io.github.podshot.events.BlockEvents;
import io.github.podshot.events.EntityEvents;
import io.github.podshot.events.GuiEvents;
import io.github.podshot.events.PlayerEvents;
import io.github.podshot.events.guns.GunSwitch;
import io.github.podshot.events.guns.KeepGun;
import io.github.podshot.events.registerers.BlockRegister;
import io.github.podshot.events.registerers.GuiRegister;
import io.github.podshot.events.registerers.GunRegister;
import io.github.podshot.events.registerers.StructureRegister;
import io.github.podshot.files.PlayerDataYAML;
import io.github.podshot.files.StructureYAML;
import io.github.podshot.internals.Internals;
import io.github.podshot.safeguards.PreventProfanity;
import io.github.podshot.squads.RejoinSquadOnLogOn;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCDataStore;
import net.citizensnpcs.api.npc.NPCRegistry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;


@SuppressWarnings("unused")
public final class WorldWar extends JavaPlugin {

	public Logger logger;
	public String fileSep;
	private String pluginFolder;
	private File pluginFolderF;
	private DisguiseCraftAPI dcAPI;
	private boolean generate;
	private static WorldWar instance;
	public boolean debug = true;

	@Override
	public void onEnable() {
		instance = this;
		this.logger = this.getLogger();
		this.fileSep = File.separator;
		this.pluginFolder = this.getDataFolder() + fileSep;
		this.pluginFolderF = new File(pluginFolder);
		if (!pluginFolderF.exists()) {
			this.generateFiles();
		}

		this.getCommand("ww").setExecutor(new WorldWarCommand());
		this.getCommand("ww").setTabCompleter(new WorldWarCommandTabCompleter());
		this.getCommand("squad").setExecutor(new SquadCommand());
		this.getCommand("squad").setTabCompleter(new SquadCommandTabCompleter());
		if (debug) {
			this.getCommand("test").setExecutor(new TestCommand());
			this.getCommand("test").setTabCompleter(new TestCommandTabCompleter());
		}
		new GunRegister();
		new GuiRegister();
		//new BlockRegister();
		new StructureRegister();
		//this.getServer().getPluginManager().registerEvents(new GunEvents(), this);
		this.getServer().getPluginManager().registerEvents(new PreventProfanity(), this);
		this.getServer().getPluginManager().registerEvents(new EntityEvents(), this);
		this.getServer().getPluginManager().registerEvents(new GuiEvents(), this);
		this.getServer().getPluginManager().registerEvents(new BlockEvents(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
		this.getServer().getPluginManager().registerEvents(new RejoinSquadOnLogOn(), this);
		this.getServer().getPluginManager().registerEvents(new GunSwitch(), this);
		this.getServer().getPluginManager().registerEvents(new KeepGun(), this);
		PreventProfanity.getWordList();
		
		ExtraConfigHandler.initalize(this);
		new StructureYAML();
		new PlayerDataYAML();
		
		FileConfiguration config = this.getConfig();
		boolean isWarD = config.getBoolean("War-Declared");
		Internals.setWarDeclared(isWarD);

		this.setupDC();
		if (Internals.isWarDeclared()) {
			this.setMetaData();
		}
	}

	private void generateFiles() {
		this.pluginFolderF.mkdir();
		this.saveDefaultConfig();
		File playerInventoriesFolder = new File("Player-Inventory-Backup");
		playerInventoriesFolder.mkdir();
		new LoadDependencies(this);
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
		for (Player p : Bukkit.getOnlinePlayers()) {
			for (MetadataValue data : p.getMetadata("WorldWar.Team")) {
				if (data.getOwningPlugin().getName().equals("WorldWar")) {
					PlayerDataYAML.setPlayerToTeam(p.getUniqueId(), data.asString());
				}
			}
		}
		FileConfiguration config = this.getConfig();
		if (Internals.isWarDeclared()) {
			config.set("War-Declared", true);
		} else {
			config.set("War-Declared", false);
		}
		this.saveConfig();
	}

	public static WorldWar getInstance() {
		return instance;
	}

	private void setupDC() {
		this.dcAPI = DisguiseCraft.getAPI();
	}
	
	public DisguiseCraftAPI getDCAPI() {
		return this.dcAPI;
	}
}
