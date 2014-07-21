package io.github.podshot.addons;

import io.github.podshot.WorldWar;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class AddonFinder {

	private WorldWar plugin = WorldWar.getInstance();
	private static WorldWar splugin = WorldWar.getInstance();

	public AddonFinder() {
		File addonFolder = new File(plugin.getDataFolder() + "Addons");
		if (!(addonFolder.exists())) {
			addonFolder.mkdir();
		}
		File[] addons = getAddons(addonFolder);
		this.loadAddons(addons);
	}

	@SuppressWarnings("resource")
	private void loadAddons(File[] addons) {
		for (File addon : addons) {
			JarFile jar = null;
			try {
				jar = new JarFile(addon);
				JarEntry entry = jar.getJarEntry("addon.yml");
				FileConfiguration addonConfig = YamlConfiguration.loadConfiguration(jar.getInputStream(entry));
				URL url = addon.toURI().toURL();
				URL[] urls = new URL[]{url};
				Class<?> cls = new URLClassLoader(urls).loadClass(addonConfig.getString("Addon-Main-Class"));
				AddonHandler.addNewAddon(addonConfig.getString("Addon-Name"));
				plugin.getLogger().info("Enabling Addon \"" + addonConfig.getString("Addon-Name") + "\"");
				initializeAddon(cls);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void initializeAddon(Class<?> cls) {
		Method enable = null;
		try {
			enable = cls.getDeclaredMethod("enable");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (enable != null) {
			try {
				enable.invoke(cls.newInstance());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
				e.printStackTrace();
			}
		}

	}

	private static File[] getAddons(final File folder) {
		return folder.listFiles(new FilenameFilter() {
			public boolean accept(File folder, String filename) {
				return filename.endsWith(".jar");
			}
		});
	}

	@SuppressWarnings("resource")
	public static void disableAddon(String name) {

		File addon = null;
		FileConfiguration possibleAddonConfig = null;
		if (AddonHandler.isAddonEnabled(name)) {
			AddonHandler.removeAddon(name);
			for (File possibleAddon : getAddons(new File(splugin.getDataFolder() + "Addons"))) {
				JarFile jar = null;
				try {
					jar = new JarFile(possibleAddon);
					JarEntry entry = jar.getJarEntry("addon.yml");
					possibleAddonConfig = YamlConfiguration.loadConfiguration(jar.getInputStream(entry));

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (possibleAddonConfig != null) {
					if (possibleAddonConfig.getString("Addon-Name").equals(name)) {
						addon = possibleAddon;
					}
				}
			}
			JarFile jar = null;
			if (addon != null) {
				try {
					jar = new JarFile(addon);
					JarEntry entry = jar.getJarEntry("addon.yml");
					FileConfiguration addonConfig = YamlConfiguration.loadConfiguration(jar.getInputStream(entry));
					URL url = addon.toURI().toURL();
					URL[] urls = new URL[]{url};
					Class<?> cls = new URLClassLoader(urls).loadClass(addonConfig.getString("Addon-Main-Class"));
					AddonHandler.addNewAddon(addonConfig.getString("Addon-Name"));
					splugin.getLogger().info("disabling Addon \"" + addonConfig.getString("Addon-Name") + "\"");
					Method enable = null;
					enable = cls.getDeclaredMethod("enable");
					if (enable != null) {
							enable.invoke(cls.newInstance());
					}
				} catch (ClassNotFoundException | IOException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
					splugin.getLogger().severe("Could not disable the Addon \"" + name + "\"");
					e.printStackTrace();
				}
			}
		}
	}
}

