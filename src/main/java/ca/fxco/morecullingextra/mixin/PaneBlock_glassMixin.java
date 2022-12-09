package ca.fxco.morecullingextra.mixin;

import net.minecraft.block.*;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(PaneBlock.class)
public class PaneBlock_glassMixin {

    private static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES
            .entrySet().stream().filter((entry) -> entry.getKey().getAxis().isHorizontal()).collect(Util.toMap());

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
        if (stateFrom.getBlock() instanceof AbstractGlassBlock || stateFrom.getBlock() instanceof BeaconBlock) {
            if (!direction.getAxis().isHorizontal()) {
                cir.setReturnValue(true);
            } else if (((Boolean)state.get((Property)FACING_PROPERTIES.get(direction))).booleanValue()) {
                cir.setReturnValue(true);
            }
        }
    }
}
