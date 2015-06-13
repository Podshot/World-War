package io.github.podshot.safeguards;

import io.github.podshot.WorldWar;
import io.github.podshot.handlers.PlayerHandler;
import io.github.podshot.internals.Internals;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PreventProfanity implements Listener {
	
	private static WorldWar plugin = WorldWar.getInstance();
	private static List<String> wordList;

	@EventHandler
	public void onChatEvent(AsyncPlayerChatEvent evt) {
		boolean positive = false;
		if (Internals.isWarDeclared()) {
			if (evt.isAsynchronous()) {
				String message = evt.getMessage();
				for (String word : wordList) {
					if (message.contains(word)) {
						positive = true;
					}
				}
				
				if (positive) {
					Player sender = evt.getPlayer();
					evt.setCancelled(true);
					sender.kickPlayer(ChatColor.RED + "Please be respectful and not use Profanity");
				}
			}
			Set<Player> players = evt.getRecipients();
			Iterator<Player> iter = players.iterator();
			while (iter.hasNext()) {
				Player p = iter.next();
				if (PlayerHandler.BlockMessagesHandler.isInList(p.getUniqueId())) {
					evt.getRecipients().remove(p);
				}
			}
		}
	}

	public static void getWordList() {
		wordList = plugin.getConfig().getStringList("BlackListed-Words");
	}
}
