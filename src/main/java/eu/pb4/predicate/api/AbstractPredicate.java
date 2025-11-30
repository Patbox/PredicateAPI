package eu.pb4.predicate.api;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;

public abstract class AbstractPredicate implements MinecraftPredicate {

    private final Identifier identifier;
    private final MapCodec<MinecraftPredicate> codec;

    public <T extends MinecraftPredicate> AbstractPredicate(Identifier identifier, MapCodec<T> codec) {
        this.identifier = identifier;
        this.codec = (MapCodec<MinecraftPredicate>) codec;
    }

    @Override
    public Identifier identifier() {
        return this.identifier;
    }

    @Override
    public MapCodec<MinecraftPredicate> codec() {
        return this.codec;
    }
}
