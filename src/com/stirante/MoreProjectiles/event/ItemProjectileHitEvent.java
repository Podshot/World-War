package com.stirante.MoreProjectiles.event;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import com.stirante.MoreProjectiles.projectile.CustomProjectile;

/**
 * ItemProjectileHitEvent is fired when item projectile hits entity or block.
 */
public class ItemProjectileHitEvent extends CustomProjectileHitEvent {
    
    private ItemStack item;
    
    /**
     * Instantiates a new item projectile hit event.
     * 
     * @param pro
     * projectile
     * @param b
     * hit block
     * @param f
     * block face
     * @param item
     * item
     */
    public ItemProjectileHitEvent(CustomProjectile pro, Block b, BlockFace f, ItemStack item) {
        super(pro, b, f);
        this.item = item;
    }
    
    /**
     * Instantiates a new item projectile hit event.
     * 
     * @param pro
     * projectile
     * @param ent
     * hit entity
     * @param item
     * item
     */
    public ItemProjectileHitEvent(CustomProjectile pro, LivingEntity ent, ItemStack item) {
        super(pro, ent);
        this.item = item;
    }
    
    /**
     * Gets the item stack.
     * 
     * @return the item stack
     */
    public ItemStack getItemStack() {
        return item;
    }
    
}
