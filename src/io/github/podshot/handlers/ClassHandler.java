package io.github.podshot.handlers;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.MetadataValue;

import com.comphenix.attribute.NbtFactory;
import com.comphenix.attribute.NbtFactory.NbtCompound;

public class ClassHandler {
	
	private static void cleanInventory(Player p) {
		p.getInventory().clear();
		updateInv(p);
	}
	
	@SuppressWarnings("deprecation")
	private static void updateInv(Player p) {
		p.updateInventory();
	}
	
	public static void giveSoldierInventory(Player p) {
		String team = null;
		
		for (MetadataValue val : p.getMetadata("WorldWar.Team")) {
			if (val.getOwningPlugin().getName().equals("WorldWar")) {
				team = val.asString();
			}
		}
		p.sendMessage("Team is: " + team);
		
		
		cleanInventory(p);
		Inventory inv = p.getInventory();
		inv.addItem(ItemStackHandler.getGunItemStack());
		
		ItemStack helmet = NbtFactory.getCraftItemStack(new ItemStack(Material.IRON_HELMET));
		NbtCompound tags = NbtFactory.fromItemTag(helmet);
		tags.putPath("Unbreakable", (byte) 1);
		p.getEquipment().setHelmet(helmet);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta lamChest = (LeatherArmorMeta) chestplate.getItemMeta();
		if (team.equalsIgnoreCase("Blue")) {
			lamChest.setColor(Color.BLUE);		
		} else if (team.equalsIgnoreCase("Red")) {
			lamChest.setColor(Color.RED);
		}
		chestplate.setItemMeta(lamChest);
		p.getEquipment().setChestplate(chestplate);
		
		
		ItemStack legging = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta lamLegging = (LeatherArmorMeta) legging.getItemMeta();
		if (team.equalsIgnoreCase("Blue")) {
			lamLegging.setColor(Color.BLUE);
		} else if (team.equalsIgnoreCase("Red")) {
			lamLegging.setColor(Color.RED);
		}
		legging.setItemMeta(lamLegging);
		p.getEquipment().setLeggings(legging);
		
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta lamBoots = (LeatherArmorMeta) boots.getItemMeta();
		if (team.equalsIgnoreCase("Blue")) {
			lamBoots.setColor(Color.BLUE);
		} else if (team.equalsIgnoreCase("Red")) {
			lamBoots.setColor(Color.RED);
		}
		boots.setItemMeta(lamBoots);
		p.getEquipment().setBoots(boots);
		//p.setItemInHand(ItemStackHandler.getGunItemStack());
	
		
		updateInv(p);
	}
	
	public static void giveSpyInventory(Player p) {
		String team = null;
		
		for (MetadataValue val : p.getMetadata("WorldWar.Team")) {
			if (val.getOwningPlugin().getName().equals("WorldWar")) {
				team = val.asString();
			}
		}
		p.sendMessage("Team is: " + team);
		
		
		cleanInventory(p);
		
		
	}

}
