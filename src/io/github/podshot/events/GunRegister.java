package io.github.podshot.events;

import io.github.podshot.WorldWar;
import io.github.podshot.events.bullets.RifleBullet;
import io.github.podshot.events.guns.Remote;
import io.github.podshot.events.guns.Rifle;

public class GunRegister {

	public GunRegister() {
		WorldWar plugin = WorldWar.getInstance();
		plugin.getServer().getPluginManager().registerEvents(new Rifle(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new RifleBullet(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new Remote(), plugin);
	}

}
