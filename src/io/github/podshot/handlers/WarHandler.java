package io.github.podshot.handlers;

import io.github.podshot.WorldWar;
import io.github.podshot.files.BackUp;
import io.github.podshot.internals.Internals;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class WarHandler {
	private static WorldWar plugin = WorldWar.getInstance();

	public static void startWar(boolean b) {
		if (b) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"gamerule keepInventory true");
			plugin.logger.info("Backing Up the world");
			List<World> sources = Bukkit.getWorlds();
			for (World w : sources) {
				File worldSource = w.getWorldFolder();

				File backupFolder = new File("Backups/");
				backupFolder.mkdir();
				File target = new File("Backups/" + w.getName() + "/");
				target.mkdir();
				plugin.logger.info("Backing up World: " + w.getName()
						+ " to the folder Backup/" + w.getName());
				BackUp.copyWorld(worldSource, target);
			}

			Internals.warDeclared = true;
		}
	}

}
