package ca.fxco.morecullingextra.cullcondition;

import ca.fxco.moreculling.api.blockstate.MoreStateCulling;
import ca.fxco.moreculling.api.blockstate.StateCullingShapeCache;
import com.google.gson.JsonPrimitive;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleCullCondition implements CullCondition {

    public final PropertyDispatch.QuadFunction<BlockPos, BlockState, Direction, BlockGetter, Boolean> blockTest;
    public final ConditionalCullCheck cullTest;

    public SimpleCullCondition(String blockId, String type, List<JsonPrimitive> directionsString,
                               boolean cull, boolean ignoreShape) {
        Block block = BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(blockId));
        if (directionsString.isEmpty()) {
            if (type.equals("NONE")) {
                blockTest = (thisPos, sideState,
                             direction, blockGetter) -> !sideState.is(block);
            } else {
                blockTest = (thisPos, sideState,
                             direction, blockGetter) -> sideState.is(block);
            }
        } else {
            List<Direction> directions = new ArrayList<>();
            for (JsonPrimitive direction : directionsString) {
                directions.add(Direction.valueOf(direction.getAsString().toUpperCase()));
            }

            blockTest = switch (type) {
                case "OR" -> (thisPos, sideState, direction, blockGetter) -> {
                    for (Direction dir : directions) {
                        if (blockGetter.getBlockState(thisPos.relative(dir)).is(block)) {
                            return true;
                        }
                    }
                    return false;
                };
                case "AND" -> (thisPos, sideState, direction, blockGetter) -> {
                    for (Direction dir : directions) {
                        if (!blockGetter.getBlockState(thisPos.relative(dir)).is(block)) {
                            return false;
                        }
                    }

                    return true;
                };
                default -> (thisPos, sideState, direction, blockGetter) -> {
                    for (Direction dir : directions) {
                        if (blockGetter.getBlockState(thisPos.relative(dir)).is(block)) {
                            return false;
                        }
                    }

                    return true;
                };
            };
        }
        if (ignoreShape) {
            cullTest = ((thisState, sideState, world,
                         thisPos, side, sidePos) -> !cull);
        } else if (cull) {
            cullTest = (SimpleCullCondition::shouldDrawSideCulling);
        } else {
            cullTest = (thisState, sideState, world,
                        thisPos, side, sidePos) ->
                    true;
        }
    }

    @Override
    public boolean test(BlockPos thisPos, BlockState sideState, Direction direction, BlockGetter getter) {
        return blockTest.apply(thisPos, sideState, direction, getter);
    }

    @Override
    public boolean shouldCull(BlockState thisState, BlockState sideState, BlockGetter world, BlockPos thisPos, Direction side, BlockPos sidePos) {
        return cullTest.test(thisState, sideState, world, thisPos, side, sidePos);
    }


    public static boolean shouldDrawSideCulling(BlockState thisState, BlockState sideState,
                                                BlockGetter world, BlockPos thisPos, Direction side,
                                                BlockPos sidePos) {
        if (thisState.skipRendering(sideState, side)) {
            return false;
        }
        if (((MoreStateCulling) thisState).moreculling$usesCustomShouldDrawFace()) {
            Optional<Boolean> shouldDrawFace = ((MoreStateCulling) thisState).moreculling$customShouldDrawFace(
                    world, sideState, thisPos, sidePos, side
            );
            if (shouldDrawFace.isPresent()) {
                return shouldDrawFace.get();
            }
        }

        //TODO make it public
        return shouldDrawFace(world, thisState, sideState, thisPos, sidePos, side);
    }


    private static boolean shouldDrawFace(BlockGetter world, BlockState thisState, BlockState sideState,
                                          BlockPos thisPos, BlockPos sidePos, Direction side) {
        if (((MoreStateCulling) sideState).moreculling$cantCullAgainst(side)) {
            return true; // Check if we can cull against this block
        }
        Direction opposite = side.getOpposite();
        VoxelShape sideShape = ((StateCullingShapeCache) sideState).moreculling$getFaceCullingShape(opposite);

        if (sideShape == Shapes.block()) {
            return false;
        }

        VoxelShape thisShape = ((StateCullingShapeCache) thisState).moreculling$getFaceCullingShape(side);
        if (thisShape == Shapes.empty() || sideShape == Shapes.empty()) { // It this shape is empty
            return true; // Face should be drawn if the side face is empty
        }

        Block.ShapePairKey shapePairKey = new Block.ShapePairKey(
                thisShape,
                sideShape
        );
        Object2ByteLinkedOpenHashMap<Block.ShapePairKey> object2ByteLinkedOpenHashMap = Block.OCCLUSION_CACHE.get();
        byte b = object2ByteLinkedOpenHashMap.getAndMoveToFirst(shapePairKey);
        if (b != 127) {
            return b != 0;
        }
        boolean bl = Shapes.joinIsNotEmpty(thisShape, sideShape, BooleanOp.ONLY_FIRST);
        if (object2ByteLinkedOpenHashMap.size() == 256) {
            object2ByteLinkedOpenHashMap.removeLastByte();
        }
        object2ByteLinkedOpenHashMap.putAndMoveToFirst(shapePairKey, (byte) (bl ? 1 : 0));
        return bl;
    }
}
