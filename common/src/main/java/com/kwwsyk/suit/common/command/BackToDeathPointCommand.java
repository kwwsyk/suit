package com.kwwsyk.suit.common.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;

import java.util.Optional;

public final class BackToDeathPointCommand {

    private BackToDeathPointCommand(){}

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("back")
                .requires((commandSourceStack)-> commandSourceStack.hasPermission(1))
                .executes((commandContext) -> sendToDeathPoint(commandContext.getSource(),
                        commandContext.getSource().getPlayerOrException())));
    }

    private static int sendToDeathPoint(CommandSourceStack source, ServerPlayer player){
        if(player==null){
            source.sendFailure(Component.literal("no target exist."));
            return 0;
        }
        Optional<GlobalPos> lastDeathPointCheck = player.getLastDeathLocation();
        if(lastDeathPointCheck.isEmpty()){
            source.sendFailure(Component.translatable("commands.back.fail"));
        }else{
            GlobalPos lastDeathPoint = lastDeathPointCheck.get();
            Optional.ofNullable(player.getServer())
                    .map(s -> s.getLevel(lastDeathPoint.dimension()))
                    .ifPresent(
                            serverLevel -> player.teleportTo(
                                    serverLevel,
                                    lastDeathPoint.pos().getX(),
                                    lastDeathPoint.pos().getY(),
                                    lastDeathPoint.pos().getZ(),
                                    Mth.wrapDegrees(player.getYRot()),
                                    Mth.wrapDegrees(player.getXRot())
                            )
                    );
        }
        return 1;
    }
}

