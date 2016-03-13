package io.github.podshot.WorldWar.events;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.gui.WireGui;
import io.github.podshot.WorldWar.handlers.WarHandler;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftSkull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class BlockEvents implements Listener {

	private WorldWar plugin = WorldWar.getInstance();

	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt) {
		//if (Internals.warDeclared) {
		if (evt.getBlock().getType() == Material.SKULL) {
			if (evt.getPlayer().getItemInHand().getType() == Material.SHEARS) {
				if (evt.getPlayer().getItemInHand().getItemMeta().getDisplayName().toString().equals("Bomb Diffuser")) {
					String coord = evt.getBlock().getWorld().getName() + ":" + evt.getBlock().getX() + ":" + evt.getBlock().getY() + ":" + evt.getBlock().getZ();
					evt.getPlayer().setMetadata("WorldWar.bombToDiffuse", new FixedMetadataValue(this.plugin, coord));
					evt.getPlayer().openInventory(WireGui.getWireGui());
					evt.setCancelled(true);
				} else {
					evt.setCancelled(true);
					return;
				}
			} else {
				evt.setCancelled(true);
				return;
			}
			return;
		}
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
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent evt) {
		if (!(WarHandler.isWarDeclared())) {
			return;
		}
		
		if (evt.getBlockPlaced().getType() == Material.SKULL) {
			ArrayList<String> lore = new ArrayList<String>();
			Block explosive = evt.getBlockPlaced();
			CraftSkull skExplosive = new CraftSkull(explosive);
			skExplosive.setOwner("MHF_TNT2");
			skExplosive.update();
			
			int x = explosive.getLocation().getBlockX();
			int y = explosive.getLocation().getBlockY();
			int z = explosive.getLocation().getBlockZ();
			String world = explosive.getLocation().getWorld().getName();
			
			lore.add("X: " + x);
			lore.add("Y: " + y);
			lore.add("Z: " + z);
			lore.add("World: " + world);
			
			Player placer = evt.getPlayer();
			
			ItemStack remote = new ItemStack(Material.RECORD_11);
			ItemMeta imRemote = remote.getItemMeta();
			imRemote.setDisplayName("Remote");
			imRemote.setLore(lore);
			remote.setItemMeta(imRemote);
			
			placer.setItemInHand(remote);
			placer.updateInventory();
		}
	}

}
