package net.midget807.trapsntrickery.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntBlock.class)
public abstract class TntBlockMixin extends Block {
    private static BlockPos sourcePos;
    public TntBlockMixin(Settings settings) {
        super(settings);
    }
    @Inject(method = "primeTnt(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/LivingEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z", shift = At.Shift.BEFORE))
    private static void trapsntrickery$instaPrime(World world, BlockPos pos, LivingEntity igniter, CallbackInfo ci, @Local TntEntity tnt) {
        if (world.getBlockState(sourcePos).isOf(Blocks.TRAPPED_CHEST)) {
            tnt.setFuse(-1);
        }
    }
    @Inject(method = "neighborUpdate", at = @At("HEAD"))
    private void trapsntrickery$getSourcePos(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify, CallbackInfo ci) {
        this.sourcePos = sourcePos;
    }

}
