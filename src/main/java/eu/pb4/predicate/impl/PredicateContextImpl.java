package eu.pb4.predicate.impl;

import com.mojang.authlib.GameProfile;
import eu.pb4.predicate.api.PredicateContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public record PredicateContextImpl(MinecraftServer server,
                               Supplier<CommandSourceStack> lazySource,
                               @Nullable ServerLevel world,
                               @Nullable ServerPlayer player,
                               @Nullable Entity entity,
                               @Nullable GameProfile gameProfile
)implements PredicateContext {

    @Override
    public CommandSourceStack source() {
        return this.lazySource.get();
    }

    @Override public boolean hasWorld() {
        return this.world != null;
    }

    @Override public boolean hasPlayer() {
        return this.player != null;
    }

    @Override public boolean hasGameProfile() {
        return this.gameProfile != null;
    }

    @Override public boolean hasEntity() {
        return this.entity != null;
    }

    }
