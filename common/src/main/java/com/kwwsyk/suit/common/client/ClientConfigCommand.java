package com.kwwsyk.suit.common.client;

import com.kwwsyk.suit.common.options.config.command.CommandBuilder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ClientConfigCommand {

    public static void registerClient(CommandDispatcher<CommandSourceStack> dispatcher){
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("suit");

        LiteralArgumentBuilder<CommandSourceStack> configs = Commands
                .literal("clientConfigs")
                .executes(ctx -> {
                        ctx.getSource().sendSuccess(
                                () -> Component.literal("Suit client configs root. Use /suit configs <path>"), false
                        );
                        return 1;
                    }
                );

        CommandBuilder.buildCommandTree(configs, ClientConfigs.getConfigs());

        root.then(configs);
        dispatcher.register(root);
    }
}
