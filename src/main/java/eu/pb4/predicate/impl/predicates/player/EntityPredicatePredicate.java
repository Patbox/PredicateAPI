package eu.pb4.predicate.impl.predicates.player;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.resources.Identifier;

public class EntityPredicatePredicate extends AbstractPredicate {
    public static final Identifier ID = Identifier.parse("entity");

    public static final MapCodec<EntityPredicatePredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EntityPredicate.CODEC.fieldOf("value").forGetter(EntityPredicatePredicate::predicate)
    ).apply(instance, EntityPredicatePredicate::new));
    private final EntityPredicate entityPredicate;

    public EntityPredicatePredicate(EntityPredicate predicate) {
        super(ID, CODEC);
        this.entityPredicate = predicate;
    }

    private EntityPredicate predicate() {
        return this.entityPredicate;
    }

    @Override
    public PredicateResult<?> test(PredicateContext context) {
        if (context.hasEntity()) {
            return PredicateResult.ofBoolean(this.entityPredicate.matches(context.world(), context.entity().position(), context.entity()));
        }
        return PredicateResult.ofFailure();
    }
}
