package net.midget807.trapsntrickery;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.midget807.trapsntrickery.block.ModBlocks;
import net.midget807.trapsntrickery.entity.ModEntities;
import net.midget807.trapsntrickery.gui.SpyglassTooltipComponent;
import net.midget807.trapsntrickery.network.ModClientMessages;
import net.midget807.trapsntrickery.render.ModModelPredicateProvider;
import net.midget807.trapsntrickery.render.SlimeProjectileEntityRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class TrapsAndTrickeryClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRendererRegistry.register(ModEntities.BRICK_ENTITY_TYPE, FlyingItemEntityRenderer::new);
		TooltipComponentCallback.EVENT.register(SpyglassTooltipComponent::register);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.LAYING_BRICK);
		ModClientMessages.registerS2CPackets();
		ModModelPredicateProvider.registerModelPredicateProvider();
		EntityRendererRegistry.register(ModEntities.SLIME_PROJECTILE_ENTITY_TYPE, SlimeProjectileEntityRenderer::new);
	}
}