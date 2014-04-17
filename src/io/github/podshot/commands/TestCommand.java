package io.github.podshot.commands;

import io.github.podshot.WorldWar;
import io.github.podshot.gui.ClassChooser;
import io.github.podshot.structures.StructureGeneration;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class TestCommand implements CommandExecutor {
	
	private static WorldWar plugin = WorldWar.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		boolean ret = false;
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args[0].equalsIgnoreCase("downward")) {
				ret = true;
				player.setFlying(false);
				player.setFlySpeed(1.0F);
			}
			if (args[0].equalsIgnoreCase("gui")) {
				ret = true;
				player.openInventory(ClassChooser.getClassChooserGui());
			}
			if (args[0].equalsIgnoreCase("damage")) {
				ret = true;
				player.damage(2.0D);
			}
			if (args[0].equalsIgnoreCase("meta")) {
				ret = true;
				player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, args[1].toString()));
			}
			if (args[0].equalsIgnoreCase("getmeta")) {
				ret = true;
				for (MetadataValue val : player.getMetadata("WorldWar.Team")) {
					if (val.getOwningPlugin().getName().equals("WorldWar")) {
						player.sendMessage("Meta Data Value: " + val.asString());
					}
				}
			}
			if (args[0].equalsIgnoreCase("gen")) {
				ret = true;
				Location loc = new Location(player.getWorld(), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
				StructureGeneration.generateFlag(loc, "Blue");
			}
		}
		return ret;
	}

}
