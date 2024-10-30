package net.midget807.trapsntrickery.mixin;

import net.midget807.trapsntrickery.item.trapsntrickery.SlimeCubeItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @Shadow public abstract ItemStack getStack();

    @Shadow public abstract @Nullable Entity getOwner();

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void trapsntrickery$changeEntity(CallbackInfo ci) {
        ItemStack stack = this.getStack();
        if (stack.getItem() instanceof SlimeCubeItem slimeCubeItem && !this.getWorld().isClient) {
            SlimeEntity slimeEntity = slimeCubeItem.getEntityToSpawn(stack, this.getWorld());
            slimeEntity.updatePosition(this.getX(), this.getY(), this.getZ());
            slimeEntity.setPos(this.getX(), this.getY(), this.getZ());
            if (this.getOwner() instanceof PlayerEntity player && this.getOwner() != null) {
                slimeEntity.setPitch(player.getPitch());
                slimeEntity.setYaw(player.getYaw());
                slimeEntity.setVelocity(this.getVelocity().multiply(1.2));
            }
            this.getWorld().spawnEntity(slimeEntity);
            stack.decrement(1);
            ci.cancel();
        }
    }
    @Inject(method = "cannotPickup", at = @At("RETURN"), cancellable = true)
    public void trapsntrickery$pickupSlimeCubeItem(CallbackInfoReturnable<Boolean> cir) {
        if (this.getStack().getItem() instanceof SlimeCubeItem) {
            cir.setReturnValue(true);
        }
    }
}
