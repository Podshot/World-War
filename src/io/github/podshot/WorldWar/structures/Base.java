package io.github.podshot.WorldWar.structures;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Base {
	
	
	public Base(Location loc, String team) {
		World world = loc.getWorld();
		
		int highY = world.getHighestBlockYAt(loc);
		loc.setY(highY);
		
		int x1 = loc.getBlockX();
		int y1 = loc.getBlockY();
		int z1 = loc.getBlockZ();
		
		int x2 = x1 + 9;
		int y2 = y1;
		int z2 = z1 + 9;
		
		for (int xPoint = x1; xPoint <= x2; xPoint++) {
			for (int yPoint = y1; yPoint <= y2; yPoint++) {
				for (int zPoint = z1; zPoint <= z2; zPoint++) {
					Block currentB = world.getBlockAt(xPoint, yPoint, zPoint);
					currentB.setType(Material.SMOOTH_BRICK);
				}
			}
		}
		
		
	}

}
