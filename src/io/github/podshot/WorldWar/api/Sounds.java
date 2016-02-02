package io.github.podshot.WorldWar.api;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Sounds {
	
	public static void PlayRifleSound(Player player) {
		player.playSound(player.getLocation(), Sound.FIREWORK_BLAST, 0.5f, 0.6f);
	}
	
	public static void PlayRocketSound(Player player) {
		player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 1, 0.1f);
	}

}
