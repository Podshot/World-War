package io.github.podshot.events.registerers;

import io.github.podshot.WorldWar;
import io.github.podshot.events.bullets.*;
import io.github.podshot.events.guns.*;

public class GunRegister {

	public GunRegister() {
		WorldWar plugin = WorldWar.getInstance();
		plugin.getServer().getPluginManager().registerEvents(new Rifle(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new RifleBullet(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new Remote(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new RocketLauncher(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new RocketBullet(), plugin);
	}

}
