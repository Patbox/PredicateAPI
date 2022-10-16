package eu.pb4.predicate.impl.predicates.player;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import net.minecraft.util.Identifier;

public final class OperatorPredicate extends AbstractPredicate {
    public static final Identifier ID = new Identifier("operator");
    public static final MapCodec<OperatorPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("operator").forGetter(OperatorPredicate::operator)
    ).apply(instance, OperatorPredicate::new));

    private final int operator;

    public OperatorPredicate(int operator) {
        super(ID, CODEC);
        this.operator = operator;
    }

    private int operator() {
        return this.operator;
    }

    @Override
    public PredicateResult<?> test(PredicateContext context) {
        return PredicateResult.ofBoolean(context.source().hasPermissionLevel(this.operator));
    }
}
