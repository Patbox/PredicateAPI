package eu.pb4.predicate.impl.predicates.compat;

import net.fabricmc.loader.api.FabricLoader;

public interface CompatStatus {
    boolean PLACEHOLDER_API = FabricLoader.getInstance().isModLoaded("placeholder-api");

    boolean LUCKO_PERMISSION_API = FabricLoader.getInstance().isModLoaded("fabric-permissions-api-v0");
}
