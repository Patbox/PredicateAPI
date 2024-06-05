package eu.pb4.predicate.impl.predicates.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.*;
import net.minecraft.util.Identifier;

import java.util.List;

public final class AllPredicate extends AbstractPredicate {
    public static final Identifier ID = Identifier.of("all");
    public static final MapCodec<AllPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.list(PredicateRegistry.CODEC).fieldOf("values").forGetter(AllPredicate::values)
    ).apply(instance, AllPredicate::new));
    private final List<MinecraftPredicate> values;

    public AllPredicate(List<MinecraftPredicate> valueA) {
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
            if (!val.success()) {
                return PredicateResult.ofFailure();
            }
        }

        return PredicateResult.ofSuccess();
    }
}
