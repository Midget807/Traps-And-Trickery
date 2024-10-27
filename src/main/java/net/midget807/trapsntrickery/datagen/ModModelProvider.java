package net.midget807.trapsntrickery.datagen;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.midget807.trapsntrickery.TrapsAndTrickeryMain;
import net.midget807.trapsntrickery.block.ModBlocks;
import net.midget807.trapsntrickery.block.trapsntrickery.LayingBrickBlock;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.Xoroshiro128PlusPlusRandom;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerRod(ModBlocks.CRACKED_GLASS);
        //this.registerLayingBrick(blockStateModelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
    private void registerLayingBrick(BlockStateModelGenerator generator) {
        TextureKey textureKey = TextureKey.of("brick");
        int height = 3;
        int rotation = 4;
        int colors = 4;
        Block block = ModBlocks.LAYING_BRICK;
        Identifier[][][] models = new Identifier[height][rotation][colors];
        MultipartBlockStateSupplier supplier = MultipartBlockStateSupplier.create(block);
        int h;
        for (int h2 = 0; h2 < height; ++h2) {
            for (h = 0; h < rotation; ++h) {
                for (int c = 0; c < colors; ++c) {
                    Model template = new Model(Optional.of(TrapsAndTrickeryMain.id("block/template_laying_brick_" + h2 + "_r" + h)), Optional.empty(), new TextureKey[]{textureKey});
                    TextureMap texture = (new TextureMap()).register(textureKey, TextureMap.getSubId(block, "_" + c));
                    models[h2][h][c] = template.upload(block, "_" + h2 + "_r" + h + "_" + c, texture, generator.modelCollector);
                }
            }
        }
        Random random = new Xoroshiro128PlusPlusRandom(0L);
        for (h = 0; h < height; ++h) {
            ObjectArrayList<BlockStateVariant> list = new ObjectArrayList<>();
            int v;
            for (v = 0; v < rotation; ++v) {
                for (int c = 0; c < colors; ++c) {
                    Identifier model = models[h][v][c];
                    list.add(BlockStateVariant.create().put(VariantSettings.MODEL, model));
                    list.add(BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.Y, VariantSettings.Rotation.R90));
                    list.add(BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.Y, VariantSettings.Rotation.R180));
                    list.add(BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.Y, VariantSettings.Rotation.R270));
                }
            }
            Util.shuffle(list, random);
            for (v = h + 1; v <= height; ++v) {
                supplier.with(When.create().set(LayingBrickBlock.BRICKS, v), list);
            }
        }
        generator.excludeFromSimpleItemModelGeneration(block);
        generator.blockStateCollector.accept(supplier);
    }
}
