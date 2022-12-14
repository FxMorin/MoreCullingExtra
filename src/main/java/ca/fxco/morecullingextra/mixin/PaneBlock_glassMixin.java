package ca.fxco.morecullingextra.mixin;

import ca.fxco.morecullingextra.MoreCullingExtra;
import net.minecraft.block.*;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PaneBlock.class)
public class PaneBlock_glassMixin extends HorizontalConnectingBlock {

    public PaneBlock_glassMixin(float radius1, float radius2, float boundingHeight1,
                                float boundingHeight2, float collisionHeight, Settings settings) {
        super(radius1, radius2, boundingHeight1, boundingHeight2, collisionHeight, settings);
    }

    @Inject(
            method = "isSideInvisible",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/HorizontalConnectingBlock;isSideInvisible(" +
                            "Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;" +
                            "Lnet/minecraft/util/math/Direction;)Z"
            ),
            cancellable = true
    )
    private void cullAgainstGlass(BlockState state, BlockState stateFrom, Direction direction,
                                  CallbackInfoReturnable<Boolean> cir) {
        if (MoreCullingExtra.CONFIG.paneCulling) {
            if (stateFrom.getBlock() instanceof AbstractGlassBlock || stateFrom.getBlock() instanceof BeaconBlock) {
                if (!direction.getAxis().isHorizontal()) {
                    cir.setReturnValue(true);
                } else if (((Boolean) state.get((Property) FACING_PROPERTIES.get(direction))).booleanValue()) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
