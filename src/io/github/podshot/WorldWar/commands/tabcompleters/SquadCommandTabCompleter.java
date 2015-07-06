package io.github.podshot.WorldWar.commands.tabcompleters;

import io.github.podshot.WorldWar.api.SquadAPI;
import io.github.podshot.WorldWar.squads.Squad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class SquadCommandTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {
		List<String> list = new ArrayList<>();

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("squad")) {
				if (args.length == 0) {
					list.add("create");
					list.add("disband");
					list.add("invite");
					list.add("kick");
					list.add("leave");
					list.add("objective");

					Collections.sort(list);

					return list;
				} else if (args.length == 1) {
					list.add("create");
					list.add("disband");
					list.add("invite");
					list.add("kick");
					list.add("leave");
					list.add("objective");
					
					for (String s : list) {
						if (!s.toLowerCase().startsWith(args[0].toLowerCase())) {
							list.remove(s);
						}
					}
					
					Collections.sort(list);
					
					return list;
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("objective")) {
						list.add("create");
						list.add("modify");
						list.add("remove");
						
						for (String s : list) {
							if (!s.toLowerCase().startsWith(args[1].toLowerCase())) {
								list.remove(s);
							}
						}
						
						Collections.sort(list);
						
						return list;
					} else if (args[0].equalsIgnoreCase("kick")) {
						Squad squad = SquadAPI.getSquadForPlayer(player.getUniqueId());
						for (UUID member: squad.getSquadMembers()) {
							list.add(Bukkit.getPlayer(member).getDisplayName());
						}
						for (String s : list) {
							if (!s.toLowerCase().startsWith(args[1].toLowerCase())) {
								list.remove(s);
							}
						}
						
						Collections.sort(list);
						
						return list;
					}
				}
			}
		}
		return null;
	}

}
