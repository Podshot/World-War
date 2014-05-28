package io.github.podshot.events.registerers;

import io.github.podshot.WorldWar;
import io.github.podshot.events.structures.Wall;

public class StructureRegister {
	
	public StructureRegister() {
		WorldWar plugin = WorldWar.getInstance();
		plugin.getServer().getPluginManager().registerEvents(new Wall(), plugin);
	}

}
