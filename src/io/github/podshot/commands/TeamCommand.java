package io.github.podshot.commands;

import io.github.podshot.BattleStatistics;
import io.github.podshot.api.PlayerAPI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		boolean toReturn = false;
		if (sender instanceof Player) {
			Player player = (Player) sender;
			String team = PlayerAPI.getTeam(player);
			if (args.length >= 1) {
				switch(args[0].toLowerCase()) {
				default:
					break;
				case "info":
					if (team.equals("Blue")) {
						List<String> message = new ArrayList<String>();
						message.add(ChatColor.AQUA + "=====Info for Blue Team=====");
						message.add(ChatColor.AQUA + "-Team Deaths: " + BattleStatistics.getBlueDeaths());
						message.add(ChatColor.AQUA + "-Team Kills (Players): " + BattleStatistics.getBluePlayerKills());
						message.add(ChatColor.AQUA + "-Team Kills (Monsters): " + BattleStatistics.getBlueMonsterKills());
						String[] msg = message.toArray(new String[message.size()]);
						player.sendMessage(msg);
					} else {
						List<String> message = new ArrayList<String>();
						message.add(ChatColor.RED + "=====Info for Red Team=====");
						message.add(ChatColor.RED + "-Team Deaths: " + BattleStatistics.getRedDeaths());
						message.add(ChatColor.RED + "-Team Kills (Players): " + BattleStatistics.getRedPlayerKills());
						message.add(ChatColor.RED + "-Team Kills (Monsters): " + BattleStatistics.getRedMonsterKills());
						String[] msg = message.toArray(new String[message.size()]);
						player.sendMessage(msg);
					}
					toReturn = true;
					break;
				}
			}
		} else {
			if (args.length >= 1) {
				switch (args[0].toLowerCase()) {
				case "info":
					if (args.length >= 2) {
						switch (args[1].toLowerCase()) {
						case "blue":
							List<String> messageB = new ArrayList<String>();
							messageB.add(ChatColor.AQUA + "=====Info for Blue Team=====");
							messageB.add(ChatColor.AQUA + "-Team Deaths: " + BattleStatistics.getBlueDeaths());
							messageB.add(ChatColor.AQUA + "-Team Kills (Players): " + BattleStatistics.getBluePlayerKills());
							messageB.add(ChatColor.AQUA + "-Team Kills (Monsters): " + BattleStatistics.getBlueMonsterKills());
							String[] msgB = messageB.toArray(new String[messageB.size()]);
							sender.sendMessage(msgB);
							toReturn = true;
							break;
						case "red":
							List<String> messageR = new ArrayList<String>();
							messageR.add(ChatColor.RED + "=====Info for Red Team=====");
							messageR.add(ChatColor.RED + "-Team Deaths: " + BattleStatistics.getRedDeaths());
							messageR.add(ChatColor.RED + "-Team Kills (Players): " + BattleStatistics.getRedPlayerKills());
							messageR.add(ChatColor.RED + "-Team Kills (Monsters): " + BattleStatistics.getRedMonsterKills());
							String[] msgR = messageR.toArray(new String[messageR.size()]);
							sender.sendMessage(msgR);
							toReturn = true;
							break;
						default:
							break;
						}
					} else {
						List<String> message = new ArrayList<String>();
						message.add(ChatColor.AQUA + "=====Info for Blue Team=====");
						message.add(ChatColor.AQUA + "-Team Deaths: " + BattleStatistics.getBlueDeaths());
						message.add(ChatColor.AQUA + "-Team Kills (Players): " + BattleStatistics.getBluePlayerKills());
						message.add(ChatColor.AQUA + "-Team Kills (Monsters): " + BattleStatistics.getBlueMonsterKills());
						message.add(ChatColor.RED + "=====Info for Red Team=====");
						message.add(ChatColor.RED + "-Team Deaths: " + BattleStatistics.getRedDeaths());
						message.add(ChatColor.RED + "-Team Kills (Players): " + BattleStatistics.getRedPlayerKills());
						message.add(ChatColor.RED + "-Team Kills (Monsters): " + BattleStatistics.getRedMonsterKills());
						String[] msg = message.toArray(new String[message.size()]);
						sender.sendMessage(msg);
						toReturn = true;
					}
					break;
				default:
					break;
				}
			}
		}
		return toReturn;
	}
}
