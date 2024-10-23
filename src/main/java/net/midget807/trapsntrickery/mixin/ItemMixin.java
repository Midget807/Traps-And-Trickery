package net.midget807.trapsntrickery.mixin;

import net.midget807.trapsntrickery.block.trapsntrickery.LayingBrickBlock;
import net.midget807.trapsntrickery.entity.trapsntrickery.BrickEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow
    protected static BlockHitResult raycast(World world, PlayerEntity player, RaycastContext.FluidHandling fluidHandling) {
        float f = player.getPitch();
        float g = player.getYaw();
        Vec3d vec3d = player.getEyePos();
        float h = MathHelper.cos(-g * (float) (Math.PI / 180.0) - (float) Math.PI);
        float i = MathHelper.sin(-g * (float) (Math.PI / 180.0) - (float) Math.PI);
        float j = -MathHelper.cos(-f * (float) (Math.PI / 180.0));
        float k = MathHelper.sin(-f * (float) (Math.PI / 180.0));
        float l = i * j;
        float n = h * j;
        double d = 5.0;
        Vec3d vec3d2 = vec3d.add((double)l * d, (double)k * d, (double)n * d);
        return world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.OUTLINE, fluidHandling, player));
    }

    @Inject(method = "useOnBlock", at = @At("HEAD"))
    private void trapsntrickery$placeLayingBricks(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        Hand hand = context.getHand();
        BlockHitResult blockHitResult = raycast(world, player, RaycastContext.FluidHandling.NONE);
        if (!player.isSneaking()) {
            LayingBrickBlock.placeBrickStack(player, world, hand, blockHitResult);
        }
        player.swingHand(hand);
    }
    @Inject(method = "use", at = @At("HEAD"))
    private void trapsntrickery$throwBrick(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.isOf(Items.BRICK) && user.isSneaking()) {
            BrickEntity brickEntity = new BrickEntity(world, user);
            brickEntity.setItem(itemStack); // TODO: 23/10/2024 mixin itemRenderer to show 3D brick projectile
            brickEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 0.6f, 0.5f);
            itemStack.decrement(1);
            world.spawnEntity(brickEntity);
        }
    }
}
