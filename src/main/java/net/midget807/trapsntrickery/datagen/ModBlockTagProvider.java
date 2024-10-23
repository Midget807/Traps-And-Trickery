package net.midget807.trapsntrickery.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.midget807.trapsntrickery.TrapsAndTrickeryMain;
import net.midget807.trapsntrickery.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public static final TagKey<Block> BRICKABLE_BLOCKS = registerItemTag("brickable");
    public static final TagKey<Block> BRICKABLE_1_HIT = registerItemTag("brickable_1_hit");
    public static final TagKey<Block> BRICKABLE_2_HIT = registerItemTag("brickable_2_hit");
    private static TagKey<Block> registerItemTag(String name) {
        return TagKey.of(RegistryKeys.BLOCK, TrapsAndTrickeryMain.id(name));
    }
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(BRICKABLE_BLOCKS)
                .add(Blocks.ICE)
                .add(Blocks.FROSTED_ICE)
                .add(Blocks.PACKED_ICE)
                .add(Blocks.GLASS)
                .add(Blocks.WHITE_STAINED_GLASS)
                .add(Blocks.ORANGE_STAINED_GLASS)
                .add(Blocks.MAGENTA_STAINED_GLASS)
                .add(Blocks.LIGHT_BLUE_STAINED_GLASS)
                .add(Blocks.YELLOW_STAINED_GLASS)
                .add(Blocks.LIME_STAINED_GLASS)
                .add(Blocks.PINK_STAINED_GLASS)
                .add(Blocks.GRAY_STAINED_GLASS)
                .add(Blocks.LIGHT_GRAY_STAINED_GLASS)
                .add(Blocks.CYAN_STAINED_GLASS)
                .add(Blocks.PURPLE_STAINED_GLASS)
                .add(Blocks.BLUE_STAINED_GLASS)
                .add(Blocks.BROWN_STAINED_GLASS)
                .add(Blocks.GREEN_STAINED_GLASS)
                .add(Blocks.RED_STAINED_GLASS)
                .add(Blocks.BLACK_STAINED_GLASS)
                .add(Blocks.GLASS_PANE)
                .add(Blocks.WHITE_STAINED_GLASS_PANE)
                .add(Blocks.ORANGE_STAINED_GLASS_PANE)
                .add(Blocks.MAGENTA_STAINED_GLASS_PANE)
                .add(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE)
                .add(Blocks.YELLOW_STAINED_GLASS_PANE)
                .add(Blocks.LIME_STAINED_GLASS_PANE)
                .add(Blocks.PINK_STAINED_GLASS_PANE)
                .add(Blocks.GRAY_STAINED_GLASS_PANE)
                .add(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE)
                .add(Blocks.CYAN_STAINED_GLASS_PANE)
                .add(Blocks.PURPLE_STAINED_GLASS_PANE)
                .add(Blocks.BLUE_STAINED_GLASS_PANE)
                .add(Blocks.BROWN_STAINED_GLASS_PANE)
                .add(Blocks.GREEN_STAINED_GLASS_PANE)
                .add(Blocks.RED_STAINED_GLASS_PANE)
                .add(Blocks.BLACK_STAINED_GLASS_PANE)
                .add(ModBlocks.CRACKED_GLASS)
                .add(ModBlocks.CRACKED_PACKED_ICE)
                .add(ModBlocks.WHITE_STAINED_CRACKED_GLASS)
                .add(ModBlocks.ORANGE_STAINED_CRACKED_GLASS)
                .add(ModBlocks.MAGENTA_STAINED_CRACKED_GLASS)
                .add(ModBlocks.LIGHT_BLUE_STAINED_CRACKED_GLASS)
                .add(ModBlocks.YELLOW_STAINED_CRACKED_GLASS)
                .add(ModBlocks.LIME_STAINED_CRACKED_GLASS)
                .add(ModBlocks.PINK_STAINED_CRACKED_GLASS)
                .add(ModBlocks.GRAY_STAINED_CRACKED_GLASS)
                .add(ModBlocks.LIGHT_GRAY_STAINED_CRACKED_GLASS)
                .add(ModBlocks.CYAN_STAINED_CRACKED_GLASS)
                .add(ModBlocks.PURPLE_STAINED_CRACKED_GLASS)
                .add(ModBlocks.BLUE_STAINED_CRACKED_GLASS)
                .add(ModBlocks.BROWN_STAINED_CRACKED_GLASS)
                .add(ModBlocks.GREEN_STAINED_CRACKED_GLASS)
                .add(ModBlocks.RED_STAINED_CRACKED_GLASS)
                .add(ModBlocks.BLACK_STAINED_CRACKED_GLASS);

        this.getOrCreateTagBuilder(BRICKABLE_1_HIT)
                .add(Blocks.ICE)
                .add(Blocks.FROSTED_ICE)
                .add(Blocks.GLASS_PANE)
                .add(Blocks.WHITE_STAINED_GLASS_PANE)
                .add(Blocks.ORANGE_STAINED_GLASS_PANE)
                .add(Blocks.MAGENTA_STAINED_GLASS_PANE)
                .add(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE)
                .add(Blocks.YELLOW_STAINED_GLASS_PANE)
                .add(Blocks.LIME_STAINED_GLASS_PANE)
                .add(Blocks.PINK_STAINED_GLASS_PANE)
                .add(Blocks.GRAY_STAINED_GLASS_PANE)
                .add(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE)
                .add(Blocks.CYAN_STAINED_GLASS_PANE)
                .add(Blocks.PURPLE_STAINED_GLASS_PANE)
                .add(Blocks.BLUE_STAINED_GLASS_PANE)
                .add(Blocks.BROWN_STAINED_GLASS_PANE)
                .add(Blocks.GREEN_STAINED_GLASS_PANE)
                .add(Blocks.RED_STAINED_GLASS_PANE)
                .add(Blocks.BLACK_STAINED_GLASS_PANE)
                .add(ModBlocks.CRACKED_GLASS)
                .add(ModBlocks.CRACKED_PACKED_ICE)
                .add(ModBlocks.WHITE_STAINED_CRACKED_GLASS)
                .add(ModBlocks.ORANGE_STAINED_CRACKED_GLASS)
                .add(ModBlocks.MAGENTA_STAINED_CRACKED_GLASS)
                .add(ModBlocks.LIGHT_BLUE_STAINED_CRACKED_GLASS)
                .add(ModBlocks.YELLOW_STAINED_CRACKED_GLASS)
                .add(ModBlocks.LIME_STAINED_CRACKED_GLASS)
                .add(ModBlocks.PINK_STAINED_CRACKED_GLASS)
                .add(ModBlocks.GRAY_STAINED_CRACKED_GLASS)
                .add(ModBlocks.LIGHT_GRAY_STAINED_CRACKED_GLASS)
                .add(ModBlocks.CYAN_STAINED_CRACKED_GLASS)
                .add(ModBlocks.PURPLE_STAINED_CRACKED_GLASS)
                .add(ModBlocks.BLUE_STAINED_CRACKED_GLASS)
                .add(ModBlocks.BROWN_STAINED_CRACKED_GLASS)
                .add(ModBlocks.GREEN_STAINED_CRACKED_GLASS)
                .add(ModBlocks.RED_STAINED_CRACKED_GLASS)
                .add(ModBlocks.BLACK_STAINED_CRACKED_GLASS);

        this.getOrCreateTagBuilder(BRICKABLE_2_HIT)
                .add(Blocks.PACKED_ICE)
                .add(Blocks.GLASS)
                .add(Blocks.WHITE_STAINED_GLASS)
                .add(Blocks.ORANGE_STAINED_GLASS)
                .add(Blocks.MAGENTA_STAINED_GLASS)
                .add(Blocks.LIGHT_BLUE_STAINED_GLASS)
                .add(Blocks.YELLOW_STAINED_GLASS)
                .add(Blocks.LIME_STAINED_GLASS)
                .add(Blocks.PINK_STAINED_GLASS)
                .add(Blocks.GRAY_STAINED_GLASS)
                .add(Blocks.LIGHT_GRAY_STAINED_GLASS)
                .add(Blocks.CYAN_STAINED_GLASS)
                .add(Blocks.PURPLE_STAINED_GLASS)
                .add(Blocks.BLUE_STAINED_GLASS)
                .add(Blocks.BROWN_STAINED_GLASS)
                .add(Blocks.GREEN_STAINED_GLASS)
                .add(Blocks.RED_STAINED_GLASS)
                .add(Blocks.BLACK_STAINED_GLASS);
    }
}
