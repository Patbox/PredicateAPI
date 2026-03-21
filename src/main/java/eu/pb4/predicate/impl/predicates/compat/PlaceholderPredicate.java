package eu.pb4.predicate.impl.predicates.compat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.placeholders.api.Placeholders;
import eu.pb4.placeholders.api.ServerPlaceholderContext;
import eu.pb4.predicate.api.AbstractPredicate;
import eu.pb4.predicate.api.PredicateContext;
import eu.pb4.predicate.api.PredicateResult;
import net.minecraft.resources.Identifier;

public final class PlaceholderPredicate extends AbstractPredicate {
    public static final Identifier ID = Identifier.parse("placeholder");
    public static final MapCodec<PlaceholderPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("placeholder").forGetter(PlaceholderPredicate::value)
    ).apply(instance, PlaceholderPredicate::new));

    private final Identifier placeholderId;
    private final String arg;

    public PlaceholderPredicate(String placeholder) {
        super(ID, CODEC);

        var data = placeholder.split(" ", 2);

        this.placeholderId = Identifier.tryParse(data[0]);

        this.arg = data.length == 2 ? data[1] : null;
    }

    public String value() {
        return this.placeholderId + (this.arg != null ? " " + this.arg : "");
    }

    @Override
    public PredicateResult<?> test(PredicateContext context) {
        var placeholder = Placeholders.getServerPlaceholder(this.placeholderId);
        if (placeholder == null) {
            return new PredicateResult<>(false, null);
        }
        var result = placeholder.onPlaceholderRequest(ServerPlaceholderContext.of(context.source()), this.arg);
        return new PredicateResult<>(result.isValid(), result.component());
    }
}
