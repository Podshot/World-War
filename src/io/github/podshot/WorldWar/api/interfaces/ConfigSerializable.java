package io.github.podshot.WorldWar.api.interfaces;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigSerializable<T extends ConfigSerializable<T>> {
	
	public T fromConfig(ConfigurationSection section);
	
	public ConfigurationSection toConfig();
}
