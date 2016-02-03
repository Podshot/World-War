package io.github.podshot.WorldWar.events.blocks;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.gui.BuildingChooser;
import io.github.podshot.WorldWar.handlers.PlayerHandler;
import io.github.podshot.WorldWar.handlers.WarHandler;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class StructurePlace implements Listener {
	
	private WorldWar plugin = WorldWar.getInstance();
	
	@EventHandler
	public void onPlaceStructureBlock(BlockPlaceEvent evt) {
		if (!(WarHandler.isWarDeclared())) {
			return;
		}
		
		if (!(evt.getBlockPlaced().getType() == Material.SPONGE)) {
			return;
		}
		
		final Player placer = evt.getPlayer();
		placer.setGameMode(GameMode.CREATIVE);
		placer.openInventory(BuildingChooser.getBuildingChooserGui());
		PlayerHandler.BuildingHandler.setLocation(placer.getUniqueId(), evt.getBlockPlaced().getLocation());
		PlayerHandler.BuildingGUIHandler.addPlayer(placer.getUniqueId());
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				placer.setGameMode(GameMode.SURVIVAL);
				if (PlayerHandler.BuildingGUIHandler.isPlayerInList(placer.getUniqueId())) {
					placer.closeInventory();
					PlayerHandler.BuildingGUIHandler.removePlayer(placer.getUniqueId());
				}
			}
		}, 1200L);
		
	}

}
