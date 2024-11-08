package net.midget807.trapsntrickery.render;

import net.midget807.trapsntrickery.item.ModItems;
import net.midget807.trapsntrickery.item.trapsntrickery.SlingshotItem;
import net.midget807.trapsntrickery.mixin.SpyglassItemMixin;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpyglassItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

        ModelPredicateProviderRegistry.register(Items.SPYGLASS, new Identifier("lens_type"), (stack, world, entity, seed) -> {
            Optional<ItemStack> lensStack;
            NbtCompound spyglassNbt = stack.getNbt();
            if (spyglassNbt == null) {
                return 0.0f;
            }
            NbtList nbtList = spyglassNbt.getList("Lens", NbtElement.COMPOUND_TYPE);
            if (nbtList.isEmpty()) {
                return 0.0f;
            } else {
                Stream<NbtElement> stream = nbtList.stream();
                List<ItemStack> list = stream.map(NbtCompound.class::cast).map(ItemStack::fromNbt).toList();
                lensStack = Optional.of(list.get(0));
                if (lensStack.get().isOf(ModItems.REVEALING_LENS)) {
                    return 1.0f;
                } else {
                    return 0.0f;
                }
            }
        });
    }
}
