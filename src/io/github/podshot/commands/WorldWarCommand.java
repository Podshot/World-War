package io.github.podshot.commands;

import io.github.podshot.Debug;
import io.github.podshot.WorldWar;
import io.github.podshot.handlers.ItemStackHandler;
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
				} else if (args[0].equalsIgnoreCase("debug")) {
					if (player.hasPermission("worldwar.war.debug")) {
						if (args[1].equalsIgnoreCase("rifle")) {
							Debug.givePlayerRifle((Player) sender);
						}
						if (args[1].equalsIgnoreCase("launcher")) {
							Debug.givePlayerlauncher((Player) sender);
						}
						if (args[1].equalsIgnoreCase("diffuser")) {
							Player p = (Player) sender;
							p.getInventory().addItem(ItemStackHandler.getBombDiffuser());
						}
					}
				}
			}
		}
		return ret;
	}

}
