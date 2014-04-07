package io.github.podshot.commands;

import io.github.podshot.WorldWar;
import io.github.podshot.handlers.WarHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WorldWarCommand implements CommandExecutor {
	
	private WorldWar plugin = WorldWar.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean ret = false;
		if (label.equalsIgnoreCase("ww")) {
			plugin.logger.info("Hi");
			if (args[0].equalsIgnoreCase("start")) {
				ret = true;
				WarHandler.startWar(true);
			}
		}
		return ret;
	}

}
