package ca.fxco.morecullingextra.mixin;

import ca.fxco.moreculling.api.block.MoreBlockCulling;
import ca.fxco.morecullingextra.MoreCullingExtra;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BeaconBlock;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(BeaconBlock.class)
public class BeaconBlock_glassMixin implements MoreBlockCulling {

    public boolean moreculling$usesCustomShouldDrawFace(net.minecraft.world.level.block.state.BlockState state) {
        return MoreCullingExtra.CONFIG.beaconCulling;
    }

    public Optional<Boolean> moreculling$customShouldDrawFace(BlockGetter getter, BlockState thisState,
                                                              BlockState sideState, BlockPos thisPos,
                                                              BlockPos sidePos, Direction side) {
        if (sideState.getBlock() instanceof HalfTransparentBlock || sideState.getBlock() instanceof BeaconBlock) {
            return Optional.of(false);
        }
        return Optional.empty();
    }
}
