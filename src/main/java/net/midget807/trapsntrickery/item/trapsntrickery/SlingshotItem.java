package net.midget807.trapsntrickery.item.trapsntrickery;

import net.midget807.trapsntrickery.datagen.ModItemTagProvider;
import net.midget807.trapsntrickery.entity.trapsntrickery.MagmaProjectileEntity;
import net.midget807.trapsntrickery.entity.trapsntrickery.SlimeProjectileEntity;
import net.midget807.trapsntrickery.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class SlingshotItem extends RangedWeaponItem {
    public static final Predicate<ItemStack> SLINGSHOT_PROJECTILES = stack -> stack.isIn(ModItemTagProvider.SLINGSHOT_PROJECTILES);
    public SlingshotItem(Settings settings) {
        super(settings);
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return SLINGSHOT_PROJECTILES;
    }

    @Override
    public int getRange() {
        return 10;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        boolean hasProjectile = !user.getProjectileType(itemStack).isEmpty();
        if (!user.getAbilities().creativeMode && !hasProjectile) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            ItemStack itemStack = player.getProjectileType(stack);
            if (!itemStack.isEmpty()) {
                int timeUsed = this.getMaxUseTime(stack) - remainingUseTicks;
                float pullProgress = getPullProgress(timeUsed);
                if (!(pullProgress < 0.1)) {
                    if (!world.isClient) {
                        SlimeCubeItem slimeCubeItem = (SlimeCubeItem) (itemStack.getItem() instanceof SlimeCubeItem ? itemStack.getItem() : ModItems.SLIME_CUBE_ITEM);
                        PersistentProjectileEntity persistentProjectileEntity = slimeCubeItem.createSlime(world, itemStack, player);
                        persistentProjectileEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, pullProgress * 1.5f, 0.5f);

                        stack.damage(1, player, p -> p.sendToolBreakStatus(player.getActiveHand()));
                        world.spawnEntity(persistentProjectileEntity);

                    }
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_SLIME_JUMP, SoundCategory.PLAYERS, 1.0f, 1.0f/ (world.getRandom().nextFloat() * 0.4F + 1.2F) + pullProgress * 0.5F);
                    if (!player.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) {
                            player.getInventory().removeOne(itemStack);
                        }
                    }
                    player.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }

    public float getPullProgress(int timeUsed) {
        float timeSec = timeUsed / 20.0f;
        timeSec = (timeSec * timeSec + timeSec * 2.0f) / 3.0f /*3.0f dilates function*/;
        if (timeSec > 1.0f) {
            timeSec = 1.0f;
        }
        return timeSec;
    }
}
