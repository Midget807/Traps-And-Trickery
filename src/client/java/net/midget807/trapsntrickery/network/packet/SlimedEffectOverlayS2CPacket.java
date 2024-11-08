package net.midget807.trapsntrickery.network.packet;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.midget807.trapsntrickery.TrapsAndTrickeryMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SlimedEffectOverlayS2CPacket {
    private static final int TEXTURE_WIDTH = 128;
    private static final int TEXTURE_HEIGHT = 64;
    private static final float MATRIX_SCALAR = 2.0f;
    private static int remainTicks;
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        boolean isMagma = buf.readBoolean();
        int duration = buf.readInt();
        remainTicks = duration;
        int globalWidth = client.getWindow().getScaledWidth();
        int globalHeight = client.getWindow().getScaledHeight();
        float centreX = (float) globalWidth / 2;
        float centreY = (float) globalHeight / 2;
        if (remainTicks > 0) {
            HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
                remainTicks -= (int) tickDelta;
                drawContext.getMatrices().push();
                drawContext.getMatrices().translate(centreX, centreY, 0.0f);
                RenderSystem.enableBlend();
                drawContext.getMatrices().push();
                drawContext.getMatrices().scale(MATRIX_SCALAR, MATRIX_SCALAR, MATRIX_SCALAR);
                if (remainTicks > 60) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 19), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 57) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 18), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 54) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 17), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 51) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 16), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 48) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 15), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 45) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 14), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 42) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 13), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 39) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 12), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 36) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 11), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 33) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 10), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 30) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 9), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 27) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 8), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 24) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 7), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 21) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 6), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 18) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 5), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 15) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 4), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 12) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 3), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 9) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 2), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 6) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 1), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else if (remainTicks > 3) {
                    drawContext.drawGuiTexture(getTexture(isMagma, 0), (int) (centreX - TEXTURE_WIDTH / 2), (int) (centreY - TEXTURE_HEIGHT / 2), TEXTURE_WIDTH, TEXTURE_HEIGHT);
                } else {
                    drawContext.getMatrices().pop();
                }
            });
        }
    }
    private static Identifier getTexture(boolean isMagma, int alpha) {
        return isMagma ? TrapsAndTrickeryMain.id("gui/effect/magma_slimed_" + alpha) : TrapsAndTrickeryMain.id("gui/effect/slimed_" + alpha);
    }
}
