package io.github.podshot.events;

import java.util.ArrayList;

import io.github.podshot.WorldWar;
import io.github.podshot.internals.Internals;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;

public class BlockEvents implements Listener {

	private static WorldWar plugin = WorldWar.getInstance();

	@EventHandler
	public  void onBlockBreak(BlockBreakEvent evt) {
		//if (Internals.warDeclared) {
		if (evt.getBlock().getType() == Material.WOOL) {
			String teamFlag = null;
			String teamCapturer = null;
			Block wool = evt.getBlock();
			Player capturer = evt.getPlayer();
			for (MetadataValue val : wool.getMetadata("WorldWar.TeamFlag")) {
				if (val.getOwningPlugin().getName().equals("WorldWar")) {
					teamFlag = val.asString();
				}
			}
			for (MetadataValue data : capturer.getMetadata("WorldWar.Team")) {
				if (data.getOwningPlugin().getName().equals("WorldWar")) {
					teamCapturer = data.asString();
				}
			}

			plugin.logger.info("Flag Capturer: " + teamCapturer);
			plugin.logger.info("Team Flag: " + teamFlag);

			if (teamFlag != null && teamCapturer != null) {
				if (!(teamFlag.equals(teamCapturer))) {
					plugin.getServer().broadcastMessage("Team: " + ChatColor.GOLD + teamCapturer + ChatColor.RESET + " has captured the " + ChatColor.GOLD + teamFlag + ChatColor.RESET + " Flag!");
					plugin.getServer().broadcastMessage("The flag was captured by " + ChatColor.GOLD + capturer.getName());
				} else {
					evt.setCancelled(true);
					capturer.sendMessage(ChatColor.RED + "You cannot capture your own flag!");
				}
			}
		}
		//}
		return;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent evt) {
		if (!(Internals.warDeclared)) {
			return;
		}
		
		if (evt.getBlockPlaced().getType() == Material.SKULL) {
			ArrayList<String> lore = new ArrayList<String>();
			Skull explosive = (Skull) evt.getBlockPlaced();
			explosive.setOwner("MHF_TNT2");
			
			int x = explosive.getLocation().getBlockX();
			int y = explosive.getLocation().getBlockY();
			int z = explosive.getLocation().getBlockZ();
			String world = explosive.getLocation().getWorld().getName();
			
			lore.add("X: " + x);
			lore.add("Y: " + y);
			lore.add("Z: " + z);
			lore.add("World: " + world);
			
			Player placer = evt.getPlayer();
			Inventory inv = placer.getInventory();
			
			ItemStack remote = new ItemStack(Material.RECORD_11);
			ItemMeta imRemote = remote.getItemMeta();
			imRemote.setDisplayName("Remote");
			imRemote.setLore(lore);
			remote.setItemMeta(imRemote);
			
			inv.addItem(remote);
			placer.updateInventory();
		}
	}

}
