package eu.pb4.predicate.impl.predicates.compat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import eu.pb4.predicate.impl.FabricPermissionBridge;
import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.PermissionLevel;
import java.util.Optional;

public final class FabricPermissionPredicate extends AbstractPredicate {
    public static final Identifier ID = Identifier.parse("permission");
    public static final MapCodec<FabricPermissionPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("permission").forGetter(FabricPermissionPredicate::permission),
            Codec.withAlternative(PermissionLevel.CODEC, PermissionLevel.INT_CODEC).optionalFieldOf("operator").forGetter(FabricPermissionPredicate::permissionLevel)
    ).apply(instance, FabricPermissionPredicate::new));

    private final Identifier permission;
    private final Optional<PermissionLevel> permissionLevel;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public FabricPermissionPredicate(Identifier permission, Optional<PermissionLevel> permissionLevel) {
        super(ID, CODEC);
        this.permission = permission;
        this.permissionLevel = permissionLevel;
    }

    public Identifier permission() {
        return this.permission;
    }

    private Optional<PermissionLevel> permissionLevel() {
        return this.permissionLevel;
    }

    @Override
    public PredicateResult<?> test(PredicateContext context) {
        return PredicateResult.ofBoolean(permissionLevel.isEmpty()
                ? FabricPermissionBridge.checkPermission(context.source(), this.permission, false)
                : FabricPermissionBridge.checkPermission(context.source(), this.permission, this.permissionLevel.orElseThrow())
        );
    }
}
