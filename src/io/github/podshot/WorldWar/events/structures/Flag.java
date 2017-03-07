package io.github.podshot.WorldWar.events.structures;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.PlayerAPI;
import io.github.podshot.WorldWar.api.WorldWarTeam;
import io.github.podshot.WorldWar.files.StructureYAML;
import io.github.podshot.WorldWar.handlers.WarHandler;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Flag implements Listener {
	
	private static WorldWar plugin = WorldWar.getInstance();
	
	private Location blueFlag;
	private Location redFlag;
	
	public Flag() {
		this.blueFlag = StructureYAML.getFlagPostition(WorldWarTeam.BLUE);
		this.redFlag = StructureYAML.getFlagPostition(WorldWarTeam.RED);
	}
	
	public void onBreakFlag(BlockBreakEvent evt) {
		if (!(WarHandler.isWarDeclared())) {
			return;
		}
		
		Block brokenBlock = evt.getBlock();
		if (!(brokenBlock.getType() == Material.WOOL)) {
			return;
		}
		
		WorldWarTeam teamFlagBroken;
		if (brokenBlock.getLocation().equals(blueFlag)) {
			teamFlagBroken = WorldWarTeam.BLUE;
		} else if (brokenBlock.getLocation().equals(redFlag)) {
			teamFlagBroken = WorldWarTeam.RED;
		} else {
			return;
		}
		
		Player player = evt.getPlayer();
		WorldWarTeam team = PlayerAPI.getTeam(player);
		if (team == WorldWarTeam.BLUE && teamFlagBroken == WorldWarTeam.BLUE) {
			evt.setCancelled(true);
			player.sendMessage(ChatColor.RED + "You cannot break your own Team's flag");
		} else if (team == WorldWarTeam.RED && teamFlagBroken == WorldWarTeam.RED) {
			evt.setCancelled(true);
			player.sendMessage(ChatColor.RED + "You cannot break your own Team's flag");
		} else {
			
		}
	}
}
