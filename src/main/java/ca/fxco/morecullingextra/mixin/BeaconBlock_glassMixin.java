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

@Mixin(BeaconBlock.class)
public class BeaconBlock_glassMixin implements MoreBlockCulling {

    public boolean moreculling$usesCustomShouldDrawFace(BlockState state) {
        return MoreCullingExtra.CONFIG.beaconCulling;
    }

    public Optional<Boolean> moreculling$customShouldDrawFace(BlockView view, BlockState thisState,
            BlockState sideState, BlockPos thisPos, BlockPos sidePos, Direction side) {
        if (sideState.getBlock() instanceof TranslucentBlock || sideState.getBlock() instanceof BeaconBlock) {
            return Optional.of(false);
        }
        return Optional.empty();
    }
}
