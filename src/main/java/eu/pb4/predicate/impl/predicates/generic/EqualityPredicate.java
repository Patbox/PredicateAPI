package eu.pb4.predicate.impl.predicates.generic;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.*;
import eu.pb4.predicate.impl.predicates.GenericObject;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

public final class EqualityPredicate extends AbstractPredicate {

    public static final Identifier ID = Identifier.of("equal");
    public static final MapCodec<EqualityPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            GenericObject.CODEC.fieldOf("value_1").forGetter(EqualityPredicate::valueA),
            GenericObject.CODEC.fieldOf("value_2").forGetter(EqualityPredicate::valueB)
    ).apply(instance, EqualityPredicate::new));
    private final Object valueA;
    private final Object valueB;
    private final MinecraftPredicate predicate1;
    private final MinecraftPredicate predicate2;


    public EqualityPredicate(Object valueA, Object valueB) {
        super(ID, CODEC);
        this.valueA = valueA;
        this.valueB = valueB;

        this.predicate1 = GenericObject.toPredicate(valueA);
        this.predicate2 = GenericObject.toPredicate(valueB);
    }

    public Object valueA() {
        return this.valueA;
    }

    public Object valueB() {
        return this.valueB;
    }

    @Override
    public PredicateResult<?> test(PredicateContext context) {
        var val1 = this.predicate1.test(context);
        var val2 = this.predicate2.test(context);

        if (val1.value() instanceof Text text && val2.value() instanceof String string) {
            return PredicateResult.ofBoolean(text.getString().equals(string));
        } else if (val2.value() instanceof Text text && val1.value() instanceof String string) {
            return PredicateResult.ofBoolean(text.getString().equals(string));
        } else {
            return PredicateResult.ofBoolean(val1.success() == val2.success() && Objects.equals(val1.value(), val2.value()));
        }
    }
}
