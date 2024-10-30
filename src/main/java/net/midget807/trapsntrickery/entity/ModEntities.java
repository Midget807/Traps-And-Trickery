package net.midget807.trapsntrickery.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.midget807.trapsntrickery.TrapsAndTrickeryMain;
import net.midget807.trapsntrickery.entity.trapsntrickery.BrickEntity;
import net.midget807.trapsntrickery.entity.trapsntrickery.SlimeProjectileEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntities {
    public static final EntityType<BrickEntity> BRICK_ENTITY_TYPE =
            Registry.register(Registries.ENTITY_TYPE, TrapsAndTrickeryMain.id("brick_entity"),
                    FabricEntityTypeBuilder.<BrickEntity>create(SpawnGroup.MISC, BrickEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(4)
                            .build());

    public static final EntityType<SlimeProjectileEntity> SLIME_PROJECTILE_ENTITY_TYPE =
            Registry.register(Registries.ENTITY_TYPE, TrapsAndTrickeryMain.id("slime_projectile"),
                    FabricEntityTypeBuilder.<SlimeProjectileEntity>create(SpawnGroup.MISC, SlimeProjectileEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(4)
                            .build());

    public static void registerModEntities() {
        TrapsAndTrickeryMain.LOGGER.info("Registering Mod Entities");
    }
}
