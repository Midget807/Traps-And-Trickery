package net.midget807.trapsntrickery.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.trapsntrickery.TrapsAndTrickeryMain;
import net.midget807.trapsntrickery.network.packet.ChestNotifierS2CPacket;
import net.midget807.trapsntrickery.network.packet.SlimedEffectOverlayS2CPacket;
import net.minecraft.util.Identifier;

public class ModClientMessages {
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.CHEST_NOTIFIER, ChestNotifierS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.SLIMED_EFFECT_OVERLAY, SlimedEffectOverlayS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.MAGMA_SLIMED_EFFECT_OVERLAY, SlimedEffectOverlayS2CPacket::receive);
    }
}
