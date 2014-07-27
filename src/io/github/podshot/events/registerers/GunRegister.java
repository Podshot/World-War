package io.github.podshot.events.registerers;

import io.github.podshot.WorldWar;
import io.github.podshot.events.guns.Remote;
import io.github.podshot.events.guns.Rifle;
import io.github.podshot.events.guns.RocketLauncher;

public class GunRegister {

	public GunRegister() {
		WorldWar plugin = WorldWar.getInstance();
		plugin.getServer().getPluginManager().registerEvents(new Rifle(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new Remote(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new RocketLauncher(), plugin);
	}

}
