package eu.pb4.predicate.impl.predicates.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.*;
import net.minecraft.util.Identifier;

import java.util.List;

public final class AnyPredicate extends AbstractPredicate {
    public static final Identifier ID = new Identifier("any");
    public static final MapCodec<AnyPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.list(PredicateRegistry.CODEC).fieldOf("values").forGetter(AnyPredicate::values)
    ).apply(instance, AnyPredicate::new));
    private final List<MinecraftPredicate> values;

    public AnyPredicate(List<MinecraftPredicate> valueA) {
        super(ID, CODEC);
        this.values = valueA;
    }

    public List<MinecraftPredicate> values() {
        return this.values;
    }


    @Override
    public PredicateResult<?> test(PredicateContext context) {
        for (var predicate : this.values) {
            var val = predicate.test(context);
            if (val.success()) {
                return val;
            }
        }

        return PredicateResult.ofFailure();
    }
}
