package io.github.podshot.WorldWar.events.registerers;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.events.Mortar;
import io.github.podshot.WorldWar.events.blocks.ClassChanger;

public class BlockRegister {
	
	public BlockRegister() {
		WorldWar plugin = WorldWar.getInstance();
		plugin.getServer().getPluginManager().registerEvents(new Mortar(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new ClassChanger(), plugin);
	}

}
