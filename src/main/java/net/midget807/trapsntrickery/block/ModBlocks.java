package net.midget807.trapsntrickery.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.midget807.trapsntrickery.TrapsAndTrickeryMain;
import net.midget807.trapsntrickery.block.trapsntrickery.LayingBrickBlock;
import net.midget807.trapsntrickery.block.trapsntrickery.ShatteredGlassBlock;
import net.midget807.trapsntrickery.block.trapsntrickery.SpikeTrapBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class ModBlocks {
    public static final Block WOODEN_SPIKE_TRAP = registerBlock("wooden_spike_trap", new SpikeTrapBlock(FabricBlockSettings.create().mapColor(MapColor.BROWN).instrument(Instrument.BASEDRUM).nonOpaque().sounds(BlockSoundGroup.WOOD).ticksRandomly().strength(1.5f, 3.0f).pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never)));
    public static final Block LAYING_BRICK = registerBlock("laying_brick", new LayingBrickBlock(FabricBlockSettings.create().mapColor(MapColor.RED).nonOpaque().strength(0.1F).sounds(BlockSoundGroup.STONE).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block CRACKED_PACKED_ICE = registerBlock("cracked_packed_ice", new Block(FabricBlockSettings.copyOf(Blocks.PACKED_ICE)));
    public static final Block CRACKED_GLASS = registerBlock("cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block WHITE_STAINED_CRACKED_GLASS = registerBlock("white_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block ORANGE_STAINED_CRACKED_GLASS = registerBlock("orange_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block MAGENTA_STAINED_CRACKED_GLASS = registerBlock("magenta_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block LIGHT_BLUE_STAINED_CRACKED_GLASS = registerBlock("light_blue_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block YELLOW_STAINED_CRACKED_GLASS = registerBlock("yellow_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block LIME_STAINED_CRACKED_GLASS = registerBlock("lime_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block PINK_STAINED_CRACKED_GLASS = registerBlock("pink_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block GRAY_STAINED_CRACKED_GLASS = registerBlock("gray_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block LIGHT_GRAY_STAINED_CRACKED_GLASS = registerBlock("light_gray_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block CYAN_STAINED_CRACKED_GLASS = registerBlock("cyan_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block PURPLE_STAINED_CRACKED_GLASS = registerBlock("purple_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block BLUE_STAINED_CRACKED_GLASS = registerBlock("blue_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block BROWN_STAINED_CRACKED_GLASS = registerBlock("brown_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block GREEN_STAINED_CRACKED_GLASS = registerBlock("green_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block RED_STAINED_CRACKED_GLASS = registerBlock("red_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block BLACK_STAINED_CRACKED_GLASS = registerBlock("black_stained_cracked_glass", new Block(FabricBlockSettings.copyOf(Blocks.GLASS)));

    //public static final Block SHATTERED_GLASS = registerBlock("shattered_glass", new ShatteredGlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, TrapsAndTrickeryMain.id(name), block);
    }
    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, TrapsAndTrickeryMain.id(name), new BlockItem(block, new FabricItemSettings()));
    }
    public static void registerModBlocks() {
        TrapsAndTrickeryMain.LOGGER.info("Registering Mod Blocks");
    }
}
