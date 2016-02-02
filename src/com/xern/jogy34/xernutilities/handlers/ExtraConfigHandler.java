/**
 * @author  Jogy34
 * 
 * NMS/OBC: None
 * Xern-Util Reliances: None
 * 
 * Notes: 
 * Allows the creation and handling of a relatively infinite number of custom config files
 */

package com.xern.jogy34.xernutilities.handlers;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ExtraConfigHandler
{
    protected static JavaPlugin plugin = null;
    
    /**
     * Initalizes the ConfigHandler so it has a referance to the plugin that it using it
     * 
     * @param jp The plugin that is using this class
     */
    public static void initalize(JavaPlugin jp)
    {
        plugin = jp;
    }
    
    protected static HashMap<String, FileConfiguration> configs = new HashMap<String, FileConfiguration>();
    
    /**
     * Reloads the configuration for the given id. Similar to the bukkit
     * JavaPlugin reloadConfig() method
     * 
     * @param id Essentially the name of the custom config but it is also used to store the FileConfiguration
     * @return The custom FileConfiguration that was either created or reloaded.
     */
    @SuppressWarnings("deprecation")
	public static FileConfiguration reloadConfig(String id)
    {
        File customConfigFile = new File(plugin.getDataFolder(), id + ".yml");
        FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        
        InputStream defConfigStream = plugin.getResource(id + ".yml");
        if (defConfigStream != null) 
        {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            customConfig.setDefaults(defConfig);
        }
        
        
        configs.put(id, customConfig);
        
        return customConfig;
    }
    
    /**
     * Retrieves any previously created FileConfiguration or creates a new one if one doesn't exist
     * 
     * @param id Essentially the name of the custom config but it is also used to store the FileConfiguration
     * @return The newely or previously created FileConfiguration
     */
    public static FileConfiguration getConfig(String id) 
    {
        if(configs.containsKey(id))
        {
            return configs.get(id);
        }
        return reloadConfig(id);
    }
    
    /**
     * Saves the FileCondfiguration to an actual file
     * 
     * @param id Essentially the name of the custom config but it is also used to store the FileConfiguration
     */
    public static void saveConfig(String id) 
    {
        try 
        {
            getConfig(id).save(new File(plugin.getDataFolder(), id + ".yml"));
        } 
        catch (Exception ex) 
        { }
    }
}

