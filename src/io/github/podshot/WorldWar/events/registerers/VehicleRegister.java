package io.github.podshot.WorldWar.events.registerers;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.events.vehicles.BomberEvents;

public class VehicleRegister {
	
	public VehicleRegister() {
		WorldWar plugin = WorldWar.getInstance();
		//plugin.getServer().getPluginManager().registerEvents(new Bomber(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new BomberEvents(), plugin);
	}

}
