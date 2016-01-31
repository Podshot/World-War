package io.github.podshot.WorldWar.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.github.podshot.WorldWar.handlers.PlayerClasses;

import org.bukkit.entity.Player;

public class Engineer {
	
	private static PlayerClasses type = PlayerClasses.Engineer;
	private static List<UUID> engineers = new ArrayList<UUID>();

	public Engineer(Player player, boolean remove) {
		if (remove) {
			if (engineers.contains(player.getUniqueId())) {
				engineers.remove(player.getUniqueId());
			}
		} else {
			return;
		}
	}

}
