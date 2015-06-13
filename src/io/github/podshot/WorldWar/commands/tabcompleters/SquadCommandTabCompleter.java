package io.github.podshot.WorldWar.commands.tabcompleters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class SquadCommandTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {
		List<String> list = new ArrayList<>();

		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("squad")) {
				if (args.length == 0) {
					list.add("create");
					list.add("disband");
					list.add("invite");
					list.add("kick");
					list.add("leave");

					Collections.sort(list);

					return list;
				} else if (args.length == 1) {
					list.add("create");
					list.add("disband");
					list.add("invite");
					list.add("kick");
					list.add("leave");
					
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
