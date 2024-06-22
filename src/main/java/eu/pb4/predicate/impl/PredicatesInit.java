package eu.pb4.predicate.impl;

import com.mojang.serialization.MapCodec;
import eu.pb4.predicate.api.MinecraftPredicate;
import eu.pb4.predicate.api.PredicateRegistry;
import eu.pb4.predicate.impl.predicates.compat.CompatStatus;
import eu.pb4.predicate.impl.predicates.compat.PermissionOptionPredicate;
import eu.pb4.predicate.impl.predicates.compat.PermissionPredicate;
import eu.pb4.predicate.impl.predicates.compat.PlaceholderPredicate;
import eu.pb4.predicate.impl.predicates.generic.*;
import eu.pb4.predicate.impl.predicates.player.OperatorPredicate;
import eu.pb4.predicate.impl.predicates.player.StatisticPredicate;
import eu.pb4.predicate.impl.predicates.player.EntityPredicatePredicate;
import net.minecraft.util.Identifier;


public class PredicatesInit {
    public static void initialize() {
        register(EqualityPredicate.ID, EqualityPredicate.CODEC);
        register(NegatePredicate.ID, NegatePredicate.CODEC);
        register(AnyPredicate.ID, AnyPredicate.CODEC);
        register(AllPredicate.ID, AllPredicate.CODEC);
        register(NumberPredicate.LessThan.ID, NumberPredicate.LessThan.CODEC);
        register(NumberPredicate.LessEqual.ID, NumberPredicate.LessEqual.CODEC);
        register(NumberPredicate.MoreThan.ID, NumberPredicate.MoreThan.CODEC);
        register(NumberPredicate.MoreEqual.ID, NumberPredicate.MoreEqual.CODEC);
        register(StringPredicate.StartsWith.ID, StringPredicate.StartsWith.CODEC);
        register(StringPredicate.EndsWith.ID, StringPredicate.EndsWith.CODEC);
        register(StringPredicate.Join.ID, StringPredicate.Join.CODEC);

        register(OperatorPredicate.ID, OperatorPredicate.CODEC);
        register(StatisticPredicate.ID, StatisticPredicate.CODEC);
        register(EntityPredicatePredicate.ID, EntityPredicatePredicate.CODEC);

        register(SimplePredicate.HAS_ENTITY);
        register(SimplePredicate.HAS_PLAYER);
        register(SimplePredicate.HAS_WORLD);
        register(SimplePredicate.HAS_GAME_PROFILE);
        register(SimplePredicate.ALWAYS_TRUE);
        register(SimplePredicate.ALWAYS_FALSE);

        if (CompatStatus.PLACEHOLDER_API) {
            register(PlaceholderPredicate.ID, PlaceholderPredicate.CODEC);
        }

        if (CompatStatus.LUCKO_PERMISSION_API) {
            register(PermissionPredicate.ID, PermissionPredicate.CODEC);
            register(PermissionOptionPredicate.ID, PermissionOptionPredicate.CODEC);
        }
    }

    public static void register(MinecraftPredicate predicate) {
        register(predicate.identifier(), predicate.codec());
    }

    public static <T extends MinecraftPredicate> void register(Identifier identifier, MapCodec<T> codec) {
        PredicateRegistry.register(identifier, codec);
    }
}
