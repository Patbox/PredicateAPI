package eu.pb4.predicate.impl.predicates.generic;

import com.mojang.serialization.MapCodec;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.MinecraftPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.resources.Identifier;

public final class SimplePredicate extends AbstractPredicate {
    public static final MinecraftPredicate ALWAYS_TRUE = new SimplePredicate(Identifier.parse("always_true"), (Predicate<PredicateContext>) ctx -> true);
    public static final MinecraftPredicate ALWAYS_FALSE = new SimplePredicate(Identifier.parse("always_false"), (Predicate<PredicateContext>) ctx -> false);
    public static final MinecraftPredicate HAS_PLAYER = new SimplePredicate(Identifier.parse("has_player"), PredicateContext::hasPlayer);
    public static final MinecraftPredicate HAS_ENTITY = new SimplePredicate(Identifier.parse("has_entity"), PredicateContext::hasEntity);
    public static final MinecraftPredicate HAS_WORLD = new SimplePredicate(Identifier.parse("has_world"), PredicateContext::hasWorld);
    public static final MinecraftPredicate HAS_GAME_PROFILE = new SimplePredicate(Identifier.parse("has_game_profile"), PredicateContext::hasGameProfile);

    private final Function<PredicateContext, PredicateResult<?>> function;

    public SimplePredicate(Identifier identifier, Predicate<PredicateContext> function) {
        this(identifier, (x) -> PredicateResult.ofBoolean(function.test(x)), new MutableObject<>());
    }

    public SimplePredicate(Identifier identifier, Function<PredicateContext, PredicateResult<?>> function) {
        this(identifier, function, new MutableObject<>());
    }

    private SimplePredicate(Identifier identifier, Function<PredicateContext, PredicateResult<?>> function, MutableObject<SimplePredicate> self) {
        super(identifier, MapCodec.unit(self::getValue));
        this.function = function;
        self.setValue(this);
    }

    @Override
    public PredicateResult<?> test(PredicateContext context) {
        return function.apply(context);
    }

}
