package io.github.podshot.WorldWar.api;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Sounds {
	
	public static void playRifleSound(Player player) {
		player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 0.5f, 0.6f);
	}
	
	public static void playRocketSound(Player player) {
		player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1, 0.1f);
	}

}
