package net.midget807.trapsntrickery;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.midget807.trapsntrickery.entity.ModEntities;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class TrapsAndTrickeryClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRendererRegistry.register(ModEntities.BRICK_ENTITY_TYPE, FlyingItemEntityRenderer::new);
	}
}