package io.github.podshot.safeguards;

import io.github.podshot.internals.Internals;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PreventProfanity implements Listener {

	@EventHandler
	public void onChatEvent(AsyncPlayerChatEvent evt) {
		boolean positive = false;
		if (Internals.isWarDeclared()) {
			if (evt.isAsynchronous()) {
				String message = evt.getMessage();
				Player sender = evt.getPlayer();
				if (message.contains("fuck") || message.contains("dick")) {
					positive = true;
				} else if (message.contains("ass") || message.contains("asshole")) {
					positive = true;
				} else if (message.contains("penis") || message.contains("vagina")) {
					
				}
				
				
				
				
				if (positive) {
					evt.setCancelled(true);
					sender.kickPlayer(ChatColor.RED + "Please be respectful and not use Profanity");
				}
			}
		}
	}
}
