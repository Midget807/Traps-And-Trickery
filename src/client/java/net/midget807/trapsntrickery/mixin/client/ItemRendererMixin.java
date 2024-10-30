package net.midget807.trapsntrickery.mixin.client;

import net.midget807.trapsntrickery.TrapsAndTrickeryMain;
import net.midget807.trapsntrickery.item.ModItems;
import net.midget807.trapsntrickery.item.trapsntrickery.SlingshotItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Unique
    private static PlayerEntity entity;
    @Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)Lnet/minecraft/client/render/model/BakedModel;"))
    private void trapsntrickery$storeEntity(LivingEntity entity, ItemStack item, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int light, int overlay, int seed, CallbackInfo ci) {
        if (item.isOf(ModItems.SLINGSHOT) && entity instanceof PlayerEntity player) {
            ItemRendererMixin.entity = player;
        }
    }
    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), argsOnly = true)
    public BakedModel trapsntrickery$customModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumer, int light, int overlay, BakedModel model) {
        if (stack.isOf(ModItems.SLINGSHOT) && (renderMode != ModelTransformationMode.GUI) && (renderMode != ModelTransformationMode.GROUND)) {
            boolean pulling = entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? true : false;
            float pull = entity.getActiveItem() != stack ? 0.0f : (stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0f;
            if (pulling) {
                return ((ItemRendererAccessor) this).trapsntrickery$getModels().getModelManager().getModel(new ModelIdentifier(TrapsAndTrickeryMain.MOD_ID, "slingshot_handheld", "inventory"));
            } else {
                return ((ItemRendererAccessor) this).trapsntrickery$getModels().getModelManager().getModel(new ModelIdentifier(TrapsAndTrickeryMain.MOD_ID, "slingshot_handheld", "inventory"));
            }
        }
        return value;
    }
}
