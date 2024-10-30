package net.midget807.trapsntrickery.render;

import net.midget807.trapsntrickery.item.ModItems;
import net.midget807.trapsntrickery.item.trapsntrickery.SlingshotItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModModelPredicateProvider {
    public static void registerModelPredicateProvider() {
        ModelPredicateProviderRegistry.register(ModItems.SLINGSHOT, new Identifier("slingshot_pull"), (stack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getActiveItem() != stack ? 0.0f : (stack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0f;
            }
        });
        ModelPredicateProviderRegistry.register(ModItems.SLINGSHOT, new Identifier("slingshot_pulling"), (stack, world, entity, seed) -> {
            return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f;
        });
        ModelPredicateProviderRegistry.register(ModItems.SLINGSHOT, new Identifier("has_magma_cube"), (stack, world, entity, seed) -> {
            if (stack.getItem() instanceof SlingshotItem slingshotItem) {
                boolean isMagma = slingshotItem.getProjectiles().test(new ItemStack(ModItems.MAGMA_CUBE_ITEM));
                return isMagma ? 1.0f : 0.0f;
            } else {
                return 0.0f;
            }
        });
    }
}
