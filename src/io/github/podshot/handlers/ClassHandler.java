package io.github.podshot.handlers;

import io.github.podshot.events.guns.Rifle;

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
	
	public ClassHandler(Player player, PlayerClasses classSelected) {
		switch (classSelected) {
		default:
			break;
		case Spy:
			this.giveSpyInventory(player);
			break;
		case Soldier:
			this.giveSoldierInventory(player);
		}
	}
	
	private void cleanInventory(Player p) {
		p.getInventory().clear();
		this.updateInv(p);
	}
	
	@SuppressWarnings("deprecation")
	private void updateInv(Player p) {
		p.updateInventory();
	}
	
	private void giveSoldierInventory(Player p) {
		String team = null;
		
		for (MetadataValue val : p.getMetadata("WorldWar.Team")) {
			if (val.getOwningPlugin().getName().equals("WorldWar")) {
				team = val.asString();
			}
		}
		p.sendMessage("Team is: " + team);
		
		
		this.cleanInventory(p);
		Inventory inv = p.getInventory();
		inv.addItem(Rifle.getGun());
		
		ItemStack helmet = NbtFactory.getCraftItemStack(new ItemStack(Material.IRON_HELMET));
		NbtCompound Htags = NbtFactory.fromItemTag(helmet);
		Htags.putPath("Unbreakable", (byte) 1);
		p.getEquipment().setHelmet(helmet);
		
		ItemStack chestplate = NbtFactory.getCraftItemStack(new ItemStack(Material.LEATHER_CHESTPLATE));
		NbtCompound Ctags = NbtFactory.fromItemTag(chestplate);
		Ctags.putPath("Unbreakable", (byte) 1);
		LeatherArmorMeta lamChest = (LeatherArmorMeta) chestplate.getItemMeta();
		if (team.equalsIgnoreCase("Blue")) {
			lamChest.setColor(Color.BLUE);		
		} else if (team.equalsIgnoreCase("Red")) {
			lamChest.setColor(Color.RED);
		}
		chestplate.setItemMeta(lamChest);
		p.getEquipment().setChestplate(chestplate);
		
		
		ItemStack legging = NbtFactory.getCraftItemStack(new ItemStack(Material.LEATHER_LEGGINGS));
		LeatherArmorMeta lamLegging = (LeatherArmorMeta) legging.getItemMeta();
		NbtCompound Ltags = NbtFactory.fromItemTag(legging);
		Ltags.putPath("Unbreakable", (byte) 1);
		if (team.equalsIgnoreCase("Blue")) {
			lamLegging.setColor(Color.BLUE);
		} else if (team.equalsIgnoreCase("Red")) {
			lamLegging.setColor(Color.RED);
		}
		legging.setItemMeta(lamLegging);
		p.getEquipment().setLeggings(legging);
		
		
		ItemStack boots = NbtFactory.getCraftItemStack(new ItemStack(Material.LEATHER_BOOTS));
		LeatherArmorMeta lamBoots = (LeatherArmorMeta) boots.getItemMeta();
		NbtCompound Btags = NbtFactory.fromItemTag(boots);
		Btags.putPath("Unbreakable", (byte) 1);
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
	
	private void giveSpyInventory(Player p) {
		String team = null;
		
		for (MetadataValue val : p.getMetadata("WorldWar.Team")) {
			if (val.getOwningPlugin().getName().equals("WorldWar")) {
				team = val.asString();
			}
		}
		p.sendMessage("Team is: " + team);
		
		this.cleanInventory(p);
		Inventory inv = p.getInventory();
		inv.addItem(ItemStackHandler.getPistolItemStack());
		
		ItemStack helmet = NbtFactory.getCraftItemStack(new ItemStack(Material.LEATHER_HELMET));
		NbtCompound Htags = NbtFactory.fromItemTag(helmet);
		Htags.putPath("Unbreakable", (byte) 1);
		LeatherArmorMeta lamHelm = (LeatherArmorMeta) helmet.getItemMeta();
		if (team.equalsIgnoreCase("Blue")) {
			lamHelm.setColor(Color.RED);
		} else if (team.equalsIgnoreCase("Red")) {
			lamHelm.setColor(Color.BLUE);
		}
		helmet.setItemMeta(lamHelm);
		p.getEquipment().setHelmet(helmet);
		
		ItemStack chestplate = NbtFactory.getCraftItemStack(new ItemStack(Material.LEATHER_CHESTPLATE));
		NbtCompound Ctags = NbtFactory.fromItemTag(chestplate);
		Ctags.putPath("Unbreakab;e", (byte) 1);
		LeatherArmorMeta lamChest = (LeatherArmorMeta) chestplate.getItemMeta();
		if (team.equalsIgnoreCase("Blue")) {
			lamChest.setColor(Color.BLUE);
		} else if (team.equalsIgnoreCase("Red")) {
			lamChest.setColor(Color.RED);
		}
		chestplate.setItemMeta(lamChest);
		p.getEquipment().setChestplate(chestplate);
		
		ItemStack legging = NbtFactory.getCraftItemStack(new ItemStack(Material.LEATHER_LEGGINGS));
		NbtCompound Ltags = NbtFactory.fromItemTag(legging);
		Ltags.putPath("Unbreakable", (byte) 1);
		LeatherArmorMeta lamLeg = (LeatherArmorMeta) legging.getItemMeta();
		if (team.equalsIgnoreCase("Blue")) {
			lamLeg.setColor(Color.BLUE);
		} else if (team.equalsIgnoreCase("Red")) {
			lamLeg.setColor(Color.RED);
		}
		legging.setItemMeta(lamLeg);
		p.getEquipment().setLeggings(legging);
		
		ItemStack boots = NbtFactory.getCraftItemStack(new ItemStack(Material.LEATHER_BOOTS));
		NbtCompound Btags = NbtFactory.fromItemTag(boots);
		Btags.putPath("Unbreakable", (byte) 1);
		LeatherArmorMeta lamBoots = (LeatherArmorMeta) boots.getItemMeta();
		if (team.equalsIgnoreCase("Blue")) {
			lamBoots.setColor(Color.BLUE);
		} else if (team.equalsIgnoreCase("Red")) {
			lamBoots.setColor(Color.RED);
		}
		boots.setItemMeta(lamBoots);
		p.getEquipment().setBoots(boots);
		
		updateInv(p);
		
	}
}
