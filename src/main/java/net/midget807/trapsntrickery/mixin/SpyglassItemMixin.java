package net.midget807.trapsntrickery.mixin;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.trapsntrickery.datagen.ModItemTagProvider;
import net.midget807.trapsntrickery.item.ModItems;
import net.midget807.trapsntrickery.item.trapsntrickery.LensItem;
import net.midget807.trapsntrickery.network.ModMessages;
import net.midget807.trapsntrickery.util.SpyglassTooltipData;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TrappedChestBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpyglassItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Mixin(SpyglassItem.class)
public class SpyglassItemMixin extends Item {
    @Unique
    private static final String LENS_KEY = "Lens";
    @Unique
    private static boolean shouldCheckChest = false;

    public SpyglassItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            ItemStack itemStack = slot.getStack();
            if (itemStack.isEmpty()) {
                this.playRemoveSound(player);
                Optional<ItemStack> removedLens = removeStack(stack);
                removedLens.ifPresent(slot::insertStack);
            } else if (addLens(stack, slot.getStack())) {
                this.playInsertSound(player);
            }
            return true;
        }
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            if (otherStack.isEmpty()) {
                removeStack(stack).ifPresent((itemStack) -> {
                    this.playRemoveSound(player);
                    cursorStackReference.set(itemStack);
                });
            } else if (addLens(stack, otherStack)) {
                this.playInsertSound(player);
                otherStack.setCount(0);
            }
            return true;
        } else {
            return false;
        }
    }

    private void playInsertSound(PlayerEntity player) {
    }
    private void playRemoveSound(PlayerEntity player) {
    }
    @Unique
    private static boolean addLens(ItemStack spyglass, ItemStack stack) {
        if (!stack.isIn(ModItemTagProvider.LENSES)) {
            return false;
        } else {
            NbtCompound nbtCompound = spyglass.getOrCreateNbt();
            if (!nbtCompound.contains(LENS_KEY)) {
                nbtCompound.put(LENS_KEY, new NbtList());
            }
            NbtList lenses = nbtCompound.getList(LENS_KEY, NbtElement.COMPOUND_TYPE);
            if (!lenses.isEmpty()) {
                return false;
            } else {
                lenses.add(0, stack.writeNbt(new NbtCompound()));
                stack.setCount(0);
                return true;
            }
        }
    }


    private static Optional<ItemStack> removeStack(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains(LENS_KEY)) {
            return Optional.empty();
        } else {
            NbtList nbtList = nbtCompound.getList(LENS_KEY, NbtElement.COMPOUND_TYPE);
            if (nbtList.isEmpty()) {
                return Optional.empty();
            } else {
                ItemStack itemStack = ItemStack.fromNbt(nbtList.getCompound(0));
                nbtList.remove(0);
                if (nbtList.isEmpty()) {
                    stack.removeSubNbt(LENS_KEY);
                }
                return Optional.of(itemStack);
            }
        }
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        getActiveLens(stack).ifPresent((lens) -> {
            tooltip.add(Text.translatable(lens.getTranslationKey()).formatted(Formatting.WHITE));
            Item lensAsItem = lens.getItem();
            if (lensAsItem instanceof LensItem lensItem) {
                lensItem.appendTooltip(stack, world, tooltip, context);
            }
        });
    }

    @Unique
    private static Optional<ItemStack> getActiveLens(ItemStack stack) {
        if (!(stack.getItem() instanceof SpyglassItem)) {
            return Optional.empty();
        } else {
            List<ItemStack> lenses = getLensStack(stack);
            if (lenses.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of((ItemStack) lenses.get(0));
            }
        }
    }

    @Unique
    private static List<ItemStack> getLensStack(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound == null) {
            return List.of();
        } else {
            Stream<NbtElement> stacks = nbtCompound.getList(LENS_KEY, NbtElement.COMPOUND_TYPE).stream();
            return stacks.map(NbtCompound.class::cast).map(ItemStack::fromNbt).toList();
        }
    }
    @Unique
    private static boolean isLensActive(ItemStack spyglass, Item lens) {
        return (Boolean)getActiveLens(spyglass).map((stack) -> {
            return stack.isOf(lens);
        }).orElse(false);
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        DefaultedList<ItemStack> lenses = DefaultedList.of();
        lenses.addAll(getLensStack(stack));
        return Optional.of(new SpyglassTooltipData(lenses));
    }

    @Inject(method = "use", at = @At("HEAD"))
    private void trapsntrickery$trappedChestCheck(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack spyglass = user.getStackInHand(hand);
    }
    @Unique
    private PacketByteBuf buf = PacketByteBufs.create();
    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!world.isClient) {
            if (user.getType() == EntityType.PLAYER) {
                //buf.writeBoolean(checkChest(isLensActive(user.getActiveItem(), ModItems.REVEALING_LENS), ((PlayerEntity) user)));
                if (checkChest(isLensActive(user.getActiveItem(), ModItems.REVEALING_LENS), (user))) {
                    //ServerPlayNetworking.send((ServerPlayerEntity) user, ModMessages.CHEST_NOTIFIER, buf);
                    ((PlayerEntity) user).sendMessage(Text.literal("This chest is ").formatted(Formatting.RED).append(Text.literal("TRAPPED").formatted(Formatting.BOLD).formatted(Formatting.RED)), true);
                } else {
                    //ServerPlayNetworking.send((ServerPlayerEntity) user, ModMessages.CHEST_NOTIFIER, buf);
                }
                getBlockHit(user);
            }
        }

    }
    @Unique
    private HitResult blockHit;
    @Unique
    private void getBlockHit(LivingEntity player) {
        this.blockHit = player.raycast(8.0, 0.0F, false);
    }
    @Unique
    private boolean checkChest(boolean isLensActive, LivingEntity player) {
        if (blockHit == null) {
            return false;
        }
        if (isLensActive) {
            if (this.blockHit.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult) this.blockHit).getBlockPos();
                BlockState blockState = player.getWorld().getBlockState(blockPos);
                return blockState.getBlock() instanceof TrappedChestBlock;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Unique
    private static boolean canSeeBlock(LivingEntity player, BlockPos blockPos) {
        Vec3d blockVec = new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        Vec3d playerVec = new Vec3d(player.getX(), player.getEyeY(), player.getZ());
        return playerVec.distanceTo(blockVec) > 128.0
                ? false
                : player.getWorld().raycast(new RaycastContext(blockVec, playerVec, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player)).getType() == HitResult.Type.MISS;
    }
}
