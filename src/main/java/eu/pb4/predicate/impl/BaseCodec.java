package eu.pb4.predicate.impl;

import com.mojang.serialization.*;
import eu.pb4.predicate.api.MinecraftPredicate;
import eu.pb4.predicate.api.PredicateRegistry;
import java.util.stream.Stream;
import net.minecraft.resources.Identifier;

public class BaseCodec extends MapCodec<MinecraftPredicate> {
    @Override
    public <T> Stream<T> keys(DynamicOps<T> ops) {
        return Stream.of(ops.createString("type"), ops.createString("config"));
    }

    @Override
    public <T> DataResult<MinecraftPredicate> decode(DynamicOps<T> ops, MapLike<T> input) {
        var value = ops.getStringValue(input.get("type"));
        return value.flatMap(type -> {
            var id = Identifier.tryParse(type);

            var codec = PredicateRegistry.getCodec(id);

            if (codec != null) {
                return codec.decode(ops, input);
            } else {
                return DataResult.error(() -> "Invalid predicate type \"" + type +"\"!");
            }
        });
    }

    @Override
    public <T> RecordBuilder<T> encode(MinecraftPredicate input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
        var type = input.identifier().getNamespace().equals(Identifier.DEFAULT_NAMESPACE) ? input.identifier().getPath() : input.identifier().toString();

        return input.codec().encode(input, ops, prefix.add("type", ops.createString(type)));
    }
}
