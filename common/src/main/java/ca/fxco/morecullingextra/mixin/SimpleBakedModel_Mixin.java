package ca.fxco.morecullingextra.mixin;

import ca.fxco.morecullingextra.api.ExtendedBakedModel;
import ca.fxco.morecullingextra.cullcondition.CullCondition;
import com.mojang.logging.LogUtils;
import net.minecraft.client.resources.model.SimpleBakedModel;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.List;

@Mixin(SimpleBakedModel.class)
public class SimpleBakedModel_Mixin implements ExtendedBakedModel {

    private CullCondition mce$$condition;

    @Override
    public void setCullCondition(CullCondition condition) {
        this.mce$$condition = condition;
    }

    @Override
    public CullCondition getCullCondition() {
        return mce$$condition;
    }
}
