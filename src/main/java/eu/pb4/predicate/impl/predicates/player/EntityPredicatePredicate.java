package eu.pb4.predicate.impl.predicates.player;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.util.Identifier;

public class EntityPredicatePredicate extends AbstractPredicate {
    public static final Identifier ID = new Identifier("entity");

    public static final MapCodec<EntityPredicatePredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EntityPredicateCodec.INSTANCE.fieldOf("value").forGetter(EntityPredicatePredicate::predicate)
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
            return PredicateResult.ofBoolean(this.entityPredicate.test(context.world(), context.entity().getPos(), context.entity()));
        }
        return PredicateResult.ofFailure();
    }

    private static class EntityPredicateCodec implements Codec<EntityPredicate> {
        private static final EntityPredicateCodec INSTANCE = new EntityPredicateCodec();

        @Override
        public <T> DataResult<Pair<EntityPredicate, T>> decode(DynamicOps<T> ops, T input) {
            return DataResult.success(new Pair<>(
                    EntityPredicate.fromJson(input instanceof JsonElement jsonElement
                            ? jsonElement
                            : ops.convertTo(JsonOps.INSTANCE, input)
                    ), input));
        }

        @Override
        public <T> DataResult<T> encode(EntityPredicate input, DynamicOps<T> ops, T prefix) {
            var encoded= ops instanceof JsonOps ? (T) input.toJson() : JsonOps.INSTANCE.convertTo(ops, input.toJson());

            return ops.getMap(encoded).flatMap(map -> ops.mergeToMap(prefix, map));
        }
    }

}
