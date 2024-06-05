package eu.pb4.predicate.impl.predicates.compat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import me.lucko.fabric.api.permissions.v0.Options;
import net.minecraft.util.Identifier;

public final class PermissionOptionPredicate extends AbstractPredicate {
    public static final Identifier ID = Identifier.of("permission_option");
    public static final MapCodec<PermissionOptionPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("option").forGetter(PermissionOptionPredicate::permission)
    ).apply(instance, PermissionOptionPredicate::new));

    private final String permission;

    public PermissionOptionPredicate(String permission) {
        super(ID, CODEC);
        this.permission = permission;
    }

    public String permission() {
        return this.permission;
    }


    @Override
    public PredicateResult<?> test(PredicateContext context) {
        return PredicateResult.ofOptional(Options.get(context.source(), this.permission));
    }
}
