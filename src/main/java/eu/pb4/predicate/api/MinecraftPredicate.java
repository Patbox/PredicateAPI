package eu.pb4.predicate.api;

import com.mojang.serialization.MapCodec;
import eu.pb4.predicate.impl.predicates.generic.ConstantUnitPredicate;
import eu.pb4.predicate.impl.predicates.generic.SimplePredicate;
import java.util.function.Function;
import net.minecraft.resources.Identifier;

public interface MinecraftPredicate {
    static MinecraftPredicate unit(Object valueA) {
        return new ConstantUnitPredicate(valueA);
    }

    static MinecraftPredicate simple(Identifier identifier, Function<PredicateContext, PredicateResult<?>> resultFunction) {
        return new SimplePredicate(identifier, resultFunction);
    }

    static MapCodec<MinecraftPredicate> simpleCodec(Identifier identifier, Function<PredicateContext, PredicateResult<?>> resultFunction) {
        return simple(identifier, resultFunction).codec();
    }

    PredicateResult<?> test(PredicateContext context);
    MapCodec<MinecraftPredicate> codec();
    Identifier identifier();
}
