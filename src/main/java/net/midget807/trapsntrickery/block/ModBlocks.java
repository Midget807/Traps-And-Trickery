package net.midget807.trapsntrickery.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.midget807.trapsntrickery.TrapsAndTrickeryMain;
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

public class ModBlocks {
    public static final Block WOODEN_SPIKE_TRAP = registerBock("", new SpikeTrapBlock(FabricBlockSettings.create().mapColor(MapColor.BROWN).instrument(Instrument.BASEDRUM).nonOpaque().sounds(BlockSoundGroup.WOOD).ticksRandomly().strength(1.5f, 3.0f).dynamicBounds().offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never)));
    public static Block registerBock(String name, Block block) {
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
