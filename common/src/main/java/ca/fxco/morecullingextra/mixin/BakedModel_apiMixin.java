package ca.fxco.morecullingextra.mixin;

import ca.fxco.morecullingextra.api.ExtendedBakedModel;
import net.minecraft.client.resources.model.BakedModel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BakedModel.class)
public interface BakedModel_apiMixin extends ExtendedBakedModel {
}
