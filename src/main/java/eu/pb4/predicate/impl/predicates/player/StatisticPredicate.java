package eu.pb4.predicate.impl.predicates.player;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;

public final class StatisticPredicate extends AbstractPredicate {
    public static final Identifier ID = Identifier.parse("statistic");
    public static final MapCodec<StatisticPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BuiltInRegistries.STAT_TYPE.byNameCodec().optionalFieldOf("stat_type", Stats.CUSTOM).forGetter(StatisticPredicate::statType),
            Identifier.CODEC.fieldOf("key").forGetter(StatisticPredicate::key)
    ).apply(instance, StatisticPredicate::new));

    private final StatType<?> type;

    private final Identifier key;
    private final Object realKey;

    public StatisticPredicate(StatType<?> type, Identifier key) {
        super(ID, CODEC);
        this.type = type;
        this.key = BuiltInRegistries.CUSTOM_STAT.getValue(key);
        this.realKey = type.getRegistry().getValue(this.key);
    }

    private Identifier key() {
        return this.key;
    }

    private StatType<?> statType() {
        return this.type;
    }

    @Override
    public PredicateResult<?> test(PredicateContext context) {
        if (context.hasPlayer()) {
            var val = context.player().getStats().getValue((StatType<Object>) this.type, this.realKey);
            return new PredicateResult<>(val > 0, val);
        } else {
            return PredicateResult.ofFailure();
        }
    }
}
