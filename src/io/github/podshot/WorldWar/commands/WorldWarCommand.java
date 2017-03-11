package io.github.podshot.WorldWar.commands;

//import io.github.podshot.addons.AddonFinder;
//import io.github.podshot.addons.AddonHandler;
import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.events.WarDeclaredEvent;
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
				if (player.hasPermission("worldwar.declare")) {
					if (args.length > 0) {
						if (args[0].equalsIgnoreCase("declare")) {
							WarDeclaredEvent event = null;
							if (args.length == 1) {
								toReturn = true;
								//WarHandler.startWar(player.getWorld());
								String time = plugin.getConfig().getString("default-war-length", "72:00");
								event = new WarDeclaredEvent(time.split(":"));
							} else if (args.length == 2) {
								String time = args[1];
								String[] splitTime = time.split(":");
								if (splitTime.length > 2) {
									String[] newSplit = new String[] {splitTime[0], splitTime[1]};
									event = new WarDeclaredEvent(newSplit);
								}
							}
							if (event != null)
								plugin.getServer().getPluginManager().callEvent(event);
						}
					}
				}
			}
		}
		return toReturn;
	}

}
