package io.github.podshot.handlers;

import java.io.File;
import java.util.List;

import io.github.podshot.WorldWar;
import io.github.podshot.files.BackUp;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class WarHandler {
	private static WorldWar plugin = WorldWar.getInstance();

	public static void startWar(boolean b) {
		if (b) {
			plugin.logger.info("Backing Up the world");
			List<World> sources = Bukkit.getWorlds();
			for (World w : sources) {
				File worldSource = w.getWorldFolder();
				
				File target = new File("Backup-" + w.getName() + "/");
				target.mkdir();
				plugin.logger.info("Backing up World: " + w.getName() + " to the folder Backup-" + w.getName());
				BackUp.copyWorld(worldSource, target);
			}
		}
	}

}
