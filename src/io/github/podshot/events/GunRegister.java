package io.github.podshot.events;

import io.github.podshot.WorldWar;
import io.github.podshot.events.guns.Rifle;

public class GunRegister {

	public GunRegister() {
		WorldWar plugin = WorldWar.getInstance();
		plugin.getServer().getPluginManager().registerEvents(new Rifle(), plugin);
	}

}