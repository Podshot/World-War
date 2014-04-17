package io.github.podshot.handlers;

import io.github.podshot.WorldWar;
import io.github.podshot.files.BackUp;
import io.github.podshot.internals.Internals;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WarHandler {
	private static WorldWar plugin = WorldWar.getInstance();

	@SuppressWarnings("unused")
	public static void startWar(boolean b) {
		if (b) {
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
			
			int x = randomCoords(1000, 100000);
			int z = randomCoords(1000, 100000);
			
			int Nx = x * -1;
			int Nz = z * -1;
			

			Internals.warDeclared = true;
		}
	}
	
	private static int randomCoords(int min, int max) {
		
		Random random = new Random();
		
		int randomNum = random.nextInt((max - min) + 1) + min;
		return randomNum;
		
	}

}
