package io.github.podshot.WorldWar.commands;

//import io.github.podshot.addons.AddonFinder;
//import io.github.podshot.addons.AddonHandler;
import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.handlers.WarHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldWarCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private WorldWar plugin = WorldWar.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean toReturn = false;
		if (label.equalsIgnoreCase("worldwar")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args[0].equalsIgnoreCase("start")) {
					if (player.hasPermission("worldwar.war.start")) {
						toReturn = true;
						WarHandler.startWar(player.getWorld());
					}
				}
			}
		}
		return toReturn;
	}

}