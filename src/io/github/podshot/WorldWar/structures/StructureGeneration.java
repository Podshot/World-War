package io.github.podshot.WorldWar.structures;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.files.StructureYAML;

import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class StructureGeneration {
	
	private static WorldWar plugin = WorldWar.getInstance();
	
	@SuppressWarnings("deprecation")
	public static void generateFlag(Location flagLoc, String team) {
		// World Generation Checks
		World world = flagLoc.getWorld();
		
		Chunk flagChunk = world.getChunkAt(flagLoc.getBlockX(), flagLoc.getBlockZ());
		flagChunk.load(true);
		// Generates flag at highest point
		int newY = world.getHighestBlockYAt(flagLoc.getBlockX(), flagLoc.getBlockZ()) + 2;
		flagLoc.setY(newY);
		// Flag Block
		Block flag = flagLoc.getBlock();
		if (team.equals("Blue")) {
			flag.setType(Material.WOOL);
			flag.setData(DyeColor.BLUE.getData());
			flag.setMetadata("WorldWar.TeamFlag", new FixedMetadataValue(plugin, "Blue"));
		} else if (team.equals("Red")) {
			flag.setType(Material.WOOL);
			flag.setData(DyeColor.RED.getData());
			flag.setMetadata("WorldWar.TeamFlag", new FixedMetadataValue(plugin, "Red"));
		}
		StructureYAML.setFlagLocation(flagLoc, team);
		
		// Flag Post
		flagLoc.setY(flagLoc.getY() - 1);
		Block post = flagLoc.getBlock();
		post.setType(Material.FENCE);
		
		flagLoc.setX(flagLoc.getX() - 1);
		flagLoc.setY(flagLoc.getY() - 1);
		flagLoc.setZ(flagLoc.getZ() - 1);
		
		int x1 = flagLoc.getBlockX();
		int y1 = flagLoc.getBlockY();
		int z1 = flagLoc.getBlockZ();
		
		int x2 = x1 + 2;
		int y2 = y1;
		int z2 = z1 + 2;
		
		
		for (int xPoint = x1; xPoint <= x2; xPoint++) {
			for (int yPoint = y1; yPoint <= y2; yPoint++) {
				for (int zPoint = z1; zPoint <= z2; zPoint++) {
					Block currentB = world.getBlockAt(xPoint, yPoint, zPoint);
					currentB.setType(Material.GLOWSTONE);
				}
			}
		}
		
	}
	
	public void generateVehiclePlatform(Location loc) {
		World world = loc.getWorld();
		
		int newY = world.getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()) + 2;
		loc.setY(newY);
		
		int x1 = loc.getBlockX();
		int y1 = loc.getBlockY();
		int z1 = loc.getBlockZ();
		
		int x2 = x1 + 2;
		int y2 = y1;
		int z2 = z1 + 2;
		
		
		for (int xPoint = x1; xPoint <= x2; xPoint++) {
			for (int yPoint = y1; yPoint <= y2; yPoint++) {
				for (int zPoint = z1; zPoint <= z2; zPoint++) {
					Block currentB = world.getBlockAt(xPoint, yPoint, zPoint);
					currentB.setType(Material.OBSIDIAN);
				}
			}
		}
		
		loc.getBlock().setType(Material.SPONGE);
	}
	
	public void generateAirAtBase(Location loc) {
		
		int x1 = loc.getBlockX();
		int y1 = loc.getBlockY();
		int z1 = loc.getBlockZ();
		
		int x2 = x1 + 9;
		int y2 = y1 + 5;
		int z2 = z1 + 9;
		
		World world = loc.getWorld();
		
		for (int xPoint = x1; xPoint <= x2; xPoint++) {
			for (int yPoint = y1; yPoint <= y2; yPoint++) {
				for (int zPoint = z1; zPoint <= z2; zPoint++) {
					Block currentB = world.getBlockAt(xPoint, yPoint, zPoint);
					currentB.setType(Material.AIR);
				}
			}
		}
	}

}
