package io.github.podshot.commands;

import io.github.podshot.gui.ClassChooser;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {

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
		}
		return ret;
	}

}
