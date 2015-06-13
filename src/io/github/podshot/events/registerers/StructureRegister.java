package io.github.podshot.events.registerers;

import io.github.podshot.WorldWar;
import io.github.podshot.events.blocks.StructurePlace;
import io.github.podshot.events.guis.BuildingChooserGUI;

@SuppressWarnings("unused")
@Deprecated
public class StructureRegister {
	
	public StructureRegister() {
		WorldWar plugin = WorldWar.getInstance();
		//plugin.getServer().getPluginManager().registerEvents(new StructurePlace(), plugin);
		//plugin.getServer().getPluginManager().registerEvents(new BuildingChooserGUI(), plugin);
	}

}
