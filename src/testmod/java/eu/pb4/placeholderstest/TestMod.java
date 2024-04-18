package eu.pb4.placeholderstest;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;


public class TestMod implements ModInitializer {

    private static int test(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity player = context.getSource().getPlayer();
            //player.sendMessage(Placeholders.parseText(context.getArgument("text", Text.class), PredicateContext.of(player)), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, dedicated) -> {
            dispatcher.register(
                    literal("test").then(argument("text", TextArgumentType.text(registryAccess)).executes(TestMod::test))
            );
        });
    }

}
