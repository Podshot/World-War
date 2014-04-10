package io.github.podshot.commands;

import io.github.podshot.Debug;
import io.github.podshot.WorldWar;
import io.github.podshot.handlers.WarHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldWarCommand implements CommandExecutor {

	private WorldWar plugin = WorldWar.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean ret = false;
		if (label.equalsIgnoreCase("ww")) {
			if (sender instanceof Player) {
				plugin.logger.info("Hi");
				if (args[0].equalsIgnoreCase("start")) {
					if (sender.hasPermission("worldwar.war.start")) {
						ret = true;
						WarHandler.startWar(true);
					}
				} else if (args[0].equalsIgnoreCase("debug")) {
					if (sender.hasPermission("worldwar.war.debug")) {
						if (args[1].equalsIgnoreCase("rifle")) {
							Debug.givePlayerRifle((Player) sender);
						}
					}
				}
			}
		}
		return ret;
	}

}
