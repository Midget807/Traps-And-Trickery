package net.midget807.trapsntrickery.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.midget807.trapsntrickery.block.ModBlocks;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.LAYING_BRICK);
        addDrop(ModBlocks.WOODEN_SPIKE_TRAP);
        addDrop(ModBlocks.CRACKED_PACKED_ICE);
        addDropWithSilkTouch(ModBlocks.CRACKED_GLASS);
    }
}
