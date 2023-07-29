package eu.pb4.predicate.impl.predicates.generic;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.MinecraftPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import eu.pb4.predicate.impl.predicates.GenericObject;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class StringPredicate extends AbstractPredicate {
    private final Object value;
    private final Object argument;
    private final MinecraftPredicate valuePredicate;
    private final MinecraftPredicate argumentPredicate;

    public <T extends MinecraftPredicate> StringPredicate(Object value, Object argument, Identifier identifier, MapCodec<T> codec) {
        super(identifier, codec);
        this.value = value;
        this.argument = argument;

        this.valuePredicate = GenericObject.toPredicate(value);
        this.argumentPredicate = GenericObject.toPredicate(argument);
    }

    public static <T extends StringPredicate> MapCodec<T> codec(BiFunction<Object, Object, T> creator) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                GenericObject.CODEC.fieldOf("input").forGetter(StringPredicate::input),
                GenericObject.CODEC.fieldOf("argument").forGetter(StringPredicate::argument)
        ).apply(instance, creator));
    }

    public final Object input() {
        return this.value;
    }

    public final Object argument() {
        return this.argument;
    }

    @Override
    public PredicateResult<?> test(PredicateContext context) {
        String a = GenericObject.toString(this.valuePredicate.test(context));
        String b = GenericObject.toString(this.argumentPredicate.test(context));

        return this.testString(a, b);
    }

    protected abstract PredicateResult<?> testString(String a, String b);

    public static final class StartsWith extends StringPredicate {
        public static final Identifier ID = new Identifier("starts_with");
        public static final MapCodec<StartsWith> CODEC = StringPredicate.codec(StartsWith::new);

        public <T extends MinecraftPredicate> StartsWith(Object value, Object argument) {
            super(value, argument, ID, CODEC);
        }

        @Override
        protected PredicateResult<?> testString(String a, String b) {
            return PredicateResult.ofBoolean(a.startsWith(b));
        }
    }

    public static final class EndsWith extends StringPredicate {
        public static final Identifier ID = new Identifier("ends_with");
        public static final MapCodec<EndsWith> CODEC = StringPredicate.codec(EndsWith::new);

        public <T extends MinecraftPredicate> EndsWith(Object value, Object argument) {
            super(value, argument, ID, CODEC);
        }

        @Override
        protected PredicateResult<?> testString(String a, String b) {
            return PredicateResult.ofBoolean(a.endsWith(b));
        }
    }

    public static final class Join extends StringPredicate {
        public static final Identifier ID = new Identifier("join_string");
        public static final MapCodec<Join> CODEC = StringPredicate.codec(Join::new);

        public <T extends MinecraftPredicate> Join(Object value, Object argument) {
            super(value, argument, ID, CODEC);
        }

        @Override
        protected PredicateResult<?> testString(String a, String b) {
            return PredicateResult.ofSuccess(a + b);
        }
    }
}
