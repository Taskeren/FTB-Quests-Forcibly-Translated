package cn.elytra.mod.forcibly_translated;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import static cn.elytra.mod.forcibly_translated.ForciblyTranslated.showKeys;

public class ForciblyTranslatedCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // @formatter:off
        dispatcher.register(literal("forcibly_translated")
            .then(literal("show-keys")
                .then(literal("on").executes(ctx -> {
                    showKeys = true;
                    ctx.getSource().sendSystemMessage(Component.literal("Now = " + showKeys));
                    return 1;
                }))
                .then(literal("off").executes(ctx -> {
                    showKeys = false;
                    ctx.getSource().sendSystemMessage(Component.literal("Now = " + showKeys));
                    return 1;
                }))
                .executes(ctx -> {
                    ctx.getSource().sendSystemMessage(Component.literal("Now = " + showKeys));
                    return 1;
                }))
        );
        // @formatter:on
    }

    private static LiteralArgumentBuilder<CommandSourceStack> literal(String string) {
        return LiteralArgumentBuilder.literal(string);
    }

    private static <S> RequiredArgumentBuilder<CommandSourceStack, S> argument(String name,
        ArgumentType<S> argumentType) {
        return RequiredArgumentBuilder.argument(name, argumentType);
    }

}
