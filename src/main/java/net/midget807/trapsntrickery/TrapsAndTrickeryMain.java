package net.midget807.trapsntrickery;

import net.fabricmc.api.ModInitializer;

import net.midget807.trapsntrickery.block.ModBlocks;
import net.midget807.trapsntrickery.entity.ModEntities;
import net.midget807.trapsntrickery.item.ModItems;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrapsAndTrickeryMain implements ModInitializer {
	public static final String MOD_ID = "trapsntrickery";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("wassup");
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();
	}
}