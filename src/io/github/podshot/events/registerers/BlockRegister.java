package io.github.podshot.events.registerers;

import io.github.podshot.WorldWar;
import io.github.podshot.events.blocks.ClassChanger;

public class BlockRegister {
	
	public BlockRegister() {
		WorldWar plugin = WorldWar.getInstance();
		plugin.getServer().getPluginManager().registerEvents(new ClassChanger(), plugin);
	}

}
