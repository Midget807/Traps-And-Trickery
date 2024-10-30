package net.midget807.trapsntrickery.entity.trapsntrickery;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.trapsntrickery.effect.ModEffects;
import net.midget807.trapsntrickery.entity.ModEntities;
import net.midget807.trapsntrickery.item.ModItems;
import net.midget807.trapsntrickery.network.ModMessages;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class SlimeProjectileEntity extends PersistentProjectileEntity {
    public static boolean isMagmaCube;
    public SlimeProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public SlimeProjectileEntity(World world, double x, double y, double z) {
        super(ModEntities.SLIME_PROJECTILE_ENTITY_TYPE, x, y, z, world);
    }

    public SlimeProjectileEntity(World world, LivingEntity owner) {
        super(ModEntities.SLIME_PROJECTILE_ENTITY_TYPE, owner, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.SLIME_CUBE_ITEM);
    }

    public void initFromStack(ItemStack stack) {
        isMagmaCube = stack.isOf(ModItems.MAGMA_CUBE_ITEM);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        BlockPos blockPos = blockHitResult.getBlockPos();
        Direction direction = blockHitResult.getSide();
        BlockPos spawnPos = blockPos.offset(direction);
        World world = this.getWorld();
        SlimeEntity slimeEntity = new SlimeEntity(isMagmaCube ? EntityType.MAGMA_CUBE : EntityType.SLIME, world);
        slimeEntity.setSize(1, true);
        slimeEntity.updatePosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
        slimeEntity.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
        world.spawnEntity(slimeEntity);
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        World world = this.getWorld();
        boolean fireImmune = entityHitResult.getEntity().isFireImmune();
        if (!world.isClient) {
            if (isMagmaCube) {
                if (fireImmune || !(entityHitResult.getEntity() instanceof LivingEntity)) {
                    BlockPos blockPos = entityHitResult.getEntity().getBlockPos();
                    SlimeEntity slimeEntity = new SlimeEntity(EntityType.MAGMA_CUBE, world);
                    slimeEntity.setSize(1, true);
                    slimeEntity.updatePosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    slimeEntity.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    world.spawnEntity(slimeEntity);
                } else {
                    if (entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
                        if (!livingEntity.hasStatusEffect(ModEffects.MAGMA_SLIMED)) {
                            livingEntity.addStatusEffect(new StatusEffectInstance(ModEffects.MAGMA_SLIMED, 5 * 20));
                            PacketByteBuf buf = PacketByteBufs.create();
                            buf.writeInt(5 * 20);
                            buf.writeBoolean(true);
                            ServerPlayNetworking.createS2CPacket(ModMessages.MAGMA_SLIMED_EFFECT_OVERLAY, buf);
                            livingEntity.setFireTicks(10 * 20);
                            livingEntity.setOnFire(true);
                            livingEntity.playSound(SoundEvents.ENTITY_MAGMA_CUBE_JUMP, 1.0f, 1.0f);
                        }
                    }
                }
            } else {
                if (entityHitResult.getEntity().getType() == EntityType.SLIME || !(entityHitResult.getEntity() instanceof LivingEntity)) {
                    BlockPos blockPos = entityHitResult.getEntity().getBlockPos();
                    SlimeEntity slimeEntity = new SlimeEntity(EntityType.SLIME, world);
                    slimeEntity.setSize(1, true);
                    slimeEntity.updatePosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    slimeEntity.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    world.spawnEntity(slimeEntity);
                } else {
                    if (entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
                        if (!livingEntity.hasStatusEffect(ModEffects.SLIMED)) {
                            livingEntity.addStatusEffect(new StatusEffectInstance(ModEffects.SLIMED, 5 * 20), this.getEffectCause());
                            PacketByteBuf buf = PacketByteBufs.create();
                            buf.writeInt(5 * 20);
                            buf.writeBoolean(false);
                            ServerPlayNetworking.createS2CPacket(ModMessages.MAGMA_SLIMED_EFFECT_OVERLAY, buf);
                            livingEntity.playSound(SoundEvents.ENTITY_SLIME_JUMP, 1.0f, 1.0f);
                        }
                    }
                }
            }
        }
    }
}
