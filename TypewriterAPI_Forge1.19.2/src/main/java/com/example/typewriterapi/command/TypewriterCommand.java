package com.example.typewriterapi.command;

import com.example.typewriterapi.network.ModNetwork;
import com.example.typewriterapi.network.packet.DisplayTextPacket;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;

public class TypewriterCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("typewriter")
            .then(Commands.argument("text", StringArgumentType.greedyString())
                .executes(context -> {
                    String text = StringArgumentType.getString(context, "text");
                    // Send packet to the command sender (client only example)
                    ModNetwork.CHANNEL.sendToServer(new DisplayTextPacket(text, 5.0f, new ResourceLocation("minecraft", "entity.player.levelup")));
                    return 1;
                })
            )
        );
    }
}
