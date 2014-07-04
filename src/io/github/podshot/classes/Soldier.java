package io.github.podshot.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.comphenix.attribute.NbtFactory;
import com.comphenix.attribute.NbtFactory.NbtCompound;

import io.github.podshot.api.PlayerAPI;
import io.github.podshot.events.guns.Rifle;
import io.github.podshot.files.PlayerDataYAML;
import io.github.podshot.handlers.PlayerClasses;
import io.github.podshot.inventories.InventoryManager;

public class Soldier {
	
	private static PlayerClasses type = PlayerClasses.Soldier;
	private static List<UUID> soldiers = new ArrayList<UUID>();
	
	public Soldier(Player player, boolean remove) {
		if (remove) {
			if (soldiers.contains(player.getUniqueId())) {
				soldiers.remove(player.getUniqueId());
			}
		} else {
			soldiers.add(player.getUniqueId());
			String savedInventory = InventoryManager.InventoryToString(player.getInventory());
			PlayerDataYAML.saveInventoryToFile(player, savedInventory);
			player.getInventory().clear();
			player.getInventory().setContents((ItemStack[]) this.getClassInventory().toArray());
			String team = PlayerAPI.getTeam(player);
			player.getInventory().setHelmet(this.getClassArmor(ArmorPiece.Helmet, team));
			player.getInventory().setChestplate(this.getClassArmor(ArmorPiece.Chestplate, team));
			player.getInventory().setLeggings(this.getClassArmor(ArmorPiece.Leggings, team));
			player.getInventory().setBoots(this.getClassArmor(ArmorPiece.Boots, team));
		}
	}
	
	private ItemStack getClassArmor(ArmorPiece piece, String team) {
		ItemStack armor = null;
		switch (piece) {
		default:
			break;
		case Helmet:
			ItemStack helmet = NbtFactory.getCraftItemStack(new ItemStack(Material.IRON_HELMET));
			NbtCompound Htags = NbtFactory.fromItemTag(helmet);
			Htags.putPath("Unbreakable", (byte) 1);
			armor = helmet;
			break;
		case Chestplate:
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
			armor = chestplate;
			break;
		case Leggings:
			ItemStack leggings = NbtFactory.getCraftItemStack(new ItemStack(Material.LEATHER_LEGGINGS));
			LeatherArmorMeta lamLeggings = (LeatherArmorMeta) leggings.getItemMeta();
			NbtCompound Ltags = NbtFactory.fromItemTag(leggings);
			Ltags.putPath("Unbreakable", (byte) 1);
			if (team.equalsIgnoreCase("Blue")) {
				lamLeggings.setColor(Color.BLUE);
			} else if (team.equalsIgnoreCase("Red")) {
				lamLeggings.setColor(Color.RED);
			}
			leggings.setItemMeta(lamLeggings);
			armor = leggings;
			break;
		case Boots:
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
			armor = boots;
			break;
		}
		return armor;
	}

	private List<ItemStack> getClassInventory() {
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		ItemStack rifle = Rifle.getGun();
		items.add(rifle);
		
		return items;
	}
	
	public static PlayerClasses getPlayerClassesType() {
		return type;
	}
	
	public static List<UUID> getSoldiers() {
		return soldiers;
	}

}
