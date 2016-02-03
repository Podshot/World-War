package io.github.podshot.WorldWar.events.registerers;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.events.guns.Remote;
import io.github.podshot.WorldWar.events.guns.Rifle;
import io.github.podshot.WorldWar.events.guns.RocketLauncher;
import io.github.podshot.WorldWar.events.guns.Shotgun;

public class GunRegister {

	public GunRegister() {		
		WorldWar plugin = WorldWar.getInstance();
		plugin.getServer().getPluginManager().registerEvents(new Rifle(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new Remote(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new RocketLauncher(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new Shotgun(), plugin);
	}

}
