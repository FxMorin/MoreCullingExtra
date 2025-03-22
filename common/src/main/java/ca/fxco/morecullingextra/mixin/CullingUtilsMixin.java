package ca.fxco.morecullingextra.mixin;

import ca.fxco.moreculling.utils.CullingUtils;
import ca.fxco.morecullingextra.api.ExtendedBlockState;
import ca.fxco.morecullingextra.cullcondition.CullCondition;
import ca.fxco.morecullingextra.cullcondition.SimpleCullCondition;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CullingUtils.class)
public class CullingUtilsMixin {
    @Inject(
            method = "shouldDrawSideCulling",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private static void test(BlockState thisState, BlockState sideState, BlockGetter world, BlockPos thisPos,
                             Direction side, BlockPos sidePos, CallbackInfoReturnable<Boolean> cir) {
        CullCondition condition = ((ExtendedBlockState) thisState).getCullCondition();
        if (condition.test(thisPos, sideState, side, world)) {
            cir.setReturnValue(condition.shouldCull(thisState, sideState, world, thisPos, side, sidePos));
        }
    }
}
