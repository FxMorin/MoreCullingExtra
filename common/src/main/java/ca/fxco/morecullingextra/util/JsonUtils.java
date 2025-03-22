package ca.fxco.morecullingextra.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class JsonUtils {

    public static String getOrDefault(JsonObject json, String name, String defaultValue) {
        if (json.has(name)) {
            return json.get(name).getAsString();
        }

        return defaultValue;
    }

    public static boolean getOrDefault(JsonObject json, String name, boolean defaultValue) {
        if (json.has(name)) {
            return json.get(name).getAsBoolean();
        }

        return defaultValue;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getOrDefault(JsonObject json, String name, List<T> defaultValue) {
        if (json.has(name)) {
            return (List<T>) json.get(name).getAsJsonArray().asList();
        }

        return defaultValue;
    }

}
