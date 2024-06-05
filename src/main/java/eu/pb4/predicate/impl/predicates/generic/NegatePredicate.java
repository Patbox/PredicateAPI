package eu.pb4.predicate.impl.predicates.generic;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.*;
import net.minecraft.util.Identifier;

public final class NegatePredicate extends AbstractPredicate {
    public static final Identifier ID = Identifier.of("negate");
    public static final MapCodec<NegatePredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            PredicateRegistry.CODEC.fieldOf("value").forGetter(NegatePredicate::value)
    ).apply(instance, NegatePredicate::new));
    private final MinecraftPredicate value;

    public NegatePredicate(MinecraftPredicate valueA) {
        super(ID, CODEC);
        this.value = valueA;
    }

    public MinecraftPredicate value() {
        return this.value;
    }


    @Override
    public PredicateResult<?> test(PredicateContext context) {
        var value = this.value.test(context);
        return PredicateResult.ofBoolean(!value.success());
    }
}
