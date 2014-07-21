package io.github.podshot.addons;

import io.github.podshot.WorldWar;

import java.util.ArrayList;
import java.util.List;

public class AddonHandler {
	
	private static WorldWar plugin = WorldWar.getInstance();
	
	private static List<String> addons = new ArrayList<String>();

	public static void addNewAddon(String addonName) {
		if (addons.contains(addonName)) {
			plugin.getLogger().warning("Addon \"" + addonName + "\" has already been enabled!");
		} else {
			addons.add(addonName);
		}
	}
	
	public static boolean isAddonEnabled(String name) {
		boolean ret = false;
		if (addons.contains(name)) {
			ret = true;
		}
		return ret;
	}
	
	public static void removeAddon(String name) {
		if (addons.contains(name)) {
			addons.remove(name);
		} else {
			 plugin.getLogger().warning("Tried to remove Addon \"" + name + "\" but the addon has not been loaded!");
		}
	}
}
