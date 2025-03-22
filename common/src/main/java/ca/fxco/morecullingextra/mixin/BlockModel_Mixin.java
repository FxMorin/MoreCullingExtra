package ca.fxco.morecullingextra.mixin;

import ca.fxco.morecullingextra.api.ExtendedBakedModel;
import ca.fxco.morecullingextra.api.ExtendedBlockModel;
import ca.fxco.morecullingextra.cullcondition.CullCondition;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockModel.class)
public class BlockModel_Mixin implements ExtendedBlockModel {

    private CullCondition condition = null;

    @Override
    public void setCullCondition(CullCondition condition) {
        this.condition = condition;
    }

    @Override
    public CullCondition getCullCondition() {
        return condition;
    }

    @Inject(
            method = "bake",
            at = @At(value = "TAIL")
    )
    private void passCondition(TextureSlots p_387258_, ModelBaker p_388168_, ModelState p_111453_, boolean p_111455_,
                               boolean p_387632_, ItemTransforms p_386577_, CallbackInfoReturnable<BakedModel> cir) {
        ((ExtendedBakedModel) cir.getReturnValue()).setCullCondition(condition);
    }
}
