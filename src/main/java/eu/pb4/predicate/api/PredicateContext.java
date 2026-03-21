package eu.pb4.predicate.api;

import com.google.common.base.Suppliers;
import com.mojang.authlib.GameProfile;
import eu.pb4.predicate.impl.PredicateContextImpl;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface PredicateContext {
    static PredicateContext of(MinecraftServer server) {
        return new PredicateContextImpl(server, Suppliers.memoize(server::createCommandSourceStack), null, null, null, null);
    }

    static PredicateContext of(GameProfile profile, MinecraftServer server) {
        var name = !profile.name().isEmpty() ? profile.name() : profile.id().toString();
        return new PredicateContextImpl(server, Suppliers.ofInstance(new CommandSourceStack(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, server.overworld(), server.getProfilePermissions(new NameAndId(profile)), name, Component.literal(name), server, null)), null, null, null, profile);
    }

    static PredicateContext of(ServerPlayer player) {
        return new PredicateContextImpl(player.level().getServer(), Suppliers.memoize(player::createCommandSourceStack), player.level(), player, player, player.getGameProfile());
    }

    static PredicateContext of(CommandSourceStack source) {
        return new PredicateContextImpl(source.getServer(), () -> source, source.getLevel(), source.getPlayer(), source.getEntity(), source.getPlayer() != null ? source.getPlayer().getGameProfile() : null);
    }

    static PredicateContext of(Entity entity) {
        if (entity instanceof ServerPlayer player) {
            return of(player);
        } else {
            return new PredicateContextImpl(entity.level().getServer(), Suppliers.memoize(() -> entity.createCommandSourceStackForNameResolution((ServerLevel) entity.level())), (ServerLevel) entity.level(), null, entity, null);
        }
    }

    boolean hasWorld();

    boolean hasPlayer();

    boolean hasGameProfile();

    boolean hasEntity();

    MinecraftServer server();

    CommandSourceStack source();

    ServerLevel world();

    ServerPlayer player();

    Entity entity();

    GameProfile gameProfile();
}
