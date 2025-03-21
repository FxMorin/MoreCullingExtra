package ca.fxco.morecullingextra.mixin;

import ca.fxco.morecullingextra.MoreCullingExtra;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronBarsBlock.class)
public abstract class IronBarsBlock_glassMixin extends CrossCollisionBlock {

    public IronBarsBlock_glassMixin(float radius1, float radius2, float boundingHeight1,
                                    float boundingHeight2, float collisionHeight, Properties settings) {
        super(radius1, radius2, boundingHeight1, boundingHeight2, collisionHeight, settings);
    }

    @Inject(
            method = "skipRendering",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/CrossCollisionBlock;skipRendering(" +
                            "Lnet/minecraft/world/level/block/state/BlockState;" +
                            "Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z"
            ),
            cancellable = true
    )
    private void cullAgainstGlass(BlockState state, BlockState stateFrom, Direction direction,
                                  CallbackInfoReturnable<Boolean> cir) {
        if (MoreCullingExtra.CONFIG.paneCulling) {
            if (stateFrom.getBlock() instanceof HalfTransparentBlock || stateFrom.getBlock() instanceof BeaconBlock) {
                if (!direction.getAxis().isHorizontal()) {
                    cir.setReturnValue(true);
                } else if (state.getValue(PROPERTY_BY_DIRECTION.get(direction))) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
