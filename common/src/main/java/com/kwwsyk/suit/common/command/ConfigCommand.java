package com.kwwsyk.suit.common.command;

import com.kwwsyk.suit.common.options.ServerConfigs;
import com.kwwsyk.suit.common.options.config.ConfigEntryImpl;
import com.kwwsyk.suit.common.options.config.command.CommandBuilder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public final class ConfigCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("suit");


        LiteralArgumentBuilder<CommandSourceStack> configs = Commands.literal("configs").requires(src -> src.hasPermission(2))
                .executes(ctx -> {
                    ctx.getSource().sendSuccess(() -> Component.literal("Suit configs root. Use /suit configs <path>"), false);
                    return 1;
                });

        for (ConfigEntryImpl<?> cfg : ServerConfigs.getConfigs()) {
            configs.then(CommandBuilder.buildNode(cfg));
        }

        root.then(configs);
        dispatcher.register(root);
    }
}