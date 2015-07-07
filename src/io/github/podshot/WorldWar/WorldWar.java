package io.github.podshot.WorldWar;

import io.github.podshot.WorldWar.api.SquadAPI;
import io.github.podshot.WorldWar.commands.SquadCommand;
import io.github.podshot.WorldWar.commands.TeamCommand;
import io.github.podshot.WorldWar.commands.TestCommand;
import io.github.podshot.WorldWar.commands.WorldWarCommand;
import io.github.podshot.WorldWar.commands.tabcompleters.SquadCommandTabCompleter;
import io.github.podshot.WorldWar.commands.tabcompleters.TeamCommandTabCompleter;
import io.github.podshot.WorldWar.commands.tabcompleters.WorldWarCommandTabCompleter;
import io.github.podshot.WorldWar.events.BlockEvents;
import io.github.podshot.WorldWar.events.EntityEvents;
import io.github.podshot.WorldWar.events.PlayerEvents;
import io.github.podshot.WorldWar.events.guns.GunSwitch;
import io.github.podshot.WorldWar.events.guns.KeepGun;
import io.github.podshot.WorldWar.events.registerers.BlockRegister;
import io.github.podshot.WorldWar.events.registerers.GuiRegister;
import io.github.podshot.WorldWar.events.registerers.GunRegister;
import io.github.podshot.WorldWar.files.PlayerDataYAML;
import io.github.podshot.WorldWar.files.ReadConfig;
import io.github.podshot.WorldWar.files.StructureYAML;
import io.github.podshot.WorldWar.internals.Internals;
import io.github.podshot.WorldWar.players.PlayerSorter;
import io.github.podshot.WorldWar.safeguards.PreventProfanity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

import com.xern.jogy34.xernutilities.handlers.ExtraConfigHandler;


//@SuppressWarnings({ "unused" })
public final class WorldWar extends JavaPlugin {

	public Logger logger;
	public String fileSep;
	private String pluginFolder;
	private File pluginFolderF;
	private DisguiseCraftAPI dcAPI;
	private static WorldWar instance;
	public boolean debug = true;
	private Random random = new Random();

	private String version = this.getDescription().getVersion();
	private boolean needsUpdate = false;

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
		ExtraConfigHandler.initalize(this);

		this.getCommand("worldwar").setExecutor(new WorldWarCommand());
		this.getCommand("worldwar").setTabCompleter(new WorldWarCommandTabCompleter());
		this.getCommand("squad").setExecutor(new SquadCommand());
		this.getCommand("squad").setTabCompleter(new SquadCommandTabCompleter());
		this.getCommand("team").setExecutor(new TeamCommand());
		this.getCommand("team").setTabCompleter(new TeamCommandTabCompleter());
		if (this.debug) {
			this.getCommand("test").setExecutor(new TestCommand());
		}
		new GunRegister();
		new GuiRegister();
		new BlockRegister();
		//new StructureRegister();

		PreventProfanity.getWordList();
		this.getServer().getPluginManager().registerEvents(new PreventProfanity(), this);
		this.getServer().getPluginManager().registerEvents(new EntityEvents(), this);
		this.getServer().getPluginManager().registerEvents(new BlockEvents(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
		this.getServer().getPluginManager().registerEvents(new GunSwitch(), this);
		this.getServer().getPluginManager().registerEvents(new KeepGun(), this);
		this.getServer().getPluginManager().registerEvents(new BattleStatistics(), this);

		new StructureYAML();
		new PlayerDataYAML();
		new SquadAPI();

		FileConfiguration config = this.getConfig();
		new ReadConfig();
		Internals.setWarDeclared(config.getBoolean("War-Declared"));

		this.setupDC();
		//if (Internals.isWarDeclared()) {
		//this.setMetaData();
		//}
		Bukkit.getScheduler().runTaskAsynchronously(this, new UpdateChecker(this));
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			PlayerEvents.doReloadFix(player);
		}
		new PlayerSorter();
		this.logger.info(PlayerSorter.getTeamWithMorePlayers());
	}

	private void generateFiles() {
		this.pluginFolderF.mkdir();
		this.saveDefaultConfig();
		File playerInventoriesFolder = new File(this.getDataFolder() + this.fileSep + "Player-Inventory-Backup");
		playerInventoriesFolder.mkdir();
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
		if (Internals.isWarDeclared()) {
			this.getConfig().set("War-Declared", true);
		} else {
			this.getConfig().set("War-Declared", false);
		}
		this.saveConfig();
		BattleStatistics.saveStatistics();
		PlayerDataYAML.saveYAML();
		StructureYAML.saveYAML();
		SquadAPI.saveYAML();
		instance = null;
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

	public boolean getNeedsUpdate() {
		return this.needsUpdate;
	}

	public Random getRandom() {
		return this.random;
	}
	
	public class UpdateChecker implements Runnable {
		
		private WorldWar caller;
		
		public UpdateChecker(WorldWar caller) {
			this.caller = caller;
		}

		@Override
		public void run() {
			InputStream in = null;
			try {
				in = new URL("https://raw.githubusercontent.com/Podshot/World-War/master/plugin.yml").openStream();
				if (in != null) {
					YamlConfiguration update = YamlConfiguration.loadConfiguration(in);
					caller.needsUpdate = update.getString("version") != caller.version;
				}
			} catch (IOException e) {
				logger.warning("Could not check for update");
				//e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						logger.severe("Could not close update stream, memory leaks may occur!");
						e.printStackTrace();
					}
				}
			}
		}
		
	}
}
