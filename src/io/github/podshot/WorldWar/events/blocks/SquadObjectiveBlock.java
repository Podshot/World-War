package io.github.podshot.WorldWar.events.blocks;

import io.github.podshot.WorldWar.api.SquadAPI;
import io.github.podshot.WorldWar.api.interfaces.ISpecialBlock;
import io.github.podshot.WorldWar.internals.Internals;
import io.github.podshot.WorldWar.squads.Squad;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SquadObjectiveBlock implements ISpecialBlock {

	@Override
	@EventHandler
	public void onClickBlock(PlayerInteractEvent evt) {
		return;
	}

	@Override
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent evt) {
		if (!(Internals.isWarDeclared())) {
			return;
		}
		
		if (evt.getBlockPlaced().equals(getSpecialBlock())) {
			return;
		}
		
		if (!(SquadAPI.isLeader(evt.getPlayer().getUniqueId()))) {
			return;
		}
		
		Squad squad = SquadAPI.getSquadThePlayerLeads(evt.getPlayer().getUniqueId());
		squad.addObjective(evt.getBlockPlaced().getLocation());
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if (squad.getSquadMembers().contains(player.getUniqueId())) {
				player.getPlayer().setCompassTarget(evt.getBlockPlaced().getLocation());
			}
		}
		evt.setCancelled(true);
		
	}

	@Override
	@EventHandler
	public void onBreakBlock(BlockBreakEvent evt) {
		return;		
	}
	
	public static ItemStack getSpecialBlock() {
		ItemStack block = new ItemStack(Material.BEACON);
		ItemMeta blockIM = block.getItemMeta();
		blockIM.setDisplayName(ChatColor.GOLD + "Set Squad Objective");
		block.setItemMeta(blockIM);
		return block;
	}

}
