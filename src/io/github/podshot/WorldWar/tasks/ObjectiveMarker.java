package io.github.podshot.WorldWar.tasks;

import java.util.List;
import java.util.UUID;

import me.bigteddy98.firework.FireworkExplosionPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ObjectiveMarker implements Runnable {
	
	private List<UUID> members;
	private String squadName;
	private Location location;
	
	public ObjectiveMarker(Location loc, String squad, List<UUID> members) {
		this.members = members;
		this.squadName = squad;
		this.location = loc.add(0, 25, 0);
	}

	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (this.members.contains(p.getUniqueId())) {
				FireworkExplosionPlayer.playToPlayer(p, this.location, this.getFireworkEffect());
			}
		}
	}

	private FireworkEffect getFireworkEffect() {
		Type type = Type.BURST;
		Color color1 = Color.RED;
		Color color2 = Color.ORANGE;
		FireworkEffect effect = FireworkEffect.builder().trail(true).withColor(color1).withFade(color2).with(type).trail(true).build();
		return effect;
	}

}
