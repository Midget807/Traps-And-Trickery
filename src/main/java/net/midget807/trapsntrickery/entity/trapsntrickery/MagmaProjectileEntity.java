package net.midget807.trapsntrickery.entity.trapsntrickery;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.world.World;

public class MagmaProjectileEntity extends SlimeProjectileEntity{
    public MagmaProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public MagmaProjectileEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public MagmaProjectileEntity(World world, LivingEntity owner) {
        super(world, owner);
    }
}
