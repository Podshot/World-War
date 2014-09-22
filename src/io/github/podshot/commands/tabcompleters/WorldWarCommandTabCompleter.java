package io.github.podshot.commands.tabcompleters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class WorldWarCommandTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {
		List<String> list = new ArrayList<>();
		
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("worldwar")) {
				if (args.length == 0) {
					list.add("start");
					list.add("debug");
					
					Collections.sort(list);
					
					return list;
				} else if (args.length == 1) {
					list.add("start");
					list.add("debug");
					
					for (String s : list) {
						if (!s.toLowerCase().startsWith(args[0].toLowerCase())) {
							list.remove(s);
						}
					}
					
					Collections.sort(list);
					return list;
					
				}
			}
		}
		return null;
	}

}
