package eu.pb4.predicate.api;

import com.mojang.serialization.MapCodec;
import eu.pb4.predicate.impl.predicates.generic.UnitPredicate;
import net.minecraft.util.Identifier;

public interface MinecraftPredicate {
    static MinecraftPredicate unit(Object valueA) {
        return new UnitPredicate(valueA);
    }

    PredicateResult<?> test(PredicateContext context);
    MapCodec<MinecraftPredicate> codec();
    Identifier identifier();
}
