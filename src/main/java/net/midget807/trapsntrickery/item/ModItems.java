package net.midget807.trapsntrickery.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.midget807.trapsntrickery.TrapsAndTrickeryMain;
import net.midget807.trapsntrickery.item.trapsntrickery.DebuggerItem;
import net.midget807.trapsntrickery.item.trapsntrickery.LensItem;
import net.midget807.trapsntrickery.item.trapsntrickery.SlimeCubeItem;
import net.midget807.trapsntrickery.item.trapsntrickery.SlingshotItem;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final Item DEBUGGER = registerItem("debugger", new DebuggerItem(new FabricItemSettings()));
    public static final Item REVEALING_LENS = registerItem("lens_reveal", new LensItem(new FabricItemSettings().maxCount(1), true));
    public static final Item SLIME_CUBE_ITEM = registerItem("slime_cube", new SlimeCubeItem(new FabricItemSettings().maxCount(1), false, EntityType.SLIME));
    public static final Item MAGMA_CUBE_ITEM = registerItem("magma_cube", new SlimeCubeItem(new FabricItemSettings().maxCount(1), true, EntityType.MAGMA_CUBE));
    public static final Item SLINGSHOT = registerItem("slingshot", new SlingshotItem(new FabricItemSettings().maxCount(1)));
    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, TrapsAndTrickeryMain.id(name), item);
    }
    public static void registerModItems() {
        TrapsAndTrickeryMain.LOGGER.info("Registering Mod Items");
    }
}
