package net.midget807.trapsntrickery.entity.trapsntrickery;

import net.midget807.trapsntrickery.block.ModBlocks;
import net.midget807.trapsntrickery.block.trapsntrickery.LayingBrickBlock;
import net.midget807.trapsntrickery.datagen.ModBlockTagProvider;
import net.midget807.trapsntrickery.entity.ModEntities;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

public class BrickEntity extends ThrownItemEntity {
    private static boolean shouldDrop = true;
    private static boolean hasBrokenBlock = false;


    public BrickEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public BrickEntity(World world, double d, double e, double f) {
        super(ModEntities.BRICK_ENTITY_TYPE, d, e, f, world);
    }

    public BrickEntity(World world, LivingEntity livingEntity) {
        super(ModEntities.BRICK_ENTITY_TYPE, livingEntity, world);
    }

    /*@Override
    protected void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);
        if (!this.getWorld().isClient) {
            if (state.isIn(ModBlockTagProvider.BRICKABLE_BLOCKS)) {

            } else {
                this.shouldDrop = true;
            }
        }
    }*/

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        BlockState state = this.getWorld().getBlockState(blockHitResult.getBlockPos());
        if (state.isIn(ModBlockTagProvider.BRICKABLE_BLOCKS)) {
            shouldDrop = false;
            if (state.isIn(ModBlockTagProvider.BRICKABLE_1_HIT)) {
                this.getWorld().setBlockState(blockHitResult.getBlockPos(), Blocks.AIR.getDefaultState());
                if (!this.getWorld().isClient) {
                    this.getWorld().playSound(null, blockHitResult.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.2f, 0.8f);
                }
                this.setVelocity(this.getVelocity().multiply(0.3));
                hasBrokenBlock = true;
                shouldDrop = true;
            } else if (state.isIn(ModBlockTagProvider.BRICKABLE_2_HIT)) {
                this.placeCrackedBlock(state, blockHitResult.getBlockPos());
                hasBrokenBlock = false;
                shouldDrop = true;
            } else {
                hasBrokenBlock = false;
                shouldDrop = true;
            }
        } else {
            hasBrokenBlock = false;
            shouldDrop = true;
        }
        if (!this.getWorld().isClient && !hasBrokenBlock) {
            Direction direction = blockHitResult.getSide();
            if (direction != Direction.UP) {
                this.bounce(direction, 0.1f);
            } else {
                this.dropAsBlock(blockHitResult.getBlockPos(), this.getWorld(), direction);
            }
        }
    }

    private void placeCrackedBlock(BlockState state, BlockPos blockPos) {
        List<Block> brickable = List.of(
                Blocks.PACKED_ICE, 
                Blocks.GLASS,
                Blocks.WHITE_STAINED_GLASS,
                Blocks.ORANGE_STAINED_GLASS,
                Blocks.MAGENTA_STAINED_GLASS,
                Blocks.LIGHT_BLUE_STAINED_GLASS,
                Blocks.YELLOW_STAINED_GLASS,
                Blocks.LIME_STAINED_GLASS,
                Blocks.PINK_STAINED_GLASS,
                Blocks.GRAY_STAINED_GLASS,
                Blocks.LIGHT_GRAY_STAINED_GLASS,
                Blocks.CYAN_STAINED_GLASS,
                Blocks.PURPLE_STAINED_GLASS,
                Blocks.BLUE_STAINED_GLASS,
                Blocks.BROWN_STAINED_GLASS,
                Blocks.GREEN_STAINED_GLASS,
                Blocks.RED_STAINED_GLASS,
                Blocks.BLACK_STAINED_GLASS
        );
        if (state.getBlock() == brickable.get(0)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.CRACKED_PACKED_ICE.getDefaultState());
        } else if (state.getBlock() == brickable.get(1)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(2)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.WHITE_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(3)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.ORANGE_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(4)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.MAGENTA_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(5)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.LIGHT_BLUE_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(6)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.YELLOW_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(7)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.LIME_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(8)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.PINK_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(9)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.GRAY_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(10)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.LIGHT_GRAY_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(11)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.CYAN_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(12)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.PURPLE_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(13)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.BLUE_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(14)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.BROWN_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(15)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.GREEN_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(16)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.RED_STAINED_CRACKED_GLASS.getDefaultState());
        } else if (state.getBlock() == brickable.get(17)) {
            this.getWorld().setBlockState(blockPos, ModBlocks.BLACK_STAINED_CRACKED_GLASS.getDefaultState());
        }
    }
    /*private void placeShatteredGlass(BlockState state, BlockPos pos) {
        String name = state.getBlock().getName().toString();
        String[] parts = name.split("_");
        String key = parts[0];
        if (key == "glass") {
            this.getWorld().setBlockState(pos, ModBlocks.SHATTERED_GLASS.getDefaultState());
        }
    }*/

    private void dropAsBlock(BlockPos blockPos, World world, Direction direction) {
        BlockPos placePos = blockPos.offset(direction);
        BlockState placeState = world.getBlockState(placePos);
        BlockState currentState = world.getBlockState(blockPos);
        if (direction == Direction.UP) {
            if (currentState.isOf(ModBlocks.LAYING_BRICK)) {
                if (currentState.get(LayingBrickBlock.BRICKS) < 3) {
                    currentState.cycle(LayingBrickBlock.BRICKS);
                    this.discard();
                } else {
                    this.dropItem(Items.BRICK);
                    this.discard();
                }
            } else if (placeState.isOf(Blocks.AIR) || placeState.isOf(Blocks.CAVE_AIR) || placeState.isOf(Blocks.VOID_AIR) || placeState.isOf(Blocks.WATER)) {
                /*if (hasBrokenBlock) {
                    this.placeShatteredGlass();
                } else {
                    world.setBlockState(placePos, ModBlocks.LAYING_BRICK.getDefaultState());
                    this.discard();
                }*/
                world.setBlockState(placePos, ModBlocks.LAYING_BRICK.getDefaultState());
                this.discard();
            } else {
                this.dropItem(this.getDefaultItem());
                this.discard();
            }
        } else {
            this.dropItem(this.getDefaultItem());
            this.discard();
        }
    }

    private void bounce(Direction direction, float velocityMultiplier) {
        if (direction == Direction.DOWN) {
            this.setVelocity(
                    this.getVelocity().x * velocityMultiplier,
                    this.getVelocity().y -1,
                    this.getVelocity().z * velocityMultiplier
            );
        } else if (direction == Direction.EAST || direction == Direction.WEST) {
            this.setVelocity(
                    this.getVelocity().x * velocityMultiplier * -1,
                    this.getVelocity().y * velocityMultiplier,
                    this.getVelocity().z * velocityMultiplier
            );
        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            this.setVelocity(
                    this.getVelocity().x * velocityMultiplier,
                    this.getVelocity().y * velocityMultiplier,
                    this.getVelocity().z * velocityMultiplier * -1
            );
        }
    }

    @Override
    protected Item getDefaultItem() {
        return Items.BRICK;
    }

}
