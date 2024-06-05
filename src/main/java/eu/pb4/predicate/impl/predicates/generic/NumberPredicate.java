package eu.pb4.predicate.impl.predicates.generic;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.MinecraftPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import eu.pb4.predicate.impl.predicates.GenericObject;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;

public abstract class NumberPredicate extends AbstractPredicate {
    public static final <T extends NumberPredicate> MapCodec<T> codec(BiFunction<Object, Object, T> creator) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                GenericObject.CODEC.fieldOf("value_1").forGetter(NumberPredicate::valueA),
                GenericObject.CODEC.fieldOf("value_2").forGetter(NumberPredicate::valueB)
        ).apply(instance, creator));
    }

    private final Object valueA;
    private final Object valueB;
    private final MinecraftPredicate predicate1;
    private final MinecraftPredicate predicate2;


    public <T extends MinecraftPredicate> NumberPredicate(Identifier identifier, MapCodec<T> codec, Object valueA, Object valueB) {
        super(identifier, codec);
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

        return this.check(GenericObject.toNumber(val1.value(), val1.success()), GenericObject.toNumber(val2.value(), val2.success()))
                ? new PredicateResult<>(true, val1.value()) : new PredicateResult<>(false, val2.value());
    }

    protected abstract boolean check(double a, double b);

    public static final class LessThan extends NumberPredicate {
        public static final Identifier ID = Identifier.of("less_than");
        public static final MapCodec<LessThan> CODEC = NumberPredicate.codec(LessThan::new);

        public LessThan(Object valueA, Object valueB) {
            super(ID, CODEC, valueA, valueB);
        }

        @Override
        protected boolean check(double a, double b) {
            return a < b;
        }
    }

    public static final class LessEqual extends NumberPredicate {
        public static final Identifier ID = Identifier.of("less_or_equal");
        public static final MapCodec<LessEqual> CODEC = NumberPredicate.codec(LessEqual::new);

        public LessEqual(Object valueA, Object valueB) {
            super(ID, CODEC, valueA, valueB);
        }

        @Override
        protected boolean check(double a, double b) {
            return a <= b;
        }
    }

    public static final class MoreThan extends NumberPredicate {
        public static final Identifier ID = Identifier.of("more_than");
        public static final MapCodec<MoreThan> CODEC = NumberPredicate.codec(MoreThan::new);

        public MoreThan(Object valueA, Object valueB) {
            super(ID, CODEC, valueA, valueB);
        }

        @Override
        protected boolean check(double a, double b) {
            return a > b;
        }
    }

    public static final class MoreEqual extends NumberPredicate {
        public static final Identifier ID = Identifier.of("more_or_equal");
        public static final MapCodec<MoreEqual> CODEC = NumberPredicate.codec(MoreEqual::new);

        public MoreEqual(Object valueA, Object valueB) {
            super(ID, CODEC, valueA, valueB);
        }

        @Override
        protected boolean check(double a, double b) {
            return a >= b;
        }
    }
}
