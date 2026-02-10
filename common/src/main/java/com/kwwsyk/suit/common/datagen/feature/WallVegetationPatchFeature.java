package com.kwwsyk.suit.common.datagen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class WallVegetationPatchFeature extends Feature<WallVegetationPatchConfiguration> {
    public WallVegetationPatchFeature(Codec<WallVegetationPatchConfiguration> codec) {
        super(codec);
    }

    /**
     * Places the given feature at the given location.
     * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated, that they can safely generate into.
     *
     * @param context A context object with a reference to the level and the position
     *                the feature is being placed at
     */
    @Override
    public boolean place(FeaturePlaceContext<WallVegetationPatchConfiguration> context) {
        return place(context, null);
    }

    public boolean place(FeaturePlaceContext<WallVegetationPatchConfiguration> context, @Nullable Direction direction) {
        WorldGenLevel worldgenlevel = context.level();
        WallVegetationPatchConfiguration config = context.config();
        RandomSource randomsource = context.random();
        BlockPos blockpos = context.origin();
        Predicate<BlockState> predicate = p_204782_ -> p_204782_.is(config.replaceable);
        int h = config.hvRadius.sample(randomsource) + 1;
        int v = config.hvRadius.sample(randomsource) + 1;
        if(direction == null) direction = availableStartDirections(worldgenlevel, blockpos).getFirst().getOpposite();//innerN direction
        Set<BlockPos> upSpaces = new HashSet<>();
        Set<BlockPos> downSpaces = new HashSet<>();
        Set<BlockPos> set = this.placeWallPatch(worldgenlevel, config, randomsource, blockpos, predicate, h, v, direction, upSpaces, downSpaces);
        for (BlockPos blockpos2 : set) {
            if (config.vegetationChance > 0.0F && randomsource.nextFloat() < config.vegetationChance) {
                ChunkGenerator chunkGenerator1 = context.chunkGenerator();
                config.sideVegetationFeature.value().place(worldgenlevel, chunkGenerator1, randomsource, blockpos2.relative(direction));
            }
        }
        for (BlockPos blockpos1 : upSpaces) {
            if (config.vegetationChance > 0.0F && randomsource.nextFloat() < config.vegetationChance) {
                ChunkGenerator chunkGenerator = context.chunkGenerator();
                config.upVegetationFeature.value().place(worldgenlevel, chunkGenerator, randomsource, blockpos1.relative(Direction.UP));
            }
        }
        for (BlockPos blockpos1 : downSpaces) {
            if (config.vegetationChance > 0.0F && randomsource.nextFloat() < config.vegetationChance) {
                ChunkGenerator chunkGenerator = context.chunkGenerator();
                config.downVegetationFeature.value().place(worldgenlevel, chunkGenerator, randomsource, blockpos1.relative(Direction.DOWN));
            }
        }
        return !set.isEmpty();
    }

    /**
     * Places a wall vegetation patch in the specified area based on the given configuration.
     *
     * @param level           The world generation level where the vegetation patch is to be placed.
     * @param config          The configuration object defining the properties of the vegetation patch.
     * @param random          A random number generator used for determining placement conditions.
     * @param pos             The starting position for the placement of the vegetation patch.
     * @param state           A predicate to filter block states for determining suitable positions.
     * @param hRadius         The horizontal radius of the vegetation patch.
     * @param vRadius         The vertical radius of the vegetation patch.
     * @param direction       The direction for vegetation growth, such as DOWN for floor moss, or null to automatically determine a suitable direction.
     * @param upSpaces Air blockposes on placed blocks.
     * @param downSpaces Air blockposes off placed blocks.
     * @return A set of block positions where the vegetation patch was successfully placed.
     */
    protected Set<BlockPos> placeWallPatch(
        WorldGenLevel level,
        WallVegetationPatchConfiguration config,
        RandomSource random,
        BlockPos pos,
        Predicate<BlockState> state,
        int hRadius,
        int vRadius,
        Direction direction,
        Set<BlockPos> upSpaces,
        Set<BlockPos> downSpaces
    ) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        BlockPos.MutableBlockPos mutablePosCpy = mutablePos.mutable();
        //Direction direction = config.surface.getDirection();

        Direction opposite = direction.getOpposite();
        Set<BlockPos> set = new HashSet<>();

        int xStep = direction.getStepX();
        int zStep = direction.getStepZ();
        //assert xStep = 0 ^ zStep = 0
        for (int i = -hRadius; i <= hRadius; i++) {
            boolean boundCond = i == -hRadius || i == hRadius;

            for (int j = -vRadius; j <= vRadius; j++) {
                boolean boundCondInner = j == -vRadius || j == vRadius;
                boolean anyBound = boundCond || boundCondInner;
                boolean allBound = boundCond && boundCondInner;
                boolean xorBound = anyBound && !allBound;
                if (!allBound && (!xorBound || config.extraEdgeColumnChance != 0.0F && random.nextFloat() <= config.extraEdgeColumnChance)) {
                    mutablePos.setWithOffset(pos, i * xStep, j, j * zStep);
                    //adjust pos to surface non-air block
                    for (int k = 0;
                        level.isStateAtPosition(mutablePos, BlockBehaviour.BlockStateBase::isAir) && k < config.adjustRange;
                        k++
                    ) {
                        mutablePos.move(direction);
                    }
                    //adjust pos to surface air.
                    for (int i1 = 0;
                        level.isStateAtPosition(mutablePos, state1 -> !state1.isAir()) && i1 < config.adjustRange;
                        i1++
                    ) {
                        mutablePos.move(opposite);
                    }

                    mutablePosCpy.setWithOffset(mutablePos, direction);
                    BlockState blockstate = level.getBlockState(mutablePosCpy);
                    if (level.isEmptyBlock(mutablePos)
                        && blockstate.isFaceSturdy(level, mutablePosCpy, opposite)) {
                        int depth = config.depth.sample(random)
                            + (config.extraBottomBlockChance > 0.0F && random.nextFloat() < config.extraBottomBlockChance ? 1 : 0);
                        BlockPos blockpos = mutablePosCpy.immutable();
                        boolean flag5 = this.erodeWall(level, config, state, random, mutablePosCpy, depth, direction, upSpaces, downSpaces);
                        if (flag5) {
                            set.add(blockpos);
                        }
                    }
                }
            }
        }

        return set;
    }

    /**
     * Determines the list of available horizontal directions (NORTH, SOUTH, WEST, EAST)
     * where there are no blocks obstructing the adjacent position relative to the specified position.
     *
     * @param level The world generation level used to check for empty block spaces.
     * @param pos The position from which adjacent blocks are checked for availability.
     * @return A list of directions that do not have any blocks obstructing the adjacent space. The gotten direction is outerN of the block of pos.
     */
    private List<Direction> availableStartDirections(WorldGenLevel level, BlockPos pos){
    return Stream.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)
            .filter(d -> level.isEmptyBlock(pos.relative(d)))
            .toList();
    }

    //like moss, erode downward
    protected boolean erodeWall(
        WorldGenLevel level,
        WallVegetationPatchConfiguration config,
        Predicate<BlockState> replaceableblocks,
        RandomSource random,
        BlockPos.MutableBlockPos mutablePos,
        int maxDistance,
        Direction direction,
        Set<BlockPos> upSpaces,
        Set<BlockPos> downSpaces
    ) {
        for (int i = 0; i < maxDistance; i++) {
            BlockState blockstate = config.groundState.getState(random, mutablePos);
            BlockState blockstate1 = level.getBlockState(mutablePos);
            if (!blockstate.is(blockstate1.getBlock())) {
                if (!replaceableblocks.test(blockstate1)) {
                    return i != 0;
                }

                level.setBlock(mutablePos, blockstate, 2);

                if(level.isEmptyBlock(mutablePos.above())){
                    upSpaces.add(mutablePos.immutable());
                }

                if(level.isEmptyBlock(mutablePos.below())){
                    downSpaces.add(mutablePos.immutable());
                }

                mutablePos.move(direction);
            }
        }

        return true;
    }
}
