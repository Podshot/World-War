package io.github.podshot.commands;

import io.github.podshot.WorldWar;
import io.github.podshot.gui.ClassChooser;
import io.github.podshot.gui.WireGui;
import io.github.podshot.inventories.SaveInventory;
import io.github.podshot.structures.StructureGeneration;
import me.astramg.resources.BlockGenerator;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class TestCommand implements CommandExecutor {

	private static WorldWar plugin = WorldWar.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		boolean ret = false;
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args[0].equalsIgnoreCase("downward")) {
				ret = true;
				player.setFlying(false);
				player.setFlySpeed(1.0F);
			}
			if (args[0].equalsIgnoreCase("Cgui")) {
				ret = true;
				player.openInventory(ClassChooser.getClassChooserGui());
			}
			if (args[0].equalsIgnoreCase("damage")) {
				ret = true;
				player.damage(2.0D);
			}
			if (args[0].equalsIgnoreCase("meta")) {
				ret = true;
				player.setMetadata("WorldWar.Team", new FixedMetadataValue(plugin, args[1].toString()));
			}
			if (args[0].equalsIgnoreCase("getmeta")) {
				ret = true;
				for (MetadataValue val : player.getMetadata(args[1].toString())) {
					if (val.getOwningPlugin().getName().equals("WorldWar")) {
						player.sendMessage("Meta Data Value (" + args[1] + "): " + val.asString());
					}
				}
			}
			if (args[0].equalsIgnoreCase("gen")) {
				ret = true;
				Location loc = player.getLocation();
				//StructureGeneration.generateFlag(loc, "Blue");
				new BlockGenerator(
						new BlockGenerator.BlockLayer(";AGA").setBlockType('G', Material.GLASS),
						new BlockGenerator.BlockLayer("AGA;ZAZ;AG").setBlockType('G', Material.GLASS).setBlockType('Z', Material.STONE),
						new BlockGenerator.BlockLayer("AGA;GAG;AG").setBlockType('G', Material.GLASS),
						new BlockGenerator.BlockLayer(";AGA").setBlockType('G', Material.GLASS)).generateWithTime(plugin, loc.subtract(1, 1, 1), 100L, true);
			}
			if (args[0].equalsIgnoreCase("spawn")) {
				ret = true;
				Location loc = player.getLocation();
				NPCRegistry registry = CitizensAPI.getNPCRegistry();
				NPC npc = registry.createNPC(EntityType.COW, "NPC-Cow");
				npc.spawn(loc);
				npc.getNavigator().setTarget(loc);
			}
			if (args[0].equalsIgnoreCase("fly")) {
				ret = true;
				player.setFlySpeed(0.5F);
			}
			if (args[0].equalsIgnoreCase("flag")) {
				ret = true;
				Location loc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 5, player.getLocation().getZ());
				StructureGeneration.generateFlag(loc, "Blue");
			}
			if (args[0].equalsIgnoreCase("wgui")) {
				ret = true;
				player.openInventory(WireGui.getWireGui());
			}
			if (args[0].equalsIgnoreCase("saveInv")) {
				ret = true;
				new SaveInventory(player.getInventory(), player.getUniqueId());
			}
		}
		return ret;
	}

}
