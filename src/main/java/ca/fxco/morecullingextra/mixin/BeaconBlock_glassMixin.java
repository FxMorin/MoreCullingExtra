package ca.fxco.morecullingextra.mixin;

import ca.fxco.moreculling.api.block.MoreBlockCulling;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(BeaconBlock.class)
public class BeaconBlock_glassMixin implements MoreBlockCulling {

    @Override
    public boolean usesCustomShouldDrawFace(BlockState state) {
        return true;
    }

    @Override
    public Optional<Boolean> customShouldDrawFace(BlockView view, BlockState thisState, BlockState sideState,
                                                  BlockPos thisPos, BlockPos sidePos, Direction side) {
        if (sideState.getBlock() instanceof AbstractGlassBlock || sideState.getBlock() instanceof BeaconBlock) {
            return Optional.of(false);
        }
        return Optional.empty();
    }
}
