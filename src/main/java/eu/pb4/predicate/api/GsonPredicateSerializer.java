package eu.pb4.predicate.api;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import java.lang.reflect.Type;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;

public final class GsonPredicateSerializer implements JsonSerializer<MinecraftPredicate>, JsonDeserializer<MinecraftPredicate> {
    @Deprecated(forRemoval = true)
    public static final GsonPredicateSerializer INSTANCE = create(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY));
    private final HolderLookup.Provider lookup;

    public static GsonPredicateSerializer create(HolderLookup.Provider lookup) {
        return new GsonPredicateSerializer(lookup);
    }

    private GsonPredicateSerializer(HolderLookup.Provider lookup) {
        this.lookup = lookup;
    }

    @Override
    public MinecraftPredicate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            return PredicateRegistry.decode(this.lookup, jsonElement);
        } catch (Throwable t) {
            throw new JsonParseException(t);
        }
    }

    @Override
    public JsonElement serialize(MinecraftPredicate minecraftPredicate, Type type, JsonSerializationContext jsonSerializationContext) {
        return PredicateRegistry.CODEC.encode(minecraftPredicate, this.lookup.createSerializationContext(JsonOps.INSTANCE), new JsonObject()).result().get();
    }
}
