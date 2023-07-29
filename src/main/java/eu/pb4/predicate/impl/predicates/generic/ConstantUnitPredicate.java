package eu.pb4.predicate.impl.predicates.generic;

import com.mojang.serialization.MapCodec;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.MinecraftPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import net.minecraft.util.Identifier;

public final class ConstantUnitPredicate extends AbstractPredicate {
    private final PredicateResult<Object> value;

    public <T extends MinecraftPredicate> ConstantUnitPredicate(Object value) {
        super(new Identifier("unit"), MapCodec.unit(null));

        this.value = PredicateResult.ofNullable(value);
    }

    @Override
    public PredicateResult<?> test(PredicateContext context) {
        return value;
    }

}
