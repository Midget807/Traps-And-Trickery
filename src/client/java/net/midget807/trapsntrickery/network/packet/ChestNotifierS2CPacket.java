package net.midget807.trapsntrickery.network.packet;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ChestNotifierS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        boolean isTrappedChest = buf.readBoolean();
        DrawContext context = new DrawContext(client, client.getBufferBuilders().getEntityVertexConsumers());
        int centerX = client.getWindow().getScaledWidth() / 2;
        int centerY = client.getWindow().getScaledHeight() / 2;
        int fadeInTicks = 5;
        int fadeOutTicks = 5;
        //InGameHud.class ln297 -> use method


            HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
                Text text;
                int color = 0xFF5555;
                int numberOfChar;
                int approxTextLengthPixels = 0;
                if (isTrappedChest) {
                    text = Text.literal("This chest is ").append(Text.literal("TRAPPED").formatted(Formatting.BOLD));
                    numberOfChar = text.toString().length();
                    approxTextLengthPixels = (numberOfChar / 2) * 2;
                } else {
                    text = Text.literal(" ");
                    color = 0xFFFFFF00;
                }
                drawContext.drawText(client.textRenderer, text, centerX - approxTextLengthPixels + 2, centerY, color, true);

            });
    }
}
