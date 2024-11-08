package net.midget807.trapsntrickery.effect.trapsntrickery;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.trapsntrickery.network.ModMessages;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class SlimedEffect extends StatusEffect {
    private final boolean isMagma;
    public SlimedEffect(StatusEffectCategory category, int color, boolean isMagma) {
        super(category, color);
        this.isMagma = isMagma;
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(isMagma);
        buf.writeInt(10 * 20 + amplifier * 3);
        ServerPlayerEntity player = (ServerPlayerEntity) entity;
        ServerPlayNetworking.send(player, isMagma ? ModMessages.MAGMA_SLIMED_EFFECT_OVERLAY : ModMessages.SLIMED_EFFECT_OVERLAY, buf);
    }
}
