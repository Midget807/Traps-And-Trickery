package net.midget807.trapsntrickery.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.midget807.trapsntrickery.util.SpyglassTooltipData;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class SpyglassTooltipComponent implements TooltipComponent {
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("container/bundle/background");
    private static final int CELL_WIDTH = 18;
    private static final int CELL_HEIGHT = 18;
    private final DefaultedList<ItemStack> inventory;
    public SpyglassTooltipComponent(SpyglassTooltipData data) {
        this.inventory = data.getInventory();
    }
    @Override
    public int getHeight() {
        return CELL_HEIGHT;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return CELL_WIDTH;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        context.drawGuiTexture(BACKGROUND_TEXTURE, x, y, CELL_WIDTH, CELL_HEIGHT);
        this.drawSlot(x, y - 1, 0, context, textRenderer);
    }

    private void drawSlot(int x, int y, int index, DrawContext context, TextRenderer textRenderer) {
        this.draw(context, x, y, Sprite.SLOT);
        if (!(index >= this.inventory.size())) {
            ItemStack itemStack = this.inventory.get(index);
            context.drawItem(itemStack, x + 1, y + 1, index);
            context.drawItemInSlot(textRenderer, itemStack, x + 1, y + 1);
        }
    }
    private void draw(DrawContext context, int x, int y, SpyglassTooltipComponent.Sprite sprite) {
        context.drawGuiTexture(sprite.texture, x, y, 0, sprite.width, sprite.height);
    }
    public static @Nullable TooltipComponent register(TooltipData data) {
        if (data instanceof SpyglassTooltipData spyglassTooltipData) {
            return new SpyglassTooltipComponent(spyglassTooltipData);
        } else {
            return null;
        }
    }
    @Environment(EnvType.CLIENT)
    static enum Sprite {
        BLOCKED_SLOT(new Identifier("container/bundle/blocked_slot"), 18, 20),
        SLOT(new Identifier("container/bundle/slot"), 18, 20);

        public final Identifier texture;
        public final int width;
        public final int height;

        private Sprite(Identifier texture, int width, int height) {
            this.texture = texture;
            this.width = width;
            this.height = height;
        }
    }
}
