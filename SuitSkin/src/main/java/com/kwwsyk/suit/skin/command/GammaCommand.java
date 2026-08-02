package com.kwwsyk.suit.skin.command;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class GammaCommand {
    private static final double MIN_GAMMA = 0.0D;
    private static final double MAX_GAMMA = 15.0D;

    public static void registerClient(CommandDispatcher<CommandSourceStack> dispatcher){

        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("suit");

        root.then(
                Commands.literal("gamma")
                        .executes(//no arg: show current value
                                context -> {
                                    double gamma = getOptions().gamma().get();
                                    context.getSource().sendSuccess(
                                            () -> Component.literal("Gamma = " + gamma), false
                                    );
                                    return 1;
                                }
                        )
                        .then(Commands.argument("value", DoubleArgumentType.doubleArg(MIN_GAMMA, MAX_GAMMA))
                                .executes(context -> {
                                    double gamma = DoubleArgumentType.getDouble(context, "value");
                                    Options options = getOptions();
                                    options.gamma().set(gamma);
                                    options.save();
                                    context.getSource().sendSuccess(
                                            () -> Component.literal("Set gamma = " + gamma), true
                                    );
                                    return 1;
                                })
                        )
        );

        dispatcher.register(root);
    }

    private static Options getOptions() {
        return Minecraft.getInstance().options;
    }
}
