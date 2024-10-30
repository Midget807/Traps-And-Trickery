package net.midget807.trapsntrickery.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.midget807.trapsntrickery.TrapsAndTrickeryMain;
import net.midget807.trapsntrickery.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> LENSES = registerItemTag("lenses");
    public static final TagKey<Item> SLINGSHOT_PROJECTILES = registerItemTag("slingshot_projectiles");
    private static TagKey<Item> registerItemTag(String name) {
        return TagKey.of(RegistryKeys.ITEM, TrapsAndTrickeryMain.id(name));
    }
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }
    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(LENSES)
                .add(ModItems.REVEALING_LENS);

        this.getOrCreateTagBuilder(SLINGSHOT_PROJECTILES)
                .add(ModItems.SLIME_CUBE_ITEM)
                .add(ModItems.MAGMA_CUBE_ITEM);
    }
}
