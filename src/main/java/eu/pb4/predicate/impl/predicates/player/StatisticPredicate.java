package eu.pb4.predicate.impl.predicates.player;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import net.minecraft.registry.Registries;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public final class StatisticPredicate extends AbstractPredicate {
    public static final Identifier ID = Identifier.of("statistic");
    public static final MapCodec<StatisticPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Registries.STAT_TYPE.getCodec().optionalFieldOf("stat_type", Stats.CUSTOM).forGetter(StatisticPredicate::statType),
            Identifier.CODEC.fieldOf("key").forGetter(StatisticPredicate::key)
    ).apply(instance, StatisticPredicate::new));

    private final StatType<?> type;

    private final Identifier key;
    private final Object realKey;

    public StatisticPredicate(StatType<?> type, Identifier key) {
        super(ID, CODEC);
        this.type = type;
        this.key = Registries.CUSTOM_STAT.get(key);
        this.realKey = type.getRegistry().get(this.key);
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
            var val = context.player().getStatHandler().getStat((StatType<Object>) this.type, this.realKey);
            return new PredicateResult<>(val > 0, val);
        } else {
            return PredicateResult.ofFailure();
        }
    }
}
