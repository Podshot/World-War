package io.github.podshot.events;

import io.github.podshot.WorldWar;
import io.github.podshot.Vehicles.Bomber;

public class VehicleRegister {
	
	public VehicleRegister() {
		WorldWar plugin = WorldWar.getInstance();
		plugin.getServer().getPluginManager().registerEvents(new Bomber(), plugin);
	}

}
