package com.stirante.MoreProjectiles.event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;

import com.stirante.MoreProjectiles.projectile.CustomProjectile;

/**
 * BlockProjectileHitEvent is fired when falling block projectile hits entity or
 * block.
 */
public class BlockProjectileHitEvent extends CustomProjectileHitEvent {
    
    private Material mat;
    private int      data;
    
    /**
     * Instantiates a new block projectile hit event.
     * 
     * @param pro
     * projectile
     * @param b
     * hit block
     * @param f
     * block face
     * @param mat
     * block id
     * @param data
     * damage value of block
     */
    public BlockProjectileHitEvent(CustomProjectile pro, Block b, BlockFace f, Material mat, int data) {
        super(pro, b, f);
        this.mat = mat;
        this.data = data;
    }
    
    /**
     * Instantiates a new block projectile hit event.
     * 
     * @param pro
     * projectile
     * @param ent
     * hit entity
     * @param mat
     * block id
     * @param data
     * damage value of block
     */
    public BlockProjectileHitEvent(CustomProjectile pro, LivingEntity ent, Material mat, int data) {
        super(pro, ent);
        this.mat = mat;
        this.data = data;
    }
    
    /**
     * Gets the block id.
     * 
     * @return the block id
     */
    public Material getMaterial() {
        return mat;
    }
    
    /**
     * Gets the data.
     * 
     * @return damage value of block
     */
    public int getData() {
        return data;
    }
    
}
