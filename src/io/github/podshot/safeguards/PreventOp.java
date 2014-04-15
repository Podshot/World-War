package io.github.podshot.safeguards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PreventOp implements Listener {
	
	@EventHandler
	public void onOpCommand(PlayerCommandPreprocessEvent evt) {
		Player player = evt.getPlayer();
		
		if (evt.getMessage().startsWith("/op")) {
			String[] args = evt.getMessage().split(" ");
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.isOp()) {
					String msg;
					if (args[1] == player.getName()) {
						msg = "Player: " + player.getName() + " just tried to Op themselves!";
					} else {
						msg = "Player: " + player.getName() + " just tried to Op: " + args[1];
					}
					p.sendMessage(msg);
				}
			}
			evt.setCancelled(true);
		}
	}

}
