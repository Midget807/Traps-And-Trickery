package net.midget807.trapsntrickery.mixin;

import net.midget807.trapsntrickery.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntity implements Monster {

    @Shadow public abstract boolean isSmall();

    @Shadow public abstract EntityType<? extends SlimeEntity> getType();

    protected SlimeEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.isSmall()) {
            hand = Hand.MAIN_HAND;
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.isEmpty() && player.isSneaking()) {
                if (this.getType() == EntityType.MAGMA_CUBE) {
                    itemStack = new ItemStack(ModItems.MAGMA_CUBE_ITEM);
                } else {
                    itemStack = new ItemStack(ModItems.SLIME_CUBE_ITEM);
                }
                player.setStackInHand(hand, itemStack);
                this.discard();
                return this.interactMob(player, hand);
            } else {
                return super.interactMob(player, hand);
            }
        } else {
            return super.interactMob(player, hand);
        }
    }
}
