package eu.pb4.predicate.impl;

import eu.pb4.predicate.impl.predicates.compat.CompatStatus;
import eu.pb4.predicate.impl.predicates.compat.PermissionOptionPredicate;
import eu.pb4.predicate.impl.predicates.compat.PermissionPredicate;
import eu.pb4.predicate.impl.predicates.compat.PlaceholderPredicate;
import eu.pb4.predicate.impl.predicates.generic.*;
import eu.pb4.predicate.impl.predicates.player.OperatorPredicate;
import eu.pb4.predicate.impl.predicates.player.StatisticPredicate;
import eu.pb4.predicate.impl.predicates.player.EntityPredicatePredicate;

import static eu.pb4.predicate.api.PredicateRegistry.register;

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

        register(OperatorPredicate.ID, OperatorPredicate.CODEC);
        register(StatisticPredicate.ID, StatisticPredicate.CODEC);
        register(EntityPredicatePredicate.ID, EntityPredicatePredicate.CODEC);


        if (CompatStatus.PLACEHOLDER_API) {
            register(PlaceholderPredicate.ID, PlaceholderPredicate.CODEC);
        }

        if (CompatStatus.LUCKO_PERMISSION_API) {
            register(PermissionPredicate.ID, PermissionPredicate.CODEC);
            register(PermissionOptionPredicate.ID, PermissionOptionPredicate.CODEC);
        }
    }
}
