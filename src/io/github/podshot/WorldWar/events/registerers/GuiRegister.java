package io.github.podshot.WorldWar.events.registerers;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.events.guis.SquadInviteGUI;
import io.github.podshot.WorldWar.events.guis.TeamChooserGUI;
import io.github.podshot.WorldWar.events.guis.WireCutGUI;

public class GuiRegister {
	
	public static void register(WorldWar plugin) {
		plugin.getServer().getPluginManager().registerEvents(new WireCutGUI(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new SquadInviteGUI(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new TeamChooserGUI(), plugin);
	}

}
