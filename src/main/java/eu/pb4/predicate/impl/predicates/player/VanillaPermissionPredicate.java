package eu.pb4.predicate.impl.predicates.player;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.Permission;
import java.util.Optional;

public final class VanillaPermissionPredicate extends AbstractPredicate {
    public static final Identifier ID = Identifier.parse("vanilla_permission");
    public static final MapCodec<VanillaPermissionPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Permission.CODEC.fieldOf("value").forGetter(VanillaPermissionPredicate::permission)
    ).apply(instance, VanillaPermissionPredicate::new));

    private final Permission permission;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public VanillaPermissionPredicate(Permission permission) {
        super(ID, CODEC);
        this.permission = permission;
    }

    public Permission permission() {
        return this.permission;
    }
    @Override
    public PredicateResult<?> test(PredicateContext context) {
        return PredicateResult.ofBoolean(context.source().permissions().hasPermission(this.permission));
    }
}
