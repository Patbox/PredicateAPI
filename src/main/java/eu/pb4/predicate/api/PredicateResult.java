package eu.pb4.predicate.api;

import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record PredicateResult<T>(boolean success, @Nullable T value) {
    public static <T> PredicateResult<T> ofNullable(@Nullable T value) {
        return new PredicateResult<>(value != null, value);
    }

    public static <T> PredicateResult<T> ofFailure(@Nullable T value) {
        return new PredicateResult<>(false, value);
    }

    public static PredicateResult<Boolean> ofFailure() {
        return new PredicateResult<>(false, Boolean.FALSE);
    }

    public static <T> PredicateResult<T> ofSuccess(@Nullable T value) {
        return new PredicateResult<>(true, value);
    }

    public static PredicateResult<Boolean> ofSuccess() {
        return new PredicateResult<>(true, Boolean.TRUE);
    }

    public static PredicateResult<Boolean> ofBoolean(boolean value) {
        return new PredicateResult<>(value, value);
    }

    public static PredicateResult<Text> ofText(Text value) {
        return new PredicateResult<>(!value.getSiblings().isEmpty() && value.getContent() != TextContent.EMPTY, value);
    }

    public static <T> PredicateResult<T> ofOptional(Optional<T> optional) {
        return optional.isPresent() ? new PredicateResult<>(true, optional.get()) : new PredicateResult<>(false, null);
    }
}
