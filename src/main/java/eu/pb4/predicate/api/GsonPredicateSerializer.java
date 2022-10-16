package eu.pb4.predicate.api;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;

import java.lang.reflect.Type;

public final class GsonPredicateSerializer implements JsonSerializer<MinecraftPredicate>, JsonDeserializer<MinecraftPredicate> {
    public static final GsonPredicateSerializer INSTANCE = new GsonPredicateSerializer();


    private GsonPredicateSerializer() {}

    @Override
    public MinecraftPredicate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            return PredicateRegistry.decode(jsonElement);
        } catch (Throwable t) {
            throw new JsonParseException(t);
        }
    }

    @Override
    public JsonElement serialize(MinecraftPredicate minecraftPredicate, Type type, JsonSerializationContext jsonSerializationContext) {
        return PredicateRegistry.CODEC.encode(minecraftPredicate, JsonOps.INSTANCE, new JsonObject()).result().get();
    }
}
