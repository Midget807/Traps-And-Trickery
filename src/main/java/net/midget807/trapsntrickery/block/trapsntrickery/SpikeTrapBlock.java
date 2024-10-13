package net.midget807.trapsntrickery.block.trapsntrickery;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class SpikeTrapBlock extends PillarBlock implements LandingBlock, Waterloggable {
    public static final IntProperty EFFECT_USES = IntProperty.of("effect_uses", 0, 8);
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);
    public SpikeTrapBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(EFFECT_USES, 0).with(FACING, Direction.UP));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, EFFECT_USES);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction.getOpposite());
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.slowMovement(state, new Vec3d(0.8f, 0.75f, 0.8f));
            if (!world.isClient && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                double d = Math.abs(entity.getX() - entity.lastRenderX);
                double e = Math.abs(entity.getZ() - entity.lastRenderZ);
                if (d >= 0.003F || e >= 0.003F) {
                    entity.damage(world.getDamageSources().cactus(), 2.5f);
                    // TODO: 13/10/2024 add effect applier
                }
            }
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess worldAccess = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER)).with(FACING, ctx.getSide());
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    //What the hell does this do????
    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

}
