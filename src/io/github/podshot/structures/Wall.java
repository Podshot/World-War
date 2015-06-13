package io.github.podshot.structures;

import io.github.podshot.WorldWar;
import io.github.podshot.api.interfaces.IStructure;
import me.astramg.resources.BlockGenerator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

public class Wall implements IStructure {
	
	private WorldWar plugin = WorldWar.getInstance();
	
	public Wall(Location loc) {
		Bukkit.broadcastMessage(ChatColor.RED + "Wall Contructor Called");
		if (loc == null) {
			Bukkit.broadcastMessage(ChatColor.RED + "Location was null!");
		}
		BlockGenerator phase1 = this.getBlocksArray(Material.COBBLESTONE);
		phase1.generateWithTime(plugin, loc, 200L, true);
	}

	@Override
	public BlockGenerator getBlocksArray(Material mat) {
		BlockGenerator structure = new BlockGenerator(
				new BlockGenerator.BlockLayer(";GGGGGGG").setBlockType('G', mat),
				new BlockGenerator.BlockLayer(";GGGGGGG").setBlockType('G', mat),
				new BlockGenerator.BlockLayer(";GGGGGGG").setBlockType('G', mat),
				new BlockGenerator.BlockLayer(";GGGGGGG").setBlockType('G', mat));
		return structure;
	}

}
