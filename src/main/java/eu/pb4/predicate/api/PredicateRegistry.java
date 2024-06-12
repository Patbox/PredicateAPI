package eu.pb4.predicate.api;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import eu.pb4.predicate.impl.BaseCodec;
import eu.pb4.predicate.impl.PredicatesInit;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class PredicateRegistry {
    private static final Map<Identifier, MapCodec<MinecraftPredicate>> CODECS = new HashMap<>();
    private static final Map<MapCodec<MinecraftPredicate>, Identifier> CODEC_IDS = new HashMap<>();
    private static final RegistryWrapper.WrapperLookup FALLBACK_LOOKUP = DynamicRegistryManager.of(Registries.REGISTRIES);
    public static final MapCodec<MinecraftPredicate> MAP_CODEC = new BaseCodec();
    public static final Codec<MinecraftPredicate> CODEC = new MapCodec.MapCodecCodec<>(MAP_CODEC);

    private PredicateRegistry() {}

    public static MinecraftPredicate decode(RegistryWrapper.WrapperLookup lookup, JsonElement object) {
        return decode(lookup.getOps(JsonOps.INSTANCE), object);
    }

    public static <T> MinecraftPredicate decode(RegistryOps<T> ops, T object) {
        var data = CODEC.decode(ops, object);
        return data.getOrThrow(IllegalArgumentException::new).getFirst();
    }

    @Nullable
    public static MapCodec<MinecraftPredicate> getCodec(Identifier identifier) {
        return CODECS.get(identifier);
    }

    @Nullable
    public static Identifier getIdentifier(MapCodec<MinecraftPredicate> codec) {
        return CODEC_IDS.get(codec);
    }

    public static <T extends MinecraftPredicate> void register(Identifier identifier, MapCodec<T> predicateCodec) {
        CODECS.put(identifier, (MapCodec<MinecraftPredicate>) predicateCodec);
        CODEC_IDS.put((MapCodec<MinecraftPredicate>) predicateCodec, identifier);
    }

    @Deprecated(forRemoval = true)
    public static MinecraftPredicate decode(JsonElement object) {
        return decode(JsonOps.INSTANCE, object);
    }
    @Deprecated(forRemoval = true)
    public static <T> MinecraftPredicate decode(DynamicOps<T> ops, T object) {
        if (ops instanceof RegistryOps<T> registryOps) {
            return decode(registryOps, object);
        }

        return decode(FALLBACK_LOOKUP.getOps(ops), object);
    }

    static {
        PredicatesInit.initialize();
    }
}
