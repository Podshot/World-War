package io.github.podshot.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClassChooser {
	
	public static Inventory getClassChooserGui() {
		Inventory classChooser = Bukkit.createInventory(null, 9, ChatColor.DARK_RED + "Choose a Fighting Class");
		
		ItemStack soldier = new ItemStack(Material.LEATHER_HELMET);
		ItemMeta imSoldier = soldier.getItemMeta();
		imSoldier.setDisplayName(ChatColor.GRAY + "Soldier");
		List<String> soldierList = new ArrayList<String>();
		soldierList.add(ChatColor.YELLOW + "Armed with a Standard Issue Rifle,");
		soldierList.add(ChatColor.YELLOW + "partially used Armor, and a hand grenade.");
		soldierList.add("");
		soldierList.add(ChatColor.YELLOW + "This class makes up the backbone of any strong force");
		imSoldier.setLore(soldierList);
		soldier.setItemMeta(imSoldier);
		classChooser.setItem(0, soldier);
		
		ItemStack spy = new ItemStack(Material.POTION);
		ItemMeta imSpy = spy.getItemMeta();
		List<String> spyList = new ArrayList<String>();
		spyList.add(ChatColor.YELLOW + "Equipped with a Silenced Pistol, Cloaking device,");
		spyList.add(ChatColor.YELLOW + "Plantable Explosives, and Squad Objectives");
		spyList.add("");
		spyList.add(ChatColor.YELLOW + "Vital Class for Scouting and Objective targeting");
		return classChooser;
	}

}
