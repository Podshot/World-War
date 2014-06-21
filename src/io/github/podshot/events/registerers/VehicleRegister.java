package io.github.podshot.events.registerers;

import io.github.podshot.WorldWar;
import io.github.podshot.events.vehicles.BomberEvents;
import io.github.podshot.vehicles.Bomber;

public class VehicleRegister {
	
	public VehicleRegister() {
		WorldWar plugin = WorldWar.getInstance();
		plugin.getServer().getPluginManager().registerEvents(new Bomber(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new BomberEvents(), plugin);
	}

}