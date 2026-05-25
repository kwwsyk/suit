package com.kwwsyk.suit.common.command;

import com.kwwsyk.suit.common.options.config.ConfigEntryImpl;
import com.kwwsyk.suit.common.options.config.command.CommandBuilder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import java.util.List;

public final class SuitConfigCommandHelper {

    public static void register(
            CommandDispatcher<CommandSourceStack> dispatcher,
            List<ConfigEntryImpl<?>> configEntries,
            String sub_modName
    ){
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("suit");

        LiteralArgumentBuilder<CommandSourceStack> configs = Commands
                .literal(sub_modName)
                .requires(src -> src.hasPermission(2))
                .executes(ctx -> {
                    ctx.getSource().sendSuccess(() -> Component.literal("Suit configs root. Use /suit configs <path>"), false);
                    return 1;
                });

        for (ConfigEntryImpl<?> cfg : configEntries) {
            configs.then(CommandBuilder.buildNode(cfg));
        }

        root.then(configs);
        dispatcher.register(root);
    }
}