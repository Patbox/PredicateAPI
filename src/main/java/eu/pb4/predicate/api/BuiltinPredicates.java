package eu.pb4.predicate.api;

import eu.pb4.predicate.impl.predicates.compat.CompatStatus;
import eu.pb4.predicate.impl.predicates.compat.FabricPermissionPredicate;
import eu.pb4.predicate.impl.predicates.compat.PlaceholderPredicate;
import eu.pb4.predicate.impl.predicates.generic.*;
import eu.pb4.predicate.impl.predicates.player.OperatorPredicate;
import eu.pb4.predicate.impl.predicates.player.StatisticPredicate;
import eu.pb4.predicate.impl.predicates.player.EntityPredicatePredicate;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.stats.StatType;

public final class BuiltinPredicates {
    private BuiltinPredicates() {}

    public static MinecraftPredicate alwaysTrue() {
        return SimplePredicate.ALWAYS_TRUE;
    }

    public static MinecraftPredicate alwaysFalse() {
        return SimplePredicate.ALWAYS_FALSE;
    }

    public static MinecraftPredicate all(MinecraftPredicate... predicates) {
        return new AllPredicate(List.of(predicates));
    }

    public static MinecraftPredicate all(Collection<MinecraftPredicate> predicates) {
        return new AllPredicate(List.copyOf(predicates));
    }

    public static MinecraftPredicate any(MinecraftPredicate... predicates) {
        return new AnyPredicate(List.of(predicates));
    }

    public static MinecraftPredicate any(Collection<MinecraftPredicate> predicates) {
        return new AnyPredicate(List.copyOf(predicates));
    }

    public static MinecraftPredicate negate(MinecraftPredicate predicate) {
        return new NegatePredicate(predicate);
    }

    public static MinecraftPredicate equal(Object object, Object object2) {
        return new EqualityPredicate(object, object2);
    }

    public static MinecraftPredicate lessThan(Object object, Object object2) {
        return new NumberPredicate.LessThan(object, object2);
    }

    public static MinecraftPredicate lessOrEqual(Object object, Object object2) {
        return new NumberPredicate.LessEqual(object, object2);
    }

    public static MinecraftPredicate moreThan(Object object, Object object2) {
        return new NumberPredicate.MoreThan(object, object2);
    }

    public static MinecraftPredicate moreOrEqual(Object object, Object object2) {
        return new NumberPredicate.MoreEqual(object, object2);
    }

    public static MinecraftPredicate startsWith(Object input, Object argument) {
        return new StringPredicate.StartsWith(input, argument);
    }

    public static MinecraftPredicate endsWith(Object input, Object argument) {
        return new StringPredicate.EndsWith(input, argument);
    }

    public static MinecraftPredicate operatorLevel(PermissionLevel level) {
        return new OperatorPredicate(level);
    }


    public static <T> MinecraftPredicate statistic(StatType<T> type, T key) {
        return new StatisticPredicate(type, type.getRegistry().getKey(key));
    }

    public static MinecraftPredicate hasWorld() {
        return SimplePredicate.HAS_WORLD;
    }

    public static MinecraftPredicate hasPlayer() {
        return SimplePredicate.HAS_PLAYER;
    }

    public static MinecraftPredicate hasGameProfile() {
        return SimplePredicate.HAS_GAME_PROFILE;
    }

    public static MinecraftPredicate hasEntity() {
        return SimplePredicate.HAS_ENTITY;
    }

    public static <T> MinecraftPredicate vanillaEntityPredicate(EntityPredicate predicate) {
        return new EntityPredicatePredicate(predicate);
    }

    @Nullable
    public static MinecraftPredicate modPlaceholderApi(String placeholder) {
        return CompatStatus.PLACEHOLDER_API ? new PlaceholderPredicate(placeholder) : null;
    }

    public static MinecraftPredicate fabricPermission(Identifier permission, PermissionLevel alternativeOperatorLevel) {
        return new FabricPermissionPredicate(permission, Optional.ofNullable(alternativeOperatorLevel));
    }

    @Nullable
    public static MinecraftPredicate fabricPermission(Identifier permission) {
        return new FabricPermissionPredicate(permission, Optional.empty());
    }

    static {
        PredicateRegistry.CODEC.fieldOf("");
    }
}
