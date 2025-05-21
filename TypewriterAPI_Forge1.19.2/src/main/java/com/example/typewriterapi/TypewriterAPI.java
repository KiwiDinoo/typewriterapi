package com.example.typewriterapi.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.resources.ResourceLocation;

@Mod.EventBusSubscriber(modid = "typewriterapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TypewriterAPI {

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
            Commands.literal("typewriter")
                .then(Commands.argument("text", StringArgumentType.greedyString())
                    .executes(ctx -> {
                        String message = StringArgumentType.getString(ctx, "text");

                        // Play experience orb pickup sound with the text screen
                        Minecraft.getInstance().setScreen(new TypewriterScreen(
                            message, 
                            5.0f, 
                            new ResourceLocation("minecraft", "entity.experience_orb.pickup")
                        ));
                        return 1;
                    })
                )
        );
    }
}
