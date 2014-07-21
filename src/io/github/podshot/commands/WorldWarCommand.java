package io.github.podshot.commands;

import io.github.podshot.WorldWar;
//import io.github.podshot.addons.AddonFinder;
//import io.github.podshot.addons.AddonHandler;
import io.github.podshot.handlers.WarHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldWarCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private WorldWar plugin = WorldWar.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean ret = false;
		if (label.equalsIgnoreCase("ww")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args[0].equalsIgnoreCase("start")) {
					if (player.hasPermission("worldwar.war.start")) {
						ret = true;
						WarHandler.startWar(player.getWorld());
					}
				}
			}
		}
		return ret;
	}

}
