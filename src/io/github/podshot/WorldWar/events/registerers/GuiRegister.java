package io.github.podshot.WorldWar.events.registerers;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.events.guis.ClassSelectorGUI;
import io.github.podshot.WorldWar.events.guis.SquadInviteGUI;
import io.github.podshot.WorldWar.events.guis.WireCutGUI;

public class GuiRegister {
	
	public GuiRegister() {
		WorldWar plugin = WorldWar.getInstance();
		plugin.getServer().getPluginManager().registerEvents(new WireCutGUI(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new ClassSelectorGUI(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new SquadInviteGUI(), plugin);
	}

}
