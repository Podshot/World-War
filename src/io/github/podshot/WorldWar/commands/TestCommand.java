package io.github.podshot.WorldWar.commands;

import java.util.Collection;

import io.github.podshot.WorldWar.WorldWar;
import io.github.podshot.WorldWar.api.GunType;
import io.github.podshot.WorldWar.api.PlayerAPI;
import io.github.podshot.WorldWar.events.guns.Rifle;
import io.github.podshot.WorldWar.events.guns.RocketLauncher;
import io.github.podshot.WorldWar.events.guns.Shotgun;
import io.github.podshot.WorldWar.events.guns.SniperRifle;
import io.github.podshot.WorldWar.gui.WireGui;
import io.github.podshot.WorldWar.inventories.InventoryManager;
import io.github.podshot.WorldWar.structures.Base;
import me.astramg.resources.BlockGenerator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

public class TestCommand implements CommandExecutor {

	private static WorldWar plugin = WorldWar.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		boolean toReturn = false;
		if (sender instanceof Player) {
			final Player player = (Player) sender;

			if (args.length >= 1) {
				switch (args[0]) {
				case "meta":
					toReturn = true;
					if (args.length >= 2) {
						player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, args[1].toString()));
					}
					break;
				case "getmeta":
					toReturn = true;
					if (args.length >= 2) {
						for (MetadataValue val : player.getMetadata(args[1].toString())) {
							if (val.getOwningPlugin().getName().equals("WorldWar")) {
								player.sendMessage("Meta Data Value (" + args[1] + "): " + val.asString());
							}
						}
					}
					break;
				case "gen":
					toReturn = true;
					Location genLOC = player.getLocation();
					//StructureGeneration.generateFlag(loc, "Blue");
					new BlockGenerator(
							new BlockGenerator.BlockLayer(";AGA").setBlockType('G', Material.GLASS),
							new BlockGenerator.BlockLayer("AGA;ZAZ;AG").setBlockType('G', Material.GLASS).setBlockType('Z', Material.STONE),
							new BlockGenerator.BlockLayer("AGA;GAG;AG").setBlockType('G', Material.GLASS),
							new BlockGenerator.BlockLayer(";AGA").setBlockType('G', Material.GLASS)).generateWithTime(plugin, genLOC.subtract(1, 1, 1), 100L, true);
					break;
				case "Wgui":
					toReturn = true;
					player.openInventory(WireGui.getWireGui());
					break;
				case "saveInv":
					toReturn = true;
					final String inv = InventoryManager.InventoryToString(player.getInventory());
					player.sendMessage(inv);
					player.getInventory().clear();
					Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

						@Override
						public void run() {
							player.getInventory().setContents(InventoryManager.StringToInventory(inv).getContents());
						}
						
					}, 1200);
					break;
				case "guns":
					toReturn = true;
					if (args.length >= 2) {
						switch (args[1]) {
						default:
							break;
						case "rifle":
							player.getInventory().addItem(Rifle.getGun());
							break;
						case "rocket":
							player.getInventory().addItem(RocketLauncher.getGun());
							break;
						case "sniper":
							player.getInventory().addItem(SniperRifle.getGun());
							break;
						case "shotgun":
							player.getInventory().addItem(Shotgun.getGun());
							break;
						}
					}
					break;
				case "base":
					toReturn = true;
					new Base(player.getLocation().add(0, -3, 0), "Blue");
					break;
				case "ammo":
					toReturn = true;
					player.sendMessage(ChatColor.YELLOW + "" + PlayerAPI.getAmmoAmount(player, GunType.RIFLE));
					break;
				case "bar":
					toReturn = true;
					if (args.length == 2) {
						Collection<BossBar> bars = BossBarAPI.getBossBars(player);
						BossBar bar = (BossBar) bars.toArray()[0];
						bar.setProgress(((float)Integer.parseInt(args[1])) / 120f);
					}
					break;
				default:
					toReturn = false;
					break;
				}
			}
		}
		return toReturn;
	}

}
