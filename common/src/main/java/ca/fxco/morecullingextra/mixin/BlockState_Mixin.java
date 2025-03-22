package ca.fxco.morecullingextra.mixin;

import ca.fxco.morecullingextra.api.ExtendedBakedModel;
import ca.fxco.morecullingextra.api.ExtendedBlockState;
import ca.fxco.morecullingextra.cullcondition.CullCondition;
import ca.fxco.morecullingextra.cullcondition.EmptyCondition;
import com.mojang.logging.LogUtils;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ca.fxco.moreculling.MoreCulling.blockRenderManager;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockState_Mixin implements ExtendedBlockState {

    @Shadow protected abstract BlockState asState();

    private CullCondition condition = EmptyCondition.DEFAULT;

    @Override
    public void setCullCondition(CullCondition condition) {
        this.condition = condition;
    }

    @Override
    public CullCondition getCullCondition() {
        return condition;
    }


    @Inject(
            method = "initCache",
            at = @At(
                    value = "TAIL"
            )
    )
    private void moreculling$customCullingShape(CallbackInfo ci) {
        if (blockRenderManager != null) {
            BakedModel model = blockRenderManager.getBlockModel(this.asState());
            if (model != null) {
                //LogUtils.getLogger().warn("1");
                CullCondition cond = ((ExtendedBakedModel) model).getCullCondition();
                if (cond != null) {
                    condition = cond;
                }
            }
        }
    }
}
