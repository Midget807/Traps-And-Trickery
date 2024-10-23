package net.midget807.trapsntrickery;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.midget807.trapsntrickery.datagen.*;

public class TrapsAndTrickeryDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		fabricDataGenerator.createPack().addProvider(ModBlockTagProvider::new);
		fabricDataGenerator.createPack().addProvider(ModItemTagProvider::new);
		fabricDataGenerator.createPack().addProvider(ModRecipeProvider::new);
		fabricDataGenerator.createPack().addProvider(ModModelProvider::new);
		fabricDataGenerator.createPack().addProvider(ModBlockLootTableProvider::new);
	}
}
