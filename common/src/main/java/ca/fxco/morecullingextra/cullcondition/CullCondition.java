package ca.fxco.morecullingextra.cullcondition;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public interface CullCondition {
    default boolean test(BlockPos thisPos, BlockState sideState, Direction direction, BlockGetter getter) {
        return false;
    }

    default boolean shouldCull(BlockState thisState, BlockState sideState, BlockGetter world,
                               BlockPos thisPos, Direction side, BlockPos sidePos) {
        return false;
    }

    @FunctionalInterface
    interface ConditionalCullCheck {
        boolean test(BlockState thisState, BlockState sideState, BlockGetter world,
                     BlockPos thisPos, Direction side, BlockPos sidePos);
    }

}
