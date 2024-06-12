package eu.pb4.predicate.api;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;

import java.lang.reflect.Type;

public final class GsonPredicateSerializer implements JsonSerializer<MinecraftPredicate>, JsonDeserializer<MinecraftPredicate> {
    @Deprecated(forRemoval = true)
    public static final GsonPredicateSerializer INSTANCE = create(DynamicRegistryManager.of(Registries.REGISTRIES));
    private final RegistryWrapper.WrapperLookup lookup;

    public static GsonPredicateSerializer create(RegistryWrapper.WrapperLookup lookup) {
        return new GsonPredicateSerializer(lookup);
    }

    private GsonPredicateSerializer(RegistryWrapper.WrapperLookup lookup) {
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
        return PredicateRegistry.CODEC.encode(minecraftPredicate, this.lookup.getOps(JsonOps.INSTANCE), new JsonObject()).result().get();
    }
}
