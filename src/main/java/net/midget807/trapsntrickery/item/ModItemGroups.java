package net.midget807.trapsntrickery.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.midget807.trapsntrickery.TrapsAndTrickeryMain;
import net.midget807.trapsntrickery.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModItemGroups {
    public static final ItemGroup MAIN = registerItemGroup("main", FabricItemGroup.builder()
            .displayName(Text.translatable("itemgroup.trapsntrickery.main"))
            .icon(() -> new ItemStack(ModBlocks.WOODEN_SPIKE_TRAP.asItem()))
            .entries((displayContext, entries) -> {
                entries.add(ModBlocks.WOODEN_SPIKE_TRAP);
                entries.add(ModBlocks.CRACKED_GLASS);
                entries.add(ModBlocks.CRACKED_PACKED_ICE);
                entries.add(ModBlocks.WHITE_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.ORANGE_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.MAGENTA_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.LIGHT_BLUE_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.YELLOW_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.LIME_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.PINK_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.GRAY_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.LIGHT_GRAY_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.CYAN_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.PURPLE_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.BLUE_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.BROWN_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.GREEN_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.RED_STAINED_CRACKED_GLASS);
                entries.add(ModBlocks.BLACK_STAINED_CRACKED_GLASS);

                entries.add(ModItems.REVEALING_LENS);
                entries.add(ModItems.SLIME_CUBE_ITEM);
                entries.add(ModItems.MAGMA_CUBE_ITEM);
                entries.add(ModItems.SLINGSHOT);
            }).build());
    public static ItemGroup registerItemGroup(String name, ItemGroup itemGroup) {
        return Registry.register(Registries.ITEM_GROUP, TrapsAndTrickeryMain.id(name), itemGroup);
    }
    public static void registerModItemGroups() {
        TrapsAndTrickeryMain.LOGGER.info("Registering Mod Item Groups");
    }
}
