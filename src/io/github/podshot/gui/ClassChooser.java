package io.github.podshot.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
		
		imSpy.setLore(spyList);
		imSpy.setDisplayName(ChatColor.GRAY + "Spy");	
		spy.setItemMeta(imSpy);
		classChooser.setItem(1, spy);
		
		ItemStack engineer = new ItemStack(Material.IRON_SPADE);
		ItemMeta imEngineer = engineer.getItemMeta();
		List<String> engineerList = new ArrayList<String>();
		engineerList.add(ChatColor.YELLOW + "Equipped with a Wrench, Portable Landmines,");
		engineerList.add(ChatColor.YELLOW + "and a Shotgun loaded with Slug Shell");
		engineerList.add("");
		engineerList.add(ChatColor.YELLOW + "Essential Class for any Squadron of Vehicles");
		
		imEngineer.setLore(engineerList);
		imEngineer.setDisplayName(ChatColor.GRAY + "Engineer");
		engineer.setItemMeta(imEngineer);
		classChooser.setItem(2, engineer);
		
		return classChooser;
	}

}
