package eu.pb4.predicate.impl.predicates;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import eu.pb4.predicate.api.MinecraftPredicate;
import eu.pb4.predicate.api.PredicateRegistry;
import net.minecraft.text.Text;

public final class GenericObject {
    public static final Codec<Object> CODEC = (Codec<Object>) (Object) Codec.either(PredicateRegistry.CODEC, Codec.either(Codec.STRING, Codec.DOUBLE));

    public static MinecraftPredicate toPredicate(Object valueA) {
        if (valueA instanceof Either<?,?> either) {
            if (either.left().isPresent()) {
                return toPredicate(either.left().get());
            } else {
                return toPredicate(either.right().get());
            }
        } else if (valueA instanceof MinecraftPredicate predicate) {
            return predicate;
        } else {
            return MinecraftPredicate.unit(valueA);
        }
    }


    public static double toNumber(Object value, boolean bool) {
        try {
            if (value instanceof Number d) {
                return d.doubleValue();
            } else if (value instanceof Text text) {
                return Double.parseDouble(text.getString());
            } else if (value instanceof String string) {
                return Double.parseDouble(string);
            }
        } catch (Throwable e) {

        }
        return bool ? 1 : 0;
    }
}
