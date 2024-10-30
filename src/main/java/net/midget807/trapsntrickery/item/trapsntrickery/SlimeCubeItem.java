package net.midget807.trapsntrickery.item.trapsntrickery;

import net.midget807.trapsntrickery.entity.trapsntrickery.MagmaProjectileEntity;
import net.midget807.trapsntrickery.entity.trapsntrickery.SlimeProjectileEntity;
import net.midget807.trapsntrickery.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class SlimeCubeItem extends Item {
    public final boolean isMagmaCube;
    public final EntityType<? extends SlimeEntity> type;
    public SlimeCubeItem(Settings settings, boolean isMagmaCube, EntityType<? extends SlimeEntity> type) {
        super(settings);
        this.isMagmaCube = isMagmaCube;
        this.type = type;
    }
    public PersistentProjectileEntity createSlime(World world, ItemStack stack, LivingEntity shooter) {
        SlimeProjectileEntity slimeCubeEntity = new SlimeProjectileEntity(world, shooter);
        slimeCubeEntity.initFromStack(stack);
        MagmaProjectileEntity magmaProjectileEntity = new MagmaProjectileEntity(world, shooter);
        magmaProjectileEntity.initFromStack(stack);
        return isMagmaCube ? magmaProjectileEntity : slimeCubeEntity;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        if (!(world instanceof ServerWorld) && !player.isSneaking()) {
            return ActionResult.SUCCESS;
        } else {
            BlockPos blockPos = context.getBlockPos();
            BlockState state = world.getBlockState(blockPos);
            Direction direction = context.getSide();

            BlockPos spawnPos;
            if (state.getCollisionShape(world, blockPos).isEmpty()) {
                spawnPos = blockPos;
            } else {
                spawnPos = blockPos.offset(direction);
            }
            ItemStack itemStack = context.getStack();
            SlimeEntity slimeEntity = getEntityToSpawn(itemStack, world);
            slimeEntity.updatePosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
            slimeEntity.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
            world.spawnEntity(slimeEntity);
            if (!player.getAbilities().creativeMode) {
                player.setStackInHand(context.getHand(), ItemStack.EMPTY);
            }
            return ActionResult.CONSUME;
        }
    }
    public SlimeEntity getEntityToSpawn(ItemStack itemStack, World world) {
        SlimeEntity slimeEntity;
        if (itemStack.isOf(ModItems.SLIME_CUBE_ITEM)) {
            slimeEntity = new SlimeEntity(EntityType.SLIME, world).getType().create(world);
            slimeEntity.setSize(1, true);
        } else if (itemStack.isOf(ModItems.MAGMA_CUBE_ITEM)) {
            slimeEntity = new SlimeEntity(EntityType.MAGMA_CUBE, world).getType().create(world);
            slimeEntity.setSize(1, true);
        } else {
            slimeEntity = null;
        }
        return slimeEntity;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (!player.isSneaking()) {
            if (blockHitResult.getType() != HitResult.Type.BLOCK) {
                return TypedActionResult.pass(itemStack);
            } else if (!(world instanceof ServerWorld)) {
                return TypedActionResult.success(itemStack);
            } else {
                BlockPos blockPos = blockHitResult.getBlockPos();
                if (!(world.getBlockState(blockPos).getBlock() instanceof FluidBlock)) {
                    return TypedActionResult.pass(itemStack);
                } else if (world.canPlayerModifyAt(player, blockPos) && player.canPlaceOn(blockPos, blockHitResult.getSide(), itemStack)) {
                    SlimeEntity slimeEntity = getEntityToSpawn(itemStack, world);
                    if (slimeEntity == null) {
                        return TypedActionResult.pass(itemStack);
                    } else {
                        if (!player.getAbilities().creativeMode) {
                            itemStack.decrement(1);
                        }
                        player.incrementStat(Stats.USED.getOrCreateStat(this));
                        world.emitGameEvent(player, GameEvent.ENTITY_PLACE, slimeEntity.getPos());
                        return TypedActionResult.consume(itemStack);
                    }
                } else {
                    return TypedActionResult.fail(itemStack);
                }
            }
        } else {
            return TypedActionResult.pass(itemStack);
        }
    }

}
