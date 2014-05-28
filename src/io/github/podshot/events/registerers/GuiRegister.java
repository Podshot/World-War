package io.github.podshot.events.registerers;

import io.github.podshot.WorldWar;
import io.github.podshot.events.guis.ClassSelectorGUI;
import io.github.podshot.events.guis.WireCutGUI;

public class GuiRegister {
	
	public GuiRegister() {
		WorldWar plugin = WorldWar.getInstance();
		plugin.getServer().getPluginManager().registerEvents(new WireCutGUI(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new ClassSelectorGUI(), plugin);
	}

}
