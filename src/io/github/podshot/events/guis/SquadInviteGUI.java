package io.github.podshot.events.guis;

import java.util.UUID;

import io.github.podshot.api.SquadAPI;
import io.github.podshot.handlers.PlayerHandler;
import io.github.podshot.internals.Internals;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SquadInviteGUI implements Listener {

	@EventHandler
	public void onSelectChoice(InventoryClickEvent evt) {
		if (Internals.isWarDeclared()) {
			Player player = (Player) evt.getWhoClicked();
			ItemStack itemClicked = evt.getCurrentItem();
			Inventory inv = evt.getInventory();
			String squadSplitPhase1 = ChatColor.stripColor(inv.getName());
			if (squadSplitPhase1.startsWith("Invite to join Squad: ")) {
				evt.setCancelled(true);
				player.closeInventory();
				if (PlayerHandler.SquadGUIHandler.isPlayerInList(player.getUniqueId())) {
					PlayerHandler.SquadGUIHandler.removePlayer(player.getUniqueId());
				}
				String squadSplitPhase2 = squadSplitPhase1.replace("Invite to join Squad: ", "");
				player.sendMessage(squadSplitPhase2);
				if (itemClicked.getType() == Material.WOOL) {
					short durability = itemClicked.getDurability();
					if (durability == (short) 5) {
						this.accept(squadSplitPhase2, player);
					} else if (durability == (short) 14) {
						this.reject(squadSplitPhase2, player);
					}
				}
			}

		}
	}
	
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent evt) {
		if (Internals.isWarDeclared()) {
			Player player = (Player) evt.getPlayer();
			Inventory inv = evt.getInventory();
			String squadSplitPhase1 = ChatColor.stripColor(inv.getName());
			if (squadSplitPhase1.startsWith("Invite to join Squad: ")) {
				if (PlayerHandler.SquadGUIHandler.isPlayerInList(player.getUniqueId())) {
					PlayerHandler.SquadGUIHandler.removePlayer(player.getUniqueId());
				}
				player.setGameMode(GameMode.SURVIVAL);
				String squadSplitPhase2 = squadSplitPhase1.replace("Invite to join Squad: ", "");
				this.reject(squadSplitPhase2, player);
			}
		}
	}
	
	private void accept(String squadName, Player player) {
		player.sendMessage(ChatColor.GREEN + "You have joined the \"" + squadName + "\" squad");
		UUID uuid = SquadAPI.getLeader(squadName);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getUniqueId().equals(uuid)) {
				p.sendMessage(ChatColor.GREEN + player.getName() + " has joined your Squad!");
			}
		}
		SquadAPI.addMember(squadName, player.getUniqueId());
	}
	
	private void reject(String squadName, Player player) {
		UUID uuid = SquadAPI.getLeader(squadName);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getUniqueId().equals(uuid)) {
				p.sendMessage(ChatColor.GREEN + player.getName() + " has denied your request");
			}
		}
	}

}
