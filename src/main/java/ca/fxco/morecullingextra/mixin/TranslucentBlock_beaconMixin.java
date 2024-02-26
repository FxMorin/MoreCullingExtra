package ca.fxco.morecullingextra.mixin;

import ca.fxco.moreculling.api.block.MoreBlockCulling;
import ca.fxco.morecullingextra.MoreCullingExtra;
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.TranslucentBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(TranslucentBlock.class)
public class TranslucentBlock_beaconMixin implements MoreBlockCulling {

    @Override
    public boolean usesCustomShouldDrawFace(BlockState state) {
        return MoreCullingExtra.CONFIG.beaconCulling;
    }

    @Override
    public Optional<Boolean> customShouldDrawFace(BlockView view, BlockState thisState, BlockState sideState,
                                                  BlockPos thisPos, BlockPos sidePos, Direction side) {
        return sideState.getBlock() instanceof BeaconBlock ? Optional.of(false) : Optional.empty();
    }
}
