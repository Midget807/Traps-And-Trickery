package net.midget807.trapsntrickery.block.trapsntrickery;

import net.midget807.trapsntrickery.block.ModBlocks;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LayingBrickBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final VoxelShape ONE_BRICK = Block.createCuboidShape(0,0,0,16, 4, 16);
    public static final VoxelShape TWO_BRICK = Block.createCuboidShape(0,0,0,16, 4, 16);
    public static final VoxelShape THREE_BRICK = Block.createCuboidShape(0,0,0,16, 8, 16);
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final IntProperty BRICKS = IntProperty.of("bricks", 1, 3);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public LayingBrickBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(BRICKS, 1).with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape;
        switch (state.get(BRICKS)) {
            case 2:
                shape = TWO_BRICK;
                break;
            case 3:
                shape = THREE_BRICK;
                break;
            default:
                shape = ONE_BRICK;

        }
        return shape;
    }

    @Override
    public Item asItem() {
        return Items.BRICK;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(Items.BRICK);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction() && context.getStack().getItem() == this.asItem() && state.get(BRICKS) < 3 || super.canReplace(state, context);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        return state.isOf(this) ? state.cycle(BRICKS) : this.getDefaultState().with(WATERLOGGED, state.getFluidState().getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BRICKS, WATERLOGGED, FACING);
    }
    public static ActionResult placeBrickStack(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(Items.BRICK)) {
            Block block = ModBlocks.LAYING_BRICK;
            ItemPlacementContext context = new ItemPlacementContext(world, player, hand, stack, hitResult);
            BlockState state = block.getPlacementState(context);
            BlockPos pos = context.getBlockPos();
            if (state != null && block.canPlaceAt(state, world, pos) && world.getBlockState(pos).canReplace(context)) {
                world.setBlockState(pos, state, 11);
                BlockState placeState = world.getBlockState(pos);
                if (placeState.isOf(state.getBlock())) {
                    placeState.getBlock().onPlaced(world, pos, placeState, player, stack);
                    if (player instanceof ServerPlayerEntity) {
                        ServerPlayerEntity serverPlayer = ((ServerPlayerEntity) player);
                        Criteria.PLACED_BLOCK.trigger(serverPlayer, pos, stack);
                    }
                }
                BlockSoundGroup blockSoundGroup = placeState.getSoundGroup();
                world.playSound(player, pos, placeState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);
                world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Emitter.of(player, placeState));
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                }
                return ActionResult.success(world.isClient);
            } else {
                return ActionResult.PASS;
            }
        } else {
            return ActionResult.PASS;
        }
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (state.get(BRICKS) > 1) {
            switch (state.get(BRICKS)) {
                case 3:
                    world.setBlockState(pos, state.with(BRICKS, 2), 11);
                    break;
                default:
                    world.setBlockState(pos, state.with(BRICKS, 1), 11);
                    break;
            }
        }
    }
}
