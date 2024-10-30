package net.midget807.trapsntrickery.effect;

import net.midget807.trapsntrickery.TrapsAndTrickeryMain;
import net.midget807.trapsntrickery.effect.trapsntrickery.EmptyStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEffects {
    public static final StatusEffect SLIMED = registerEffect("slimed", new EmptyStatusEffect(StatusEffectCategory.HARMFUL, 0xe5ffc3));
    public static final StatusEffect MAGMA_SLIMED = registerEffect("magma_slimed", new EmptyStatusEffect(StatusEffectCategory.HARMFUL, 0xffc3c3)); // TODO: 29/10/2024 update colour
    public static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, name, effect);
    }
    public static void registerModEffects() {
        TrapsAndTrickeryMain.LOGGER.info("Registering Mod Effects");
    }
}
