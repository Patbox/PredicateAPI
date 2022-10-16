package eu.pb4.predicate.impl.predicates.compat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import net.minecraft.util.Identifier;

public final class PlaceholderPredicate extends AbstractPredicate {
    public static final Identifier ID = new Identifier("placeholder");
    public static final MapCodec<PlaceholderPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("placeholder").forGetter(PlaceholderPredicate::value),
            Codec.BOOL.optionalFieldOf("raw", false).forGetter(PlaceholderPredicate::raw)
    ).apply(instance, PlaceholderPredicate::new));

    private final Identifier placeholderId;
    private final String arg;
    private final boolean raw;

    public PlaceholderPredicate(String placeholder, boolean raw) {
        super(ID, CODEC);

        var data = placeholder.split(" ", 2);

        this.placeholderId = Identifier.tryParse(data[0]);

        this.arg = data.length == 2 ? data[1] : null;
        this.raw = raw;
    }

    public String value() {
        return this.placeholderId + (this.arg != null ? " " + this.arg : "");
    }

    public boolean raw() {
        return this.raw;
    }

    @Override
    public PredicateResult<?> test(PredicateContext context) {
        var pCon = new PlaceholderContext(context.server(), context.source(), context.world(), context.player(), context.entity(), context.gameProfile());
        var result = Placeholders.parsePlaceholder(this.placeholderId, this.arg, pCon);
        return new PredicateResult<>(result.isValid(), this.raw ? result.string() : result.text());
    }
}
