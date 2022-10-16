package eu.pb4.predicate.api;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public record PredicateContext(MinecraftServer server,
                               ServerCommandSource source,
                               @Nullable ServerWorld world,
                               @Nullable ServerPlayerEntity player,
                               @Nullable Entity entity,
                               @Nullable GameProfile gameProfile
) {
    public boolean hasWorld() {
        return this.world != null;
    }

    public boolean hasPlayer() {
        return this.player != null;
    }

    public boolean hasGameProfile() {
        return this.gameProfile != null;
    }

    public boolean hasEntity() {
        return this.entity != null;
    }

    public static PredicateContext of(MinecraftServer server) {
        return new PredicateContext(server,  server.getCommandSource(), null, null, null, null);
    }

    public static PredicateContext of(GameProfile profile, MinecraftServer server) {
        var name = profile.getName() != null ? profile.getName() : profile.getId().toString();
        return new PredicateContext(server, new ServerCommandSource(CommandOutput.DUMMY, Vec3d.ZERO, Vec2f.ZERO, server.getOverworld(), server.getPermissionLevel(profile), name, Text.literal(name), server, null), null, null, null, profile);
    }

    public static PredicateContext of(ServerPlayerEntity player) {
        return new PredicateContext(player.getServer(), player.getCommandSource(), player.getWorld(), player, player, player.getGameProfile());
    }

    public static PredicateContext of(ServerCommandSource source) {
        return new PredicateContext(source.getServer(), source, source.getWorld(), source.getPlayer(), source.getEntity(), source.getPlayer() != null ? source.getPlayer().getGameProfile() : null);
    }

    public static PredicateContext of(Entity entity) {
        if (entity instanceof ServerPlayerEntity player) {
            return of(player);
        } else {
            return new PredicateContext(entity.getServer(), entity.getCommandSource(), (ServerWorld) entity.getWorld(), null, entity, null);
        }
    }
}
