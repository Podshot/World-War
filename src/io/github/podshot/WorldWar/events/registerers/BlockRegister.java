package io.github.podshot.WorldWar.events.registerers;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.events.Mortar;

public class BlockRegister {
	
	public static void register(WorldWar plugin) {
		plugin.getServer().getPluginManager().registerEvents(new Mortar(), plugin);
	}
}
