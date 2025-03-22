package ca.fxco.morecullingextra.mixin;

import ca.fxco.morecullingextra.api.ExtendedBlockModel;
import ca.fxco.morecullingextra.cullcondition.SimpleCullCondition;
import ca.fxco.morecullingextra.util.JsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BlockModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;
import java.util.List;

@Mixin(BlockModel.Deserializer.class)
public class Deserializer_cullFaceMixin implements ExtendedBlockModel {

    @Inject(
            method = "deserialize(Lcom/google/gson/JsonElement;" +
                    "Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)" +
                    "Lnet/minecraft/client/renderer/block/model/BlockModel;",
            at = @At("RETURN")
    )
    private void moreculling$onDeserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonContext,
                                           CallbackInfoReturnable<BlockModel> cir) {
        BlockModel unbakedModel = cir.getReturnValue();
        JsonObject jsonObj = jsonElement.getAsJsonObject();

        if (jsonObj.has("cull_conditions")) {
            JsonArray array = jsonObj.get("cull_conditions").getAsJsonArray();
            if (array.isEmpty()) {
                return;
            }

            if (array.size() == 1) {
                JsonObject jsonObject = array.get(0).getAsJsonObject();
                ((ExtendedBlockModel) unbakedModel).setCullCondition(new SimpleCullCondition(
                        array.get(0).getAsJsonObject().get("when").getAsString(),
                        JsonUtils.getOrDefault(jsonObject, "type", "OR"),
                        JsonUtils.getOrDefault(jsonObject, "directions", List.of()),
                        JsonUtils.getOrDefault(jsonObject, "cull", true),
                        JsonUtils.getOrDefault(jsonObject, "ignoreShape", false)
                ));
            }
        }
    }
}
