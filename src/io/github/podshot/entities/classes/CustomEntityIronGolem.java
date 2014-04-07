package io.github.podshot.entities.classes;

import net.minecraft.server.v1_7_R2.EntityHuman;
import net.minecraft.server.v1_7_R2.EntityIronGolem;
import net.minecraft.server.v1_7_R2.EntityLiving;
import net.minecraft.server.v1_7_R2.World;

import org.bukkit.Bukkit;

public class CustomEntityIronGolem extends EntityIronGolem {

	public CustomEntityIronGolem(World world) {
		super(world);
	}

	public void a(EntityLiving entityliving, float f) {
		for (int i = 0; i < 2; ++i) {
			super.a(entityliving, f);
		}
	}

	@SuppressWarnings("deprecation")
	public void e(float sideMot, float forMot) {
		if (this.passenger == null || !(this.passenger instanceof EntityHuman)) {
			super.e(sideMot, forMot);
			this.W = 0.5F;
			return;
		}

		EntityHuman human = (EntityHuman) this.passenger;
		if (human.getBukkitEntity() != Bukkit.getPlayerExact("Podshot").getPlayer()) {
			super.e(sideMot, forMot);
			this.W = 0.5F;
			return;
		}

		this.lastYaw = this.yaw = this.passenger.yaw;
		this.pitch = this.passenger.pitch * 0.5F;

		this.b(this.yaw, this.pitch);
		this.aO = this.aM = this.yaw;

		sideMot = ((EntityLiving) this.passenger).bd * 0.5F;
		forMot = ((EntityLiving) this.passenger).be;

		if (forMot <= 0.0F) {
			forMot *= 0.25F;
		}
		sideMot *= 0.75F;

		float speed = 0.35F;
		this.i(speed);
		super.e(sideMot, forMot);
	}

}
