package io.github.podshot.commands.tabcompleters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TestCommandTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {
		List<String> list = new ArrayList<>();
		
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("test")) {
				if (args.length == 0) {
					list.add("downward");
					list.add("Cgui");
					list.add("damage");
					list.add("meta");
					list.add("getmeta");
					list.add("gen");
					list.add("spawn");
					list.add("fly");
					list.add("wgui");
					
					Collections.sort(list);
					
					return list;
				} else if (args.length >= 1) {
					list.add("downward");
					list.add("Cgui");
					list.add("damage");
					list.add("meta");
					list.add("getmeta");
					list.add("gen");
					list.add("spawn");
					list.add("fly");
					list.add("wgui");
					
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
