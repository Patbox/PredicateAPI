package eu.pb4.placeholderstest;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.server.level.ServerPlayer;

import static net.minecraft.commands.Commands.literal;
import static net.minecraft.commands.Commands.argument;


public class TestMod implements ModInitializer {

    private static int test(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer player = context.getSource().getPlayer();
            //player.sendMessage(Placeholders.parseText(context.getArgument("text", Text.class), PredicateContext.of(player)), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, dedicated) -> {
            dispatcher.register(
                    literal("test").then(argument("text", ComponentArgument.textComponent(registryAccess)).executes(TestMod::test))
            );
        });
    }

}
