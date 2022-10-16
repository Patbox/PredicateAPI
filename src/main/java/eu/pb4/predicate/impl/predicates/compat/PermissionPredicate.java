package eu.pb4.predicate.impl.predicates.compat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.util.Identifier;

public final class PermissionPredicate extends AbstractPredicate {
    public static final Identifier ID = new Identifier("permission");
    public static final MapCodec<PermissionPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("permission").forGetter(PermissionPredicate::permission),
            Codec.INT.optionalFieldOf("operator", -1).forGetter(PermissionPredicate::operator)
    ).apply(instance, PermissionPredicate::new));

    private final String permission;
    private final int operator;

    public PermissionPredicate(String permission, int operator) {
        super(ID, CODEC);
        this.permission = permission;
        this.operator = operator;
    }

    public String permission() {
        return this.permission;
    }

    private int operator() {
        return this.operator;
    }

    @Override
    public PredicateResult<?> test(PredicateContext context) {
        return PredicateResult.ofBoolean(operator == -1 ? Permissions.check(context.source(), this.permission) : Permissions.check(context.source(), this.permission, this.operator));
    }
}
