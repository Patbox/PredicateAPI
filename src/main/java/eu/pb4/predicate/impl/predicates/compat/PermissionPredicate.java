package eu.pb4.predicate.impl.predicates.compat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.PermissionLevel;
import java.util.Optional;

public final class PermissionPredicate extends AbstractPredicate {
    public static final Identifier ID = Identifier.parse("permission");
    public static final MapCodec<PermissionPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("permission").forGetter(PermissionPredicate::permission),
            Codec.withAlternative(PermissionLevel.CODEC, PermissionLevel.INT_CODEC).optionalFieldOf("operator").forGetter(PermissionPredicate::permissionLevel)
    ).apply(instance, PermissionPredicate::new));

    private final String permission;
    private final Optional<PermissionLevel> permissionLevel;

    @Deprecated
    public PermissionPredicate(String permission, int operator) {
        this(permission, operator < 0 ? Optional.empty() : Optional.of(PermissionLevel.byId(operator)));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public PermissionPredicate(String permission, Optional<PermissionLevel> permissionLevel) {
        super(ID, CODEC);
        this.permission = permission;
        this.permissionLevel = permissionLevel;
    }

    public String permission() {
        return this.permission;
    }

    private Optional<PermissionLevel> permissionLevel() {
        return this.permissionLevel;
    }

    @Override
    public PredicateResult<?> test(PredicateContext context) {
        return PredicateResult.ofBoolean(permissionLevel.isEmpty() ? Permissions.check(context.source(), this.permission) : Permissions.check(context.source(), this.permission, this.permissionLevel.get()));
    }
}
