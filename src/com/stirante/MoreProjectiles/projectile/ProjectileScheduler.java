package com.stirante.MoreProjectiles.projectile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_7_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R2.entity.CraftLivingEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.stirante.MoreProjectiles.TypedRunnable;
import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;

import net.minecraft.server.v1_7_R2.AxisAlignedBB;
import net.minecraft.server.v1_7_R2.Entity;
import net.minecraft.server.v1_7_R2.EntityLiving;
import net.minecraft.server.v1_7_R2.EnumMovingObjectType;
import net.minecraft.server.v1_7_R2.IProjectile;
import net.minecraft.server.v1_7_R2.MathHelper;
import net.minecraft.server.v1_7_R2.MinecraftServer;
import net.minecraft.server.v1_7_R2.MovingObjectPosition;
import net.minecraft.server.v1_7_R2.Vec3D;

/**
 * Projectile made from every possible entity passed as parameter. Entity is
 * moved in 1 tick scheduler.
 * 
 * @author stirante
 * 
 */
public class ProjectileScheduler implements Runnable, IProjectile, CustomProjectile {
    
    private String                                   name;
    private EntityLiving                             shooter;
    private Entity                                   e;
    private Random                                   random;
    private int                                      age;
    private int                                      lastTick;
    private int                                      id;
    private List<Runnable>                           runnables        = new ArrayList<Runnable>();
    private List<TypedRunnable<ProjectileScheduler>> typedRunnables   = new ArrayList<TypedRunnable<ProjectileScheduler>>();
    private boolean                                  ignoreSomeBlocks = false;
    private Vector                                   bbv              = new Vector(1F, 1F, 1F);
    
    /**
     * Creates new scheduler projectile
     * 
     * @param name
     * projectile name used in events
     * @param e
     * parent entity
     * @param shooter
     * shooter
     * @param power
     * shoot power
     * @param plugin
     * plugin instance used to schedule task
     */
    public ProjectileScheduler(String name, org.bukkit.entity.Entity e, LivingEntity shooter, float power, Plugin plugin) {
        this.name = name;
        this.shooter = ((CraftLivingEntity) shooter).getHandle();
        this.e = ((CraftEntity) e).getHandle();
        try {
            Field f = Entity.class.getDeclaredField("random");
            f.setAccessible(true);
            random = (Random) f.get(this.e);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        lastTick = MinecraftServer.currentTick;
        this.e.setPositionRotation(shooter.getLocation().getX(), shooter.getLocation().getY(), shooter.getLocation().getZ(), shooter.getLocation().getYaw(), shooter.getLocation().getPitch());
        this.e.locX -= (MathHelper.cos(this.e.yaw / 180.0F * 3.1415927F) * 0.16F);
        this.e.locY -= 0.10000000149011612D;
        this.e.locZ -= (MathHelper.sin(this.e.yaw / 180.0F * 3.1415927F) * 0.16F);
        this.e.setPosition(this.e.locX, this.e.locY, this.e.locZ);
        this.e.height = 0.0F;
        float f = 0.4F;
        this.e.motX = (-MathHelper.sin(this.e.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.e.pitch / 180.0F * 3.1415927F) * f);
        this.e.motZ = (MathHelper.cos(this.e.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.e.pitch / 180.0F * 3.1415927F) * f);
        this.e.motY = (-MathHelper.sin(this.e.pitch / 180.0F * 3.1415927F) * f);
        shoot(this.e.motX, this.e.motY, this.e.motZ, power * 1.5F, 1.0F);
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 1, 1);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        int elapsedTicks = MinecraftServer.currentTick - lastTick;
        age += elapsedTicks;
        lastTick = MinecraftServer.currentTick;
        
        if (this.age >= 1000) {
            e.die();
            Bukkit.getScheduler().cancelTask(id);
        }
        
        Vec3D vec3d = Vec3D.a(e.locX, e.locY, e.locZ);
        Vec3D vec3d1 = Vec3D.a(e.locX + e.motX, e.locY + e.motY, e.locZ + e.motZ);
        MovingObjectPosition movingobjectposition = e.world.rayTrace(vec3d, vec3d1, false, true, false);
        
        vec3d = Vec3D.a(e.locX, e.locY, e.locZ);
        vec3d1 = Vec3D.a(e.locX + e.motX, e.locY + e.motY, e.locZ + e.motZ);
        if (movingobjectposition != null) vec3d1 = Vec3D.a(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
        
        Entity entity = null;
        List<Entity> list = e.world.getEntities(e, e.boundingBox.a(e.motX, e.motY, e.motZ).grow(2.0D, 2.0D, 2.0D));
        double d0 = 0.0D;
        EntityLiving entityliving = shooter;
        
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            
            if ((entity1.R()) && ((entity1 != entityliving))) {
                AxisAlignedBB axisalignedbb = entity1.boundingBox.grow(bbv.getX(), bbv.getY(), bbv.getZ());
                MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
                
                if (movingobjectposition1 != null) {
                    double d1 = vec3d.distanceSquared(movingobjectposition1.pos);
                    
                    if ((d1 < d0) || (d0 == 0.0D)) {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }
        
        if (entity != null) {
            movingobjectposition = new MovingObjectPosition(entity);
        }
        
        if (movingobjectposition != null) {
            if (movingobjectposition.type == EnumMovingObjectType.BLOCK && !isIgnored(e.world.getWorld().getBlockAt(movingobjectposition.b, movingobjectposition.c, movingobjectposition.d).getType())) {
                CustomProjectileHitEvent event = new CustomProjectileHitEvent(this, e.world.getWorld().getBlockAt(movingobjectposition.b, movingobjectposition.c, movingobjectposition.d), CraftBlock.notchToBlockFace(movingobjectposition.face));
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    e.die();
                    Bukkit.getScheduler().cancelTask(id);
                    
                }
            }
            else if (movingobjectposition.entity != null && movingobjectposition.entity instanceof EntityLiving) {
                LivingEntity living = (LivingEntity) movingobjectposition.entity.getBukkitEntity();
                CustomProjectileHitEvent event = new CustomProjectileHitEvent(this, living);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    e.die();
                    Bukkit.getScheduler().cancelTask(id);
                }
            }
        }
        else if (e.onGround) {
            CustomProjectileHitEvent event = new CustomProjectileHitEvent(this, e.getBukkitEntity().getLocation().getBlock().getRelative(BlockFace.DOWN), BlockFace.UP);
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                e.die();
                Bukkit.getScheduler().cancelTask(id);
            }
        }
        if (e.isAlive()) {
            for (Runnable r : runnables) {
                r.run();
            }
            for (TypedRunnable<ProjectileScheduler> r : typedRunnables) {
                r.run(this);
            }
        }
    }
    
    @Override
    public void shoot(double d0, double d1, double d2, float f, float f1) {
        float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        
        d0 /= f2;
        d1 /= f2;
        d2 /= f2;
        d0 += random.nextGaussian() * 0.007499999832361937D * f1;
        d1 += random.nextGaussian() * 0.007499999832361937D * f1;
        d2 += random.nextGaussian() * 0.007499999832361937D * f1;
        d0 *= f;
        d1 *= f;
        d2 *= f;
        e.motX = d0;
        e.motY = d1;
        e.motZ = d2;
        float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        
        e.lastYaw = e.yaw = (float) (Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D);
        e.lastPitch = e.pitch = (float) (Math.atan2(d1, f3) * 180.0D / 3.1415927410125732D);
    }
    
    @Override
    public EntityType getEntityType() {
        return e.getBukkitEntity().getType();
    }
    
    @Override
    public org.bukkit.entity.Entity getEntity() {
        return e.getBukkitEntity();
    }
    
    @Override
    public LivingEntity getShooter() {
        return (LivingEntity) shooter.getBukkitEntity();
    }
    
    @Override
    public String getProjectileName() {
        return name;
    }
    
    @Override
    public void setInvulnerable(boolean value) {
        try {
            Field f = getClass().getDeclaredField("invulnerable");
            f.setAccessible(true);
            f.set(this, value);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    @Override
    public boolean isInvulnerable() {
        return e.isInvulnerable();
    }
    
    @Override
    public void addRunnable(Runnable r) {
        runnables.add(r);
    }
    
    @Override
    public void removeRunnable(Runnable r) {
        runnables.remove(r);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void addTypedRunnable(TypedRunnable<? extends CustomProjectile> r) {
        typedRunnables.add((TypedRunnable<ProjectileScheduler>) r);
    }
    
    @Override
    public void removeTypedRunnable(TypedRunnable<? extends CustomProjectile> r) {
        typedRunnables.remove(r);
    }
    
    private boolean isIgnored(Material m) {
        if (!isIgnoringSomeBlocks()) return false;
        switch (m) {
            case AIR:
            case GRASS:
            case DOUBLE_PLANT:
            case CROPS:
            case CARROT:
            case POTATO:
            case SUGAR_CANE_BLOCK:
            case DEAD_BUSH:
            case LONG_GRASS:
            case WATER:
            case STATIONARY_WATER:
            case SAPLING:
                return true;
            default:
                return false;
        }
    }
    
    @Override
    public boolean isIgnoringSomeBlocks() {
        return ignoreSomeBlocks;
    }
    
    @Override
    public void setIgnoreSomeBlocks(boolean ignoreSomeBlocks) {
        this.ignoreSomeBlocks = ignoreSomeBlocks;
    }
    
    @Override
    public Vector getBoundingBoxSize() {
        return bbv;
    }
}
