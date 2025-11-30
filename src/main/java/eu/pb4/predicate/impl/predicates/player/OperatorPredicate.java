package eu.pb4.predicate.impl.predicates.player;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;

public final class OperatorPredicate extends AbstractPredicate {
    public static final Identifier ID = Identifier.parse("operator");
    public static final MapCodec<OperatorPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.withAlternative(PermissionLevel.CODEC, PermissionLevel.INT_CODEC).fieldOf("operator").forGetter(OperatorPredicate::level)
    ).apply(instance, OperatorPredicate::new));

    private final PermissionLevel level;
    private Permission permission;

    @Deprecated
    public OperatorPredicate(int operator) {
        this(PermissionLevel.byId(operator));
    }

    public OperatorPredicate(PermissionLevel level) {
        super(ID, CODEC);
        this.level = level;
        this.permission = new Permission.HasCommandLevel(this.level);
    }

    private PermissionLevel level() {
        return this.level;
    }

    @Override
    public PredicateResult<?> test(PredicateContext context) {
        return PredicateResult.ofBoolean(context.source().permissions().hasPermission(this.permission));
    }
}
