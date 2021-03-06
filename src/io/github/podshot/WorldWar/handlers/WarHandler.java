package io.github.podshot.WorldWar.handlers;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.events.WarDeclaredEvent;
//import io.github.podshot.WorldWar.api.WarEndCause;
//import io.github.podshot.WorldWar.api.events.WarDeclaredEvent;
import io.github.podshot.WorldWar.files.BackUp;
import io.github.podshot.WorldWar.structures.StructureGeneration;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WarHandler implements Listener {
	
	private static WorldWar plugin = WorldWar.getInstance();
	private static boolean warDeclared = false;
	
	@EventHandler
	public void onDeclareWar(WarDeclaredEvent evt) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule keepInventory true");
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.saveData();
		}
		plugin.logger.info("Backing Up the world");
		List<World> sources = Bukkit.getWorlds();
		for (World w : sources) {
			File worldSource = w.getWorldFolder();

			File backupFolder = new File("Backups/");
			backupFolder.mkdir();
			File target = new File("Backups/" + w.getName() + "/");
			target.mkdir();
			plugin.logger.info("Backing up World: " + w.getName() + " to the folder Backup/" + w.getName());
			BackUp.copyWorld(worldSource, target);
		}
		int x = randomCoords(0, 30);
		int z = randomCoords(0, 30);

		int Nx = x * -1;
		int Nz = z * -1;

		Location bBase = new Location(world, x, 100, z);
		StructureGeneration.generateFlag(bBase, "Blue");
		Location rBase = new Location(world, Nx, 100, Nz);
		StructureGeneration.generateFlag(rBase, "Red");


		warDeclared = true;
		
	}

	@Deprecated
	public static void startWar(World world) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule keepInventory true");
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.saveData();
		}
		plugin.logger.info("Backing Up the world");
		List<World> sources = Bukkit.getWorlds();
		for (World w : sources) {
			File worldSource = w.getWorldFolder();

			File backupFolder = new File("Backups/");
			backupFolder.mkdir();
			File target = new File("Backups/" + w.getName() + "/");
			target.mkdir();
			plugin.logger.info("Backing up World: " + w.getName() + " to the folder Backup/" + w.getName());
			BackUp.copyWorld(worldSource, target);
		}
		
		//plugin.getServer().getPluginManager().callEvent(new WarDeclaredEvent());
		//int x = randomCoords(1000, 100000);
		//int z = randomCoords(1000, 100000);
		int x = randomCoords(0, 30);
		int z = randomCoords(0, 30);

		int Nx = x * -1;
		int Nz = z * -1;

		Location bBase = new Location(world, x, 100, z);
		StructureGeneration.generateFlag(bBase, "Blue");
		Location rBase = new Location(world, Nx, 100, Nz);
		StructureGeneration.generateFlag(rBase, "Red");


		warDeclared = true;
	}

	private static int randomCoords(int min, int max) {

		Random random = new Random();

		int randomNum = random.nextInt((max - min) + 1) + min;
		return randomNum;

	}
	
	@Deprecated
	public static void endWar(WarEndCause cause) {
		warDeclared = false;
		
		plugin.logger.info("War has Ended");
		
		switch(cause) {
		case FLAG_CAPTURED:
			Bukkit.getServer().broadcastMessage("");
			break;
		case TIME_RAN_OUT:
			Bukkit.getServer().broadcastMessage("");
			break;
		default:
			break;
		}
	}
	
	public static boolean isWarDeclared() {
		return warDeclared;
	}
	
	public static void setWarDeclared(boolean bool) {
		warDeclared = bool;
	}
	
	public enum WarEndCause {
		FLAG_CAPTURED, TIME_RAN_OUT;
	}
}
