package net.midget807.trapsntrickery.util;

import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class SpyglassTooltipData implements TooltipData {
    private final DefaultedList<ItemStack> inventory;
    public SpyglassTooltipData(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }
    public DefaultedList<ItemStack> getInventory() {
        return this.inventory;
    }
}
